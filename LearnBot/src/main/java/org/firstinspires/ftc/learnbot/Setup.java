package org.firstinspires.ftc.learnbot;

import com.acmerobotics.dashboard.config.Config;

public class Setup {

    @Config
    public static class Connected {

        public static boolean DRIVEBASE = false;
        public static boolean TESTSUBSYSTEM = false;
        public static boolean MOTOR = true;
        public static boolean SERVO = false;
        public static boolean DISTANCE_SENSOR = false;
        public static boolean COLOR_SENSOR = false;
    }

    @Config
    public static class HardwareNames {

        public static String MOTOR = "liftmotor";
        public static String FLMOTOR = "fl";
        public static String FRMOTOR = "fr";
        public static String RLMOTOR = "rl";
        public static String RRMOTOR = "rr";
        public static String SERVO = "s";
        public static String IMU = "imu";
        public static String DISTANCE = "d";
        public static String COLOR = "c";
    }

    @Config
    public static class OtherSettings {

        public static double STICK_DEAD_ZONE = 0.1;
        public static int AUTOTIME = 25;
    }
}
