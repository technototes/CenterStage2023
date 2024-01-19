package org.firstinspires.ftc.learnbot.opmodes;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.technototes.library.logger.Loggable;
import com.technototes.library.structure.CommandOpMode;
import org.firstinspires.ftc.learnbot.ClawAndWristBot;
import org.firstinspires.ftc.learnbot.controllers.ClawAndWristController;

@TeleOp(name = "Claw And Wrist")
public class ClawAndWristTele extends CommandOpMode implements Loggable {

    ClawAndWristBot cawbot;
    ClawAndWristController controller;

    @Override
    public void uponInit() {
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        cawbot = new ClawAndWristBot();
        controller = new ClawAndWristController(driverGamepad, cawbot);
    }
}
