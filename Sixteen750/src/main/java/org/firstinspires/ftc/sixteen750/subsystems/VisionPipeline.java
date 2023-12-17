package org.firstinspires.ftc.sixteen750.subsystems;

import android.graphics.Bitmap;
import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.technototes.library.logger.Log;
import com.technototes.library.logger.LogConfig;
import com.technototes.library.logger.Loggable;
import com.technototes.library.util.Alliance;
import java.util.function.Supplier;
import org.firstinspires.ftc.sixteen750.helpers.StartingPosition;
import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

@Config
public class VisionPipeline extends OpenCvPipeline implements Supplier<Integer>, Loggable {

    public Alliance alliance;
    public StartingPosition side;

    public VisionPipeline(Alliance teamAlliance, StartingPosition startSide) {
        super();
        alliance = teamAlliance;
        side = startSide;
    }

    @Config
    public static class VisionConstants {

        @Config
        public static class Right {

            public static int X = 250;
            public static int Y = 90;
            public static int WIDTH = 69;
            public static int HEIGHT = 100;
        }

        @Config
        public static class Middle {

            public static int X = 60;
            public static int Y = 90;
            public static int WIDTH = 100;
            public static int HEIGHT = 100;
        }

        public enum Position {
            LEFT,
            CENTER,
            RIGHT,
        }

        public static double RED1 = 0;

        public static double RED2 = 179;
        public static double BLUE = 105;

        // The low saturation point for color identification
        public static double lowS = 70;
        // The high saturation point for color identification
        public static double highS = 255;
        // The low value for color ID
        public static double lowV = 50;
        // The high value for color ID
        public static double highV = 255;
        // The 'range' around the hue that we're looking for
        public static double RANGE = 10;

        // In the 160x120 bitmap, where are we looking?

        public static Scalar RGB_HIGHLIGHT = new Scalar(255, 128, 255);

        // the mininum amount of pixels needed in order to find a pixel
        public static int MINPIXELCOUNT = 1300;
    }

    @LogConfig.Run(duringRun = false, duringInit = true)
    @Log.Boolean(name = "left")
    public volatile boolean leftDetected = false;

    @LogConfig.Run(duringRun = false, duringInit = true)
    @Log.Boolean(name = "middle")
    public volatile boolean middleDetected = true;

    @LogConfig.Run(duringRun = false, duringInit = true)
    @Log.Boolean(name = "right")
    public volatile boolean rightDetected = false;

    @LogConfig.Run(duringRun = false, duringInit = true)
    @Log(name = "fps")
    public volatile double fps = 0.0;

    private ElapsedTime time = new ElapsedTime();

    public Mat Cr = new Mat();
    public Mat img = null;

    private int countColor(double hue, Mat rect, int xoff, int yoff) {
        Scalar edge1 = new Scalar(
            hue - VisionConstants.RANGE,
            VisionConstants.lowS,
            VisionConstants.lowV
        );
        Scalar edge2 = new Scalar(
            hue + VisionConstants.RANGE,
            VisionConstants.highS,
            VisionConstants.highV
        );
        // Check to see which pixels are between edge1 & edge2, output into a boolean matrix Cr
        Core.inRange(rect, edge1, edge2, Cr);
        int count = 0;
        for (int i = 0; i < Cr.width(); i++) {
            for (int j = 0; j < Cr.height(); j++) {
                if (Cr.get(j, i)[0] > 0) {
                    count++;
                    // Draw a dot on the image at this point - input was put into img
                    // The color choice makes things stripey, which makes it easier to identify
                    if (VisionSubsystem.VisionSubsystemConstants.DEBUG_VIEW) {
                        double[] colorToDraw = ((j + i) & 3) != 0 ? edge1.val : edge2.val;
                        img.put(j + yoff, i + xoff, colorToDraw);
                    }
                }
            }
        }
        return count;
        //        count = 0;
        //        for (int i = 0; i < Cr.width(); i++) {
        //            for (int j = 0; j < Cr.height(); j++) {
        //                if (Cr.get(j, i)[0] > 0) {
        //                    count++;
        //                    // Draw a dot on the image at this point - input was put into img
        //                    // The color choice makes things stripey, which makes it easier to identify
        //                    if (VisionSubsystem.VisionSubsystemConstants.DEBUG_VIEW) {
        //                        double[] colorToDraw = ((j + i) & 3) != 0 ? edge1.val : edge2.val;
        //                        img.put(
        //                                j + VisionConstants.Left.Y,
        //                                i + VisionConstants.Left.X,
        //                                colorToDraw
        //                        );
        //                    }
        //            }
        //        }
        //        return count;
    }

