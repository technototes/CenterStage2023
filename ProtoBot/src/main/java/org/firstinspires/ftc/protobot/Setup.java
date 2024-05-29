package org.firstinspires.ftc.protobot;

import com.acmerobotics.dashboard.config.Config;

public class Setup {

    @Config
    public static class Connected {

        public static boolean DRIVEBASE = false;
    }

    @Config
    public static class HardwareNames {

        public static String FLMOTOR = "fl";
        public static String FRMOTOR = "fr";
        public static String RLMOTOR = "rl";
        public static String RRMOTOR = "rr";
        public static String IMU = "imu";
    }

    @Config
    public static class OtherSettings {

        public static int AUTOTIME = 25;
        public static double STRAIGHTEN_DEAD_ZONE = 0.1;
    }
}
