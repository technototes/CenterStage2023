package org.firstinspires.ftc.sixteen750.subsystems;

import com.technototes.library.hardware.servo.Servo;
import com.technototes.library.logger.Loggable;
import com.technototes.library.subsystem.Subsystem;
import org.firstinspires.ftc.sixteen750.Hardware;

public class DroneSubsystem implements Subsystem, Loggable {

    private Servo DroneServo;
    private boolean isHardware;

    public DroneSubsystem(Hardware hw) {
        DroneServo = hw.DroneServo;
        isHardware = true;
    }

    public DroneSubsystem() {
        DroneServo = null;
        isHardware = false;
    }
}