    private void countPixels(Mat input) {
        // Put the input matrix in a member variable, so that other functions can draw on it
        img = input;

        // First, slice the smaller rectangle out of the overall bitmap:
        Mat mRectToLookAtM = input.submat(
            // Row start to Row end
            VisionConstants.Middle.Y,
            VisionConstants.Middle.Y + VisionConstants.Middle.HEIGHT,
            // Col start to Col end
            VisionConstants.Middle.X,
            VisionConstants.Middle.X + VisionConstants.Middle.WIDTH
        );

        Mat mRectToLookAtL = input.submat(
            // Row start to Row end
            VisionConstants.Right.Y,
            VisionConstants.Right.Y + VisionConstants.Right.HEIGHT,
            // Col start to Col end
            VisionConstants.Right.X,
            VisionConstants.Right.X + VisionConstants.Right.WIDTH
        );

        // Next, convert the RGB image to HSV, because HUE is much easier to identify colors in
        // The output is in 'customColorSpace'
        Mat rectM = new Mat();
        Mat rectL = new Mat();
        Imgproc.cvtColor(mRectToLookAtM, rectM, Imgproc.COLOR_RGB2HSV);
        Imgproc.cvtColor(mRectToLookAtL, rectL, Imgproc.COLOR_RGB2HSV);
        // Check to see which colors occur:
        int colorCountR = 0;
        int colorCountM = 0;
        if (this.alliance == Alliance.BLUE) {
            colorCountM =
                countColor(
                    VisionConstants.BLUE,
                    rectM,
                    VisionConstants.Middle.X,
                    VisionConstants.Middle.Y
                );
        } else {
            colorCountM =
                countColor(
                    VisionConstants.RED1,
                    rectM,
                    VisionConstants.Middle.X,
                    VisionConstants.Middle.Y
                );
            colorCountM +=
            countColor(
                VisionConstants.RED2,
                rectM,
                VisionConstants.Middle.X,
                VisionConstants.Middle.Y
            );
        }
        if (this.alliance == Alliance.BLUE) {
            colorCountR =
                countColor(
                    VisionConstants.BLUE,
                    rectL,
                    VisionConstants.Right.X,
                    VisionConstants.Right.Y
                );
        } else {
            colorCountR =
                countColor(
                    VisionConstants.RED1,
                    rectL,
                    VisionConstants.Right.X,
                    VisionConstants.Right.Y
                );
            colorCountR +=
            countColor(
                VisionConstants.RED2,
                rectL,
                VisionConstants.Right.X,
                VisionConstants.Right.Y
            );
        }
        pickLocation(colorCountR, colorCountM);
    }

    public void init(Mat firstFrame) {
        countPixels(firstFrame);
    }

    @Override
    public Mat processFrame(Mat input) {
        // Update the FPS counter to see how slow the vision code is
        // As of October 2022, it runs between 10 and 14 FPS.
        fps = 1000 / time.milliseconds();
        time.reset();
        countPixels(input);
        if (VisionSubsystem.VisionSubsystemConstants.DEBUG_VIEW) {
            sendBitmap();
        }
        return input;
    }

    private void pickLocation(int countR, int countM) {
        /*
        First we have to create a rectangle from teh view that the camera sees.
        Then we have to convert RGB to HSV.
        Then we check for specific colors (in this case, red and blue)
        create another rectangle and do the same stuff

        If there is more of that color than a specific value in either of the rectangles(left and middle) then the pixel is there.
        If neither of the rectangles have more of that color, then it is in the 3rd position (right)
        If there is more blue than red or vice versa, then that color is your alliance yay :)
         */

        if (countR > VisionConstants.MINPIXELCOUNT && countR > countM) {
            leftDetected = false;
            middleDetected = false;
            rightDetected = true;
        } else if (
            countM <= VisionConstants.MINPIXELCOUNT && countR <= VisionConstants.MINPIXELCOUNT
        ) {
            rightDetected = false;
            middleDetected = false;
            leftDetected = true;
        } else {
            leftDetected = false;
            middleDetected = true;
            rightDetected = false;
        }
    }

    @Override
    public Integer get() {
        return null;
    }

    public boolean left() {
        return leftDetected;
    }

    public boolean middle() {
        return middleDetected;
    }

    public boolean right() {
        return rightDetected;
    }

    // Helper to send the bitmap to the FTC Dashboard
    private void sendBitmap() {
        FtcDashboard db = FtcDashboard.getInstance();
        if (db != null) {
            Bitmap bitmap = Bitmap.createBitmap(img.cols(), img.rows(), Bitmap.Config.RGB_565);
            Utils.matToBitmap(img, bitmap);
            db.sendImage(bitmap);
        }
    }
}
