package org.firstinspires.ftc.learnbot;

import com.technototes.library.logger.Loggable;
import com.technototes.library.util.Alliance;
import org.firstinspires.ftc.learnbot.helpers.StartingPosition;
import org.firstinspires.ftc.learnbot.subsystems.DrivebaseSubsystem;
import org.firstinspires.ftc.learnbot.subsystems.PlacementSubsystem;
import org.firstinspires.ftc.learnbot.subsystems.TestSubsystem;

public class Robot implements Loggable {

    public StartingPosition position;
    public Alliance alliance;
    public double initialVoltage;
    public TestSubsystem test;
    public DrivebaseSubsystem drivebaseSubsystem;

    public PlacementSubsystem placementSubsystem;

    public Robot(Hardware hw) {
        this.initialVoltage = hw.voltage();
        this.test = new TestSubsystem(hw);
        if (Setup.Connected.DRIVEBASE) {
            this.drivebaseSubsystem = new DrivebaseSubsystem(
                hw.flMotor,
                hw.frMotor,
                hw.rlMotor,
                hw.rrMotor,
                hw.imu,
                hw.distanceSensor
            );
        }
        if (Setup.Connected.TESTSUBSYSTEM) {
            this.test = new TestSubsystem(hw);
        }
    }
}
