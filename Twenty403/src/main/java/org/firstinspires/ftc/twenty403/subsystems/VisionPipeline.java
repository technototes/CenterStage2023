package org.firstinspires.ftc.twenty403.subsystems;

import android.graphics.Bitmap;
import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import com.technototes.library.logger.Log;
import com.technototes.library.logger.LogConfig;
import com.technototes.library.logger.Loggable;
import com.technototes.library.util.Alliance;
import java.util.function.Supplier;
import org.firstinspires.ftc.twenty403.helpers.StartingPosition;
import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
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
        public static class Left {

            public static int X = 100;
            public static int Y = 150;
            public static int WIDTH = 60;
            public static int HEIGHT = 60;
        }

        public static class Middle {

            public static int X = 165;
            public static int Y = 136;
            public static int WIDTH = 60;
            public static int HEIGHT = 60;
        }

        public enum Position {
            LEFT,
            CENTER,
            RIGHT,
        }

        public static double RED1 = 0;

        public static double RED2 = 179;
        public static double BLUE = 120;

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
        public static int MINPIXELCOUNT = 130;
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

    private int countColor(double hue, Mat rect) {
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
                        img.put(
                            j + VisionConstants.Middle.Y,
                            i + VisionConstants.Middle.X,
                            colorToDraw
                        );
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
            VisionConstants.Left.Y,
            VisionConstants.Left.Y + VisionConstants.Left.HEIGHT,
            // Col start to Col end
            VisionConstants.Left.X,
            VisionConstants.Left.X + VisionConstants.Left.WIDTH
        );

        // Next, convert the RGB image to HSV, because HUE is much easier to identify colors in
        // The output is in 'customColorSpace'
        Mat rectM = new Mat();
        Mat rectL = new Mat();
        Imgproc.cvtColor(mRectToLookAtM, rectM, Imgproc.COLOR_RGB2HSV);
        Imgproc.cvtColor(mRectToLookAtL, rectL, Imgproc.COLOR_RGB2HSV);
        // Check to see which colors occur:
        int colorCountL = 0;
        int colorCountM = 0;
        if (this.alliance == Alliance.BLUE) {
            colorCountM = countColor(VisionConstants.BLUE, rectM);
        } else {
            colorCountM = countColor(VisionConstants.RED1, rectM);
            colorCountM += countColor(VisionConstants.RED2, rectM);
        }
        if (this.alliance == Alliance.BLUE) {
            colorCountL = countColor(VisionConstants.BLUE, rectL);
        } else {
            colorCountL = countColor(VisionConstants.RED1, rectL);
            colorCountL += countColor(VisionConstants.RED2, rectL);
        }
        pickLocation(colorCountL, colorCountM);
    }

    public void detectSignal(Mat input) {
        // Put the input matrix in a member variable, so that other functions can draw on it
        img = input;

        countPixels(input);
        // Check which spot we should park in
        // middleDetected = countA >= countY && countA >= countP;
        // leftDetected = countP >= countA && countP >= countY;
        rightDetected = !leftDetected && !middleDetected;

        // Draw a rectangle around the area we're looking at, for debugging
        int x = Range.clip(VisionConstants.Middle.X - 1, 0, input.width() - 1);
        int y = Range.clip(VisionConstants.Middle.Y - 1, 0, input.height() - 1);
        int w = Range.clip(VisionConstants.Middle.WIDTH + 2, 1, input.width() - x);
        int h = Range.clip(VisionConstants.Middle.HEIGHT + 2, 1, input.height() - y);

        int xl = Range.clip(VisionConstants.Left.X - 1, 0, input.width() - 1);
        int yl = Range.clip(VisionConstants.Left.Y - 1, 0, input.height() - 1);
        int wl = Range.clip(VisionConstants.Left.WIDTH + 2, 1, input.width() - x);
        int hl = Range.clip(VisionConstants.Left.HEIGHT + 2, 1, input.height() - y);
        Imgproc.rectangle(input, new Rect(x, y, w, h), VisionConstants.RGB_HIGHLIGHT);
        Imgproc.rectangle(input, new Rect(xl, yl, wl, hl), VisionConstants.RGB_HIGHLIGHT);
    }

    public void init(Mat firstFrame) {
        detectSignal(firstFrame);
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

    private void pickLocation(int countL, int countM) {
        /*
        First we have to create a rectangle from teh view that the camera sees.
        Then we have to convert RGB to HSV.
        Then we check for specific colors (in this case, red and blue)
        create another rectangle and do the same stuff

        If there is more of that color than a specific value in either of the rectangles(left and middle) then the pixel is there.
        If neither of the rectangles have more of that color, then it is in the 3rd position (right)
        If there is more blue than red or vice versa, then that color is your alliance yay :)
         */

        if (countL > VisionConstants.MINPIXELCOUNT && countL > countM) {
            leftDetected = true;
            middleDetected = false;
        } else if (
            countM <= VisionConstants.MINPIXELCOUNT && countL <= VisionConstants.MINPIXELCOUNT
        ) {
            rightDetected = true;
            middleDetected = false;
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
