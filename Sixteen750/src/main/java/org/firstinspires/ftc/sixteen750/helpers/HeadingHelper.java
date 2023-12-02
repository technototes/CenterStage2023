package org.firstinspires.ftc.sixteen750.helpers;

import org.firstinspires.ftc.robotcontroller.internal.FtcRobotControllerActivity;

public class HeadingHelper {

    public static void updateHeading(double d) {
        FtcRobotControllerActivity.endAutoHeading = d;
        FtcRobotControllerActivity.headingUpdateTime = System.currentTimeMillis() / 1000;
    }

    public static boolean validHeading() {
        double now = System.currentTimeMillis() / 1000;
        if (now < FtcRobotControllerActivity.headingUpdateTime + 45) {
            return true;
        } else {
            return false;
        }
    }

    public static double getSavedHeading() {
        return FtcRobotControllerActivity.endAutoHeading;
    }
}
