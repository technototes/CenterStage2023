package org.firstinspires.ftc.sixteen750.subsystems;

import com.technototes.library.hardware.servo.Servo;
import com.technototes.library.logger.Loggable;
import com.technototes.library.subsystem.Subsystem;
import org.firstinspires.ftc.sixteen750.Hardware;

public class DroneSubsystem implements Subsystem, Loggable {

    private Servo DroneServo;
    private boolean isHardware;

     public static double LAUNCH = 0.1;
//     public static double INTAKE = 0;
    public DroneSubsystem(Hardware hw) {
        DroneServo = hw.DroneServo;
        isHardware = true;
    }

    public DroneSubsystem() {
        DroneServo = null;
        isHardware = false;
    }

    public void Launch() { DroneServo.setPosition(LAUNCH);}

//    public void Reset() { DroneServo.setPosition(INTAKE);}
}
