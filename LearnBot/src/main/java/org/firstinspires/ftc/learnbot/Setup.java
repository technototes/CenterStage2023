package org.firstinspires.ftc.learnbot;

import com.acmerobotics.dashboard.config.Config;

public class Setup {

    @Config
    public static class Connected {

        public static boolean MOTOR = true;
        public static boolean SERVO = false;
        public static boolean DISTANCE_SENSOR = true;
        public static boolean COLOR_SENSOR = false;
    }

    @Config
    public static class HardwareNames {

        public static String MOTOR = "m";
        public static String SERVO = "s";
        public static String IMU = "imu";
        public static String DISTANCE = "d";
        public static String COLOR = "c";
    }

    @Config
    public static class OtherSettings {

        public static int AUTOTIME = 25;
    }
}
