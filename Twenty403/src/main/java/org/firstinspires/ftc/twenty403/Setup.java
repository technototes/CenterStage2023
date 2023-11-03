package org.firstinspires.ftc.twenty403;

import com.acmerobotics.dashboard.config.Config;

public class Setup {

    @Config
    public static class Connected {

        public static boolean DRIVEBASE = true;
        public static boolean CLAWSUBSYSTEM = true;
        public static boolean DRONESUBSYSTEM = true;
        public static boolean WEBCAM = true;
        public static boolean HANGSUBSYSTEM = false;
    }

    @Config
    public static class HardwareNames {

        public static String FLMOTOR = "fl";
        public static String FRMOTOR = "fr";
        public static String RLMOTOR = "rl";
        public static String RRMOTOR = "rr";
        public static String IMU = "imu";
        public static String CAMERA = "webcam";
        public static String CLAWSERVO = "clawservo";
        //        public static String ELBOWSERVO = "elbowservo";
        public static String SHOULDERMOTOR = "shouldermotor";
        public static String HANG_CRSERVO = "hang";
        public static String HANG_MOTOR = "botlift";
        public static String DRONESERVO = "droneservo";
    }

    @Config
    public static class OtherSettings {

        public static int AUTOTIME = 25;
        public static double STRAIGHTEN_DEAD_ZONE = 0.015;
    }
}
