package org.firstinspires.ftc.learnbot;

import com.technototes.library.logger.Loggable;

import org.firstinspires.ftc.learnbot.subsystems.DrivebaseSubsystem;

public class Robot implements Loggable {

    public double initialVoltage;
    public DrivebaseSubsystem drivebaseSubsystem;

    public Robot(Hardware hw) {
        this.initialVoltage = hw.voltage();

        if (Setup.Connected.DRIVEBASE) {
            this.drivebaseSubsystem =
                new DrivebaseSubsystem(hw.imu, hw.flMotor, hw.frMotor, hw.rlMotor, hw.rrMotor);
        }

    }
}
