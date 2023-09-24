package org.firstinspires.ftc.learnbot.subsystems;

import com.technototes.library.hardware.motor.Motor;
import com.technototes.library.hardware.sensor.IMU;
import com.technototes.library.logger.Log;
import com.technototes.library.logger.Loggable;
import com.technototes.library.subsystem.drivebase.SimpleMecanumDrivebaseSubsystem;

public class DrivebaseSubsystem extends SimpleMecanumDrivebaseSubsystem implements Loggable {

    @Log(name = "cur heading")
    double curHeading;

    public DrivebaseSubsystem(IMU imu, Motor flMotor, Motor frMotor, Motor rlMotor, Motor rrMotor) {
        super(() -> imu.gyroHeading(), flMotor, frMotor, rlMotor, rrMotor);
        curHeading = imu.gyroHeading();
    }

    @Override
    public void periodic() {
        curHeading = this.getGyro();
    }
}
