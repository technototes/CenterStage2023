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
import org.firstinspires.ftc.twenty403.commands.driving.ResetGyroCommand;
import org.firstinspires.ftc.twenty403.controls.DriverController;
import org.firstinspires.ftc.twenty403.helpers.StartingPosition;

@TeleOp(name = "Just Driving")
@SuppressWarnings("unused")
public class JustDrivingTeleOp extends CommandOpMode {

    public Robot robot;
    public DriverController controlsDriver;
    // public ControlOperator controlsOperator;
    public Hardware hardware;

    @Override
    public void uponInit() {
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        hardware = new Hardware(hardwareMap);
        robot = new Robot(hardware, Alliance.BLUE, StartingPosition.Unspecified);
        controlsDriver = new DriverController(driverGamepad, robot);
        // controlsOperator = new ControlOperator(codriverGamepad, robot);
        robot.drivebaseSubsystem.setPoseEstimate(AutoConstants.Wing.Red.START.toPose());
        CommandScheduler
            .getInstance()
            .scheduleForState(new ResetGyroCommand(robot.drivebaseSubsystem), OpModeState.INIT);
    }
}
