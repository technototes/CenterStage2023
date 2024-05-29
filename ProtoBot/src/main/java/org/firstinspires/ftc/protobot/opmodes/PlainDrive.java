package org.firstinspires.ftc.protobot.opmodes;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.technototes.library.logger.Loggable;
import com.technototes.library.structure.CommandOpMode;
import org.firstinspires.ftc.protobot.Hardware;
import org.firstinspires.ftc.protobot.Robot;
import org.firstinspires.ftc.protobot.controllers.DriverController;

@TeleOp(name = "Plain Drivebase")
public class PlainDrive extends CommandOpMode implements Loggable {

    public Hardware hardware;
    public Robot robot;

    public DriverController driver;

    @Override
    public void uponInit() {
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        hardware = new Hardware(hardwareMap);
        robot = new Robot(hardware);
        driver = new DriverController(driverGamepad, robot);
    }
}
