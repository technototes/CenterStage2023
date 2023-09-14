package org.firstinspires.ftc.learnbot.opmodes;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.technototes.library.command.CommandScheduler;
import com.technototes.library.command.SequentialCommandGroup;
import com.technototes.library.command.WaitCommand;
import com.technototes.library.structure.CommandOpMode;
import com.technototes.library.util.Alliance;
import org.firstinspires.ftc.learnbot.Hardware;
import org.firstinspires.ftc.learnbot.Robot;
import org.firstinspires.ftc.learnbot.StartingPosition;
import org.firstinspires.ftc.learnbot.commands.StartSpinningCmd;
import org.firstinspires.ftc.learnbot.commands.StopSpinningCmd;

@Autonomous(name = "BasicAuto")
@SuppressWarnings("unused")
public class BasicAuto extends CommandOpMode {

    Hardware hardware;
    Robot robot;

    @Override
    public void uponInit() {
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        hardware = new Hardware(hardwareMap);
        robot = new Robot(hardware, Alliance.NONE, StartingPosition.Backstage);
        CommandScheduler
            .getInstance()
            .scheduleForState(
                new SequentialCommandGroup(
                    //new TurboCommand(robot.drivebaseSubsystem),
                    new StartSpinningCmd(robot.spinner),
                    new WaitCommand(15),
                    new StopSpinningCmd(robot.spinner),
                    CommandScheduler.getInstance()::terminateOpMode
                ),
                CommandOpMode.OpModeState.RUN
            );
    }
}
