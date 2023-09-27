package org.firstinspires.ftc.sixteen750.opmodes;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.technototes.library.command.CommandScheduler;
import com.technototes.library.structure.CommandOpMode;
import com.technototes.library.util.Alliance;

import org.firstinspires.ftc.sixteen750.Hardware;
import org.firstinspires.ftc.sixteen750.Robot;
import org.firstinspires.ftc.sixteen750.controls.OperatorController;
import org.firstinspires.ftc.sixteen750.helpers.StartingPosition;

@TeleOp(name = "Just Intake")
@SuppressWarnings("unused")
public class IntakeTeleOp extends CommandOpMode {

    public Robot robot;
    public OperatorController controlsOperator;
    // public ControlOperator controlsOperator;
    public Hardware hardware;

    @Override
    public void uponInit() {
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        hardware = new Hardware(hardwareMap);
        robot = new Robot(hardware, Alliance.BLUE, StartingPosition.Wing);
        controlsOperator = new OperatorController(driverGamepad, robot);
    }
}
