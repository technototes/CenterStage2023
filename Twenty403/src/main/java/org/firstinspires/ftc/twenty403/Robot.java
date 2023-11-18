package org.firstinspires.ftc.twenty403;

import com.technototes.library.logger.Loggable;
import com.technototes.library.util.Alliance;

import org.firstinspires.ftc.twenty403.helpers.StartingPosition;
import org.firstinspires.ftc.twenty403.subsystems.ArmSubsystem;
import org.firstinspires.ftc.twenty403.subsystems.DrivebaseSubsystem;
import org.firstinspires.ftc.twenty403.subsystems.DroneSubsystem;
import org.firstinspires.ftc.twenty403.subsystems.HangSubsystem;
import org.firstinspires.ftc.twenty403.subsystems.TwoDeadWheelLocalizer;
import org.firstinspires.ftc.twenty403.subsystems.VisionSubsystem;

public class Robot implements Loggable {

    public StartingPosition position;
    public Alliance alliance;
    public double initialVoltage;

    public DrivebaseSubsystem drivebaseSubsystem;
    public ArmSubsystem clawSubsystem;
    public VisionSubsystem vision;
    public HangSubsystem hangSubsystem;
    public DroneSubsystem droneSubsystem;

    public TwoDeadWheelLocalizer localizer;

    public Robot(Hardware hw, Alliance team, StartingPosition pos) {
        this.position = pos;
        this.alliance = team;
        this.initialVoltage = hw.voltage();
        if (Setup.Connected.ODOSUBSYSTEM) {
            this.localizer = new TwoDeadWheelLocalizer(hw.odoR, hw.odoF);
        } else {
            this.localizer = null;
        }
        if (Setup.Connected.DRIVEBASE) {
            this.drivebaseSubsystem =
                new DrivebaseSubsystem(hw.fl, hw.fr, hw.rl, hw.rr, hw.imu, localizer);
            if (localizer != null) {
                // YOU MUST CALL THIS IMMEDIATELY AFTER CREATING THE DRIVEBASE!
                localizer.setDrivebase(this.drivebaseSubsystem);
            }
        }
        if (Setup.Connected.WEBCAM) {
            this.vision = new VisionSubsystem(hw.camera, team, pos);
        } else {
            this.vision = new VisionSubsystem();
        }
        if (Setup.Connected.ARMSUBSYSTEM) {
            this.clawSubsystem = new ArmSubsystem(hw.clawServo, hw.shoulderMotor, hw.elbowMotor);
        } else {
            this.clawSubsystem = new ArmSubsystem();
        }
        if (Setup.Connected.HANGSUBSYSTEM) {
            this.hangSubsystem = new HangSubsystem(hw);
        } else {
            this.hangSubsystem = new HangSubsystem();
        }
        if (Setup.Connected.DRONESUBSYSTEM) {
            this.droneSubsystem = new DroneSubsystem(hw.launchServo);
        } else {
            this.droneSubsystem = new DroneSubsystem();
        }
    }
}
