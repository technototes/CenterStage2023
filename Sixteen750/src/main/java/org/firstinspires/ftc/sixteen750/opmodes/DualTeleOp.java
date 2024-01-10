package org.firstinspires.ftc.sixteen750.opmodes;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.technototes.library.command.CommandScheduler;
import com.technototes.library.command.SequentialCommandGroup;
import com.technototes.library.structure.CommandOpMode;
import com.technototes.library.util.Alliance;
import org.firstinspires.ftc.sixteen750.AutoConstants;
import org.firstinspires.ftc.sixteen750.Hardware;
import org.firstinspires.ftc.sixteen750.Robot;
import org.firstinspires.ftc.sixteen750.Setup;
import org.firstinspires.ftc.sixteen750.commands.DroneStart;
import org.firstinspires.ftc.sixteen750.commands.driving.ResetGyroCommand;
import org.firstinspires.ftc.sixteen750.controls.DriverController;
import org.firstinspires.ftc.sixteen750.controls.OperatorController;
import org.firstinspires.ftc.sixteen750.helpers.StartingPosition;

@TeleOp(name = "Dual Control")
@SuppressWarnings("unused")
public class DualTeleOp extends CommandOpMode {

    public Robot robot;
    public OperatorController controlsOperator;
    public DriverController controlsDriver;
    public Hardware hardware;

    @Override
    public void uponInit() {
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        hardware = new Hardware(hardwareMap);
        robot = new Robot(hardware, Alliance.NONE, StartingPosition.Unspecified);
        controlsOperator = new OperatorController(codriverGamepad, robot);
        if (Setup.Connected.DRIVEBASE) {
            controlsDriver = new DriverController(driverGamepad, robot);
            // Just pick a starting point
            robot.drivebase.setPoseEstimate(AutoConstants.WingRed.START.toPose());
            CommandScheduler
                .getInstance()
                .scheduleForState(
                    new SequentialCommandGroup(
                        new ResetGyroCommand(robot.drivebase)
                    ),
                    OpModeState.INIT
                );
        }
    }
}
