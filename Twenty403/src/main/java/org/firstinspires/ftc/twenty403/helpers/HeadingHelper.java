package org.firstinspires.ftc.twenty403.helpers;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import org.firstinspires.ftc.robotcontroller.internal.FtcRobotControllerActivity;

public class HeadingHelper {

    public static void updateHeading(double d) {
        FtcRobotControllerActivity.endAutoHeading = d;
        FtcRobotControllerActivity.headingUpdateTime = System.currentTimeMillis() / 1000;
    }

    public static boolean validHeading() {
        double now = System.currentTimeMillis() / 1000.0;
        return now < FtcRobotControllerActivity.headingUpdateTime + 45;
    }

    public static double getSavedHeading() {
        return FtcRobotControllerActivity.endAutoHeading;
    }
}
