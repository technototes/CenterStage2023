package org.firstinspires.ftc.learnbot;

import com.technototes.library.logger.Loggable;
import com.technototes.library.util.Alliance;
import org.firstinspires.ftc.learnbot.helpers.StartingPosition;
import org.firstinspires.ftc.learnbot.subsystems.DrivebaseSubsystem;
import org.firstinspires.ftc.learnbot.subsystems.SpinnySubsystem;
import org.firstinspires.ftc.learnbot.subsystems.StuffSubsystem;
import org.firstinspires.ftc.learnbot.subsystems.TestSubsystem;

public class Robot implements Loggable {

    public StartingPosition position;
    public Alliance alliance;
    public double initialVoltage;
    public SpinnySubsystem spinner;
    public StuffSubsystem stuff;
    public TestSubsystem test;
    public DrivebaseSubsystem drivebaseSubsystem;

    public Robot(Hardware hw) {
        this.initialVoltage = hw.voltage();
        this.spinner = new SpinnySubsystem(hw);
        this.stuff = new StuffSubsystem(hw);
        this.test = new TestSubsystem(hw);
        this.drivebaseSubsystem =
            new DrivebaseSubsystem(hw.flMotor, hw.frMotor, hw.rlMotor, hw.rrMotor);
    }
}
