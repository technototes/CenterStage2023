package org.firstinspires.ftc.sixteen750;

import com.acmerobotics.roadrunner.drive.Drive;
import com.technototes.library.logger.Loggable;
import com.technototes.library.util.Alliance;
import org.firstinspires.ftc.sixteen750.helpers.StartingPosition;
import org.firstinspires.ftc.sixteen750.subsystems.DrivebaseSubsystem;
import org.firstinspires.ftc.sixteen750.subsystems.DroneSubsystem;
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
    public DroneSubsystem Drone;

    public Robot(Hardware hw, Alliance team, StartingPosition pos) {
        this.position = pos;
        this.alliance = team;
        this.initialVoltage = hw.voltage();

        if (Setup.Connected.DRIVEBASE) {
            drivebase = new DrivebaseSubsystem(hw.fl, hw.fr, hw.rl, hw.rr, hw.imu);
        }

        if (Setup.Connected.DRONE) {
            Drone = new DroneSubsystem(hw);
        } else {
            this.Drone = new DroneSubsystem();
        }
        if (Setup.Connected.WEBCAM) {
            this.vision = new VisionSubsystem(hw.camera, team, pos);
        } else {
            this.vision = new VisionSubsystem();
        }
        if (Setup.Connected.PLACEMENT) {
            this.placement = new PlacementSubsystem(hw);
        } else {
            this.placement = new PlacementSubsystem();
        }
        if (Setup.Connected.INTAKE) {
            this.intake = new IntakeSubsystem(hw.leftIntake);
        } else {
            this.intake = new IntakeSubsystem();
        }
        if (Setup.Connected.HANG) {
            this.hang = new HangSubsystem(hw);
        } else {
            this.hang = new HangSubsystem();
        }
    }
}
