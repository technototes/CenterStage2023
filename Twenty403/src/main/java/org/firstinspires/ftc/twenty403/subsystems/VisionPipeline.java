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
        public static class SignalDetection {
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
        }
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
            hue - VisionConstants.SignalDetection.RANGE,
            VisionConstants.SignalDetection.lowS,
            VisionConstants.SignalDetection.lowV
        );
        Scalar edge2 = new Scalar(
            hue + VisionConstants.SignalDetection.RANGE,
            VisionConstants.SignalDetection.highS,
            VisionConstants.SignalDetection.highV
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
                            j + VisionConstants.SignalDetection.Middle.Y,
                            i + VisionConstants.SignalDetection.Middle.X,
                            colorToDraw
                        );
                    }
                }
            }
        }
        return count;
    }

    public void detectSignal(Mat input) {
        // Put the input matrix in a member variable, so that other functions can draw on it
        img = input;

        // First, slice the smaller rectangle out of the overall bitmap:
        Mat mRectToLookAt = input.submat(
            // Row start to Row end
            VisionConstants.SignalDetection.Middle.Y,
            VisionConstants.SignalDetection.Middle.Y + VisionConstants.SignalDetection.Middle.HEIGHT,
            // Col start to Col end
            VisionConstants.SignalDetection.Middle.X,
            VisionConstants.SignalDetection.Middle.X + VisionConstants.SignalDetection.Middle.WIDTH
        );

        // Next, convert the RGB image to HSV, because HUE is much easier to identify colors in
        // The output is in 'customColorSpace'
        Mat rect = new Mat();
        Imgproc.cvtColor(mRectToLookAt, rect, Imgproc.COLOR_RGB2HSV);

        // Check to see which colors occur:
        int colorCount = 0;
        if (this.alliance == Alliance.BLUE) {
            colorCount = countColor(VisionConstants.SignalDetection.BLUE, rect);
        } else {
            colorCount = countColor(VisionConstants.SignalDetection.RED1, rect);
            colorCount += countColor(VisionConstants.SignalDetection.RED2, rect);
        }
        // Check which spot we should park in
        // middleDetected = countA >= countY && countA >= countP;
        // rightDetected = countP >= countA && countP >= countY;
        leftDetected = !rightDetected && !middleDetected;

        // Draw a rectangle around the area we're looking at, for debugging
        int x = Range.clip(VisionConstants.SignalDetection.Middle.X - 1, 0, input.width() - 1);
        int y = Range.clip(VisionConstants.SignalDetection.Middle.Y - 1, 0, input.height() - 1);
        int w = Range.clip(VisionConstants.SignalDetection.Middle.WIDTH + 2, 1, input.width() - x);
        int h = Range.clip(VisionConstants.SignalDetection.Middle.HEIGHT + 2, 1, input.height() - y);
        Imgproc.rectangle(
            input,
            new Rect(x, y, w, h),
            VisionConstants.SignalDetection.RGB_HIGHLIGHT
        );
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

        detectSignal(input);
        if (VisionSubsystem.VisionSubsystemConstants.DEBUG_VIEW) {
            sendBitmap();
        }
        return input;
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
