package org.firstinspires.ftc.sixteen750;

import com.acmerobotics.dashboard.config.Config;
import org.firstinspires.ftc.sixteen750.subsystems.DroneSubsystem;

public class Setup {

    @Config
    public static class Connected {

        public static boolean DRIVEBASE = false;
        public static boolean INTAKE = false;
        public static boolean WEBCAM = false;
        public static boolean PLACEMENT = false;

        public static boolean HANG = true;

        public static boolean DRONE = false;
    }

    @Config
    public static class HardwareNames {

        public static String FLMOTOR = "fl";
        public static String FRMOTOR = "fr";
        public static String RLMOTOR = "rl";
        public static String RRMOTOR = "rr";
        public static String IMU = "imu";
        public static String INTAKELEFT = "lwheel";
        //public static String INTAKERIGHT = "rwheel";
        public static String CAMERA = "webcam";
        public static String LIFTMOTOR = "liftmotor";

        public static String ARMSERVO = "aservo";

        public static String SCORESERVO = "sservo";
        public static String HANGSERVO = "hangS";
        public static String HANGMOTOR = "hangM";

        public static String DRONESERVO = "droneS";
    }

    @Config
    public static class OtherSettings {

        public static double STRAIGHTEN_DEAD_ZONE = 0.08;
    }
}
