package org.firstinspires.ftc.twenty403.opmodes;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.technototes.library.command.CommandScheduler;
import com.technototes.library.structure.CommandOpMode;
import com.technototes.library.util.Alliance;

import org.firstinspires.ftc.twenty403.AutoConstants;
import org.firstinspires.ftc.twenty403.Hardware;
import org.firstinspires.ftc.twenty403.Robot;
import org.firstinspires.ftc.twenty403.Setup;
import org.firstinspires.ftc.twenty403.commands.driving.ResetGyroCommand;
import org.firstinspires.ftc.twenty403.controls.SingleDriver;
import org.firstinspires.ftc.twenty403.helpers.StartingPosition;

@TeleOp(name = "OneDriverTeleOp")
@SuppressWarnings("unused")
public class SingleDriverTeleOp extends CommandOpMode {

    public Robot robot;
    public Setup setup;
    public SingleDriver controls;
    public Hardware hardware;

    @Override
    public void uponInit() {
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        hardware = new Hardware(hardwareMap);
        robot = new Robot(hardware, Alliance.NONE, StartingPosition.Unspecified);
        controls = new SingleDriver(driverGamepad, robot, setup);
        robot.drivebaseSubsystem.setPoseEstimate(AutoConstants.WingRed.TELESTART.toPose());
        CommandScheduler
                .getInstance()
                .scheduleForState(new ResetGyroCommand(robot.drivebaseSubsystem), OpModeState.INIT);
    }
}