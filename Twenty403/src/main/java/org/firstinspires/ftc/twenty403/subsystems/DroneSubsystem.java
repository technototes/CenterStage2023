package org.firstinspires.ftc.twenty403.subsystems;

import com.technototes.library.hardware.servo.Servo;
import com.technototes.library.logger.Loggable;
import com.technototes.library.subsystem.Subsystem;

public class DroneSubsystem implements Subsystem, Loggable {

    private Servo launchServo;
    private boolean haveHardware;

    public DroneSubsystem(Servo l) {}
}
