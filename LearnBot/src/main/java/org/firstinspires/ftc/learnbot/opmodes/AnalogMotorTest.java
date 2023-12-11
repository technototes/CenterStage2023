package org.firstinspires.ftc.learnbot.opmodes;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.technototes.library.logger.Loggable;
import com.technototes.library.structure.CommandOpMode;
import org.firstinspires.ftc.learnbot.Hardware;
import org.firstinspires.ftc.learnbot.OnlyMotorRobot;
import org.firstinspires.ftc.learnbot.Robot;
import org.firstinspires.ftc.learnbot.Setup;
import org.firstinspires.ftc.learnbot.controllers.DriverController;
import org.firstinspires.ftc.learnbot.controllers.MotorController;

@TeleOp(name = "Analog Motor Test")
public class AnalogMotorTest extends CommandOpMode implements Loggable {

    public Hardware hardware;
    public OnlyMotorRobot robot;
    public MotorController driver;

    @Override
    public void uponInit() {
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        hardware = new Hardware(hardwareMap);
        robot = new OnlyMotorRobot(hardware);
        if (Setup.Connected.MOTOR) {
            driver = new MotorController(driverGamepad, robot);
        }
    }
}
