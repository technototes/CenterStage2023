package org.firstinspires.ftc.sixteen750;

import com.technototes.library.logger.Loggable;
import com.technototes.library.util.Alliance;
import org.firstinspires.ftc.sixteen750.helpers.StartingPosition;
import org.firstinspires.ftc.sixteen750.subsystems.DrivebaseSubsystem;
import org.firstinspires.ftc.sixteen750.subsystems.HangSubsystem;
import org.firstinspires.ftc.sixteen750.subsystems.IntakeSubsystem;
import org.firstinspires.ftc.sixteen750.subsystems.PlacementSubsystem;
import org.firstinspires.ftc.sixteen750.subsystems.VisionSubsystem;

public class Robot implements Loggable {

    public StartingPosition position;
    public Alliance alliance;

    public double initialVoltage;

    public IntakeSubsystem intake;
    public DrivebaseSubsystem drivebase;
    public VisionSubsystem vision;
    public PlacementSubsystem placement;
    public HangSubsystem hang;

    public Robot(Hardware hw, Alliance team, StartingPosition pos) {
        this.position = pos;
        this.alliance = team;
        this.initialVoltage = hw.voltage();

        if (Setup.Connected.DRIVEBASE) {
            drivebase = new DrivebaseSubsystem(hw.fl, hw.fr, hw.rl, hw.rr, hw.imu);
        }
        if (Setup.Connected.WEBCAM) {
            this.vision = new VisionSubsystem(hw.camera, team, pos);
        }
        if (Setup.Connected.INTAKE) {
            this.intake = new IntakeSubsystem(hw.leftIntake, hw.rightIntake);
        } else {
            this.intake = new IntakeSubsystem();
        }
        if (Setup.Connected.PLACEMENT) {
            this.placement = new PlacementSubsystem(hw);
        } else {
            this.placement = new PlacementSubsystem();
        }
        if (Setup.Connected.HANG) {
            this.hang = new HangSubsystem();
        }
    }
}
