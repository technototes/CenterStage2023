package org.firstinspires.ftc.sixteen750;

import com.acmerobotics.dashboard.config.Config;

public class Setup {

    @Config
    public static class Connected {

        public static boolean DRIVEBASE = true;
        public static boolean INTAKE = true;
        public static boolean WEBCAM = true;
    }

    @Config
    public static class HardwareNames {

        public static String FLMOTOR = "fl";
        public static String FRMOTOR = "fr";
        public static String RLMOTOR = "rl";
        public static String RRMOTOR = "rr";
        public static String IMU = "imu";
        public static String INTAKELEFT = "lwheel";
        public static String INTAKERIGHT = "rwheel";
        public static String CAMERA = "webcam";
    }

    @Config
    public static class OtherSettings {

        public static double STRAIGHTEN_DEAD_ZONE = 0.08;
    }
}
