package org.firstinspires.ftc.sixteen750;

import com.technototes.library.logger.Loggable;
import com.technototes.library.util.Alliance;
import org.firstinspires.ftc.sixteen750.helpers.StartingPosition;
import org.firstinspires.ftc.sixteen750.subsystems.DrivebaseSubsystem;
import org.firstinspires.ftc.sixteen750.subsystems.IntakeSubsystem;

public class Robot implements Loggable {

    public StartingPosition position;
    public Alliance alliance;

    public double initialVoltage;

    public IntakeSubsystem intake;
    public DrivebaseSubsystem drivebase;

    public Robot(Hardware hw, Alliance team, StartingPosition pos) {
        this.position = pos;
        this.alliance = team;
        this.initialVoltage = hw.voltage();
        intake = new IntakeSubsystem(hw.leftIntake, hw.rightIntake);
        drivebase = new DrivebaseSubsystem(hw.fl, hw.fr, hw.rl, hw.rr, hw.imu);
    }
}
