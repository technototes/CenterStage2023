package org.firstinspires.ftc.twenty403.opmodes.auto;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.technototes.library.command.CommandScheduler;
import com.technototes.library.structure.CommandOpMode;
import com.technototes.library.util.Alliance;
import org.firstinspires.ftc.twenty403.AutoConstants;
import org.firstinspires.ftc.twenty403.Hardware;
import org.firstinspires.ftc.twenty403.Robot;
import org.firstinspires.ftc.twenty403.Setup;
import org.firstinspires.ftc.twenty403.commands.VisionCommand;
import org.firstinspires.ftc.twenty403.commands.auto.blue.BlueStageParkCenter;
import org.firstinspires.ftc.twenty403.controls.DriverController;
import org.firstinspires.ftc.twenty403.helpers.StartingPosition;

// The last 4 weird things are '🟥' and '🪶' (wing)
@Autonomous(name = "PixelThenParkCenterBlueStage")
@SuppressWarnings("unused")
public class PixelThenParkCenterBlueStage extends CommandOpMode {

    public Robot robot;
    public DriverController controls;
    public Hardware hardware;

    @Override
    public void uponInit() {
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        hardware = new Hardware(hardwareMap);
        robot = new Robot(hardware, Alliance.BLUE, StartingPosition.Backstage);
        robot.drivebaseSubsystem.setPoseEstimate(AutoConstants.StageBlue.START.toPose());
        CommandScheduler
            .getInstance()
            .scheduleForState(new BlueStageParkCenter(robot), OpModeState.RUN);
        if (Setup.Connected.WEBCAM) {
            CommandScheduler.getInstance().scheduleInit(new VisionCommand(robot.vision));
        }
    }
}
