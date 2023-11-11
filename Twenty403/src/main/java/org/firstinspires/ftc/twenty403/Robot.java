package org.firstinspires.ftc.twenty403;

import com.technototes.library.logger.Loggable;
import com.technototes.library.util.Alliance;
import java.util.Set;
import org.firstinspires.ftc.twenty403.helpers.StartingPosition;
import org.firstinspires.ftc.twenty403.subsystems.ClawSubsystem;
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
    public ClawSubsystem clawSubsystem;
    public VisionSubsystem vision;
    public HangSubsystem hangSubsystem;
    public DroneSubsystem droneSubsystem;

    public TwoDeadWheelLocalizer localizer;

    public Robot(Hardware hw, Alliance team, StartingPosition pos) {
        this.position = pos;
        this.alliance = team;
        this.initialVoltage = hw.voltage();
        if (Setup.Connected.DRIVEBASE) {
            this.drivebaseSubsystem = new DrivebaseSubsystem(hw.fl, hw.fr, hw.rl, hw.rr, hw.imu);
        }
        if (Setup.Connected.WEBCAM) {
            this.vision = new VisionSubsystem(hw.camera, team, pos);
        } else {
            this.vision = new VisionSubsystem();
        }
        if (Setup.Connected.CLAWSUBSYSTEM) {
            this.clawSubsystem = new ClawSubsystem(hw.clawServo, hw.swingMotor);
        } else {
            this.clawSubsystem = new ClawSubsystem();
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
        if (Setup.Connected.ODOSUBSYSTEM) {
            this.localizer = new TwoDeadWheelLocalizer(hw.odoR, hw.odoF, hw.imu);
        }
    }
}
