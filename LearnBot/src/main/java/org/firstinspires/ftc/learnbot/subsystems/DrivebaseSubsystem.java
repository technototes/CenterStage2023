package org.firstinspires.ftc.learnbot.subsystems;

import com.technototes.library.hardware.motor.Motor;
import com.technototes.library.subsystem.drivebase.SimpleMecanumDrivebaseSubsystem;

public class DrivebaseSubsystem extends SimpleMecanumDrivebaseSubsystem {

    public DrivebaseSubsystem(Motor flMotor, Motor frMotor, Motor rlMotor, Motor rrMotor) {
        super(flMotor, frMotor, rlMotor, rrMotor);
    }
}
