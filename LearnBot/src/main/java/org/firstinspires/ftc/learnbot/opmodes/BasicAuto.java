package org.firstinspires.ftc.learnbot.opmodes;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.technototes.library.command.CommandScheduler;
import com.technototes.library.command.SequentialCommandGroup;
import com.technototes.library.command.WaitCommand;
import com.technototes.library.logger.Loggable;
import com.technototes.library.structure.CommandOpMode;
import com.technototes.library.util.Alliance;
import org.firstinspires.ftc.learnbot.Hardware;
import org.firstinspires.ftc.learnbot.Robot;
import org.firstinspires.ftc.learnbot.Setup;
import org.firstinspires.ftc.learnbot.commands.StartSpinningCmd;
import org.firstinspires.ftc.learnbot.commands.StopSpinningCmd;
import org.firstinspires.ftc.learnbot.helpers.StartingPosition;

/*
 * Some Emojis for opmode names:
 * Copy them and paste them in the 'name' section of the @Autonomous tag
 * Red alliance:  🟥
 * Blue alliance: 🟦
 * Wing position: 🪶
 * Backstage pos: 🎦
 */
@Autonomous(name = "Basic Auto")
@SuppressWarnings("unused")
public class BasicAuto extends CommandOpMode implements Loggable {

    public Hardware hardware;
    public Robot robot;

    @Override
    public void uponInit() {
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        hardware = new Hardware(hardwareMap);
        robot = new Robot(hardware);
        CommandScheduler
            .getInstance()
            .scheduleForState(
                new SequentialCommandGroup(
                    //new TurboCommand(robot.drivebaseSubsystem),
                    new StartSpinningCmd(robot.spinner),
                    new WaitCommand(Setup.OtherSettings.AUTOTIME),
                    new StopSpinningCmd(robot.spinner),
                    CommandScheduler.getInstance()::terminateOpMode
                ),
                CommandOpMode.OpModeState.RUN
            );
    }
}
