package org.firstinspires.ftc.twenty403;

import com.acmerobotics.dashboard.config.Config;

public class Setup {

    @Config
    public static class Connected {

        public static boolean DRIVEBASE = true;
        public static boolean ARMSUBSYSTEM = true;
        public static boolean DRONESUBSYSTEM = false;
        public static boolean WEBCAM = true;
        public static boolean ODOSUBSYSTEM = true;
        public static boolean SAFETYSUBSYSTEM = true;
    }

    @Config
    public static class HardwareNames {

        public static String FLMOTOR = "fl";
        public static String FRMOTOR = "fr";
        public static String RLMOTOR = "rl";
        public static String RRMOTOR = "rr";
        public static String IMU = "imu";
        public static String CAMERA = "webcam";
        public static String INTAKESERVO = "intakeservo";
        public static String WRISTSERVO = "wristservo";
        public static String SHOULDERMOTOR = "shoulder";
        public static String SHOULDER2 = "odor";
        public static String DRONESERVO = "droneservo";
        public static String ODOF = "odof";
        public static String ODOR = "odor";
    }

    @Config
    public static class OtherSettings {

        public static int AUTOTIME = 25;
        public static double STRAIGHTEN_DEAD_ZONE = 0.015;
    }
}
