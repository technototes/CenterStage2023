package org.firstinspires.ftc.learnbot.opmodes.auto;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.technototes.library.command.CommandScheduler;
import com.technototes.library.command.SequentialCommandGroup;
import com.technototes.library.command.WaitCommand;
import com.technototes.library.logger.Loggable;
import com.technototes.library.structure.CommandOpMode;
import org.firstinspires.ftc.learnbot.Hardware;
import org.firstinspires.ftc.learnbot.Robot;
import org.firstinspires.ftc.learnbot.Setup;

/*
 * Some Emojis for opmode names:
 * Copy them and paste them in the 'name' section of the @Autonomous tag
 * Red alliance:  🟥
 * Blue alliance: 🟦
 * Wing position: 🪶
 * Backstage pos: 🎦
 */
@Config
@Autonomous(name = "Basic Auto")
@SuppressWarnings("unused")
public class Basic extends CommandOpMode implements Loggable {

    public static int AUTO_TIME = 25;
    public Hardware hardware;
    public Robot robot;

    @Override
    public void uponInit() {
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        hardware = new Hardware(hardwareMap);
        robot = new Robot(hardware);
        CommandScheduler.scheduleForState(
            new SequentialCommandGroup(
                // new TurboCommand(robot.drivebaseSubsystem),
                // new StartSpinningCmd(robot.spinner),
                new WaitCommand(AUTO_TIME),
                // new StopSpinningCmd(robot.spinner),
                CommandScheduler::terminateOpMode
            ),
            CommandOpMode.OpModeState.RUN
        );
    }
}
