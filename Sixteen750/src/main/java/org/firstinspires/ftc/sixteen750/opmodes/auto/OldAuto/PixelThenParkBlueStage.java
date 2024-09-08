package org.firstinspires.ftc.sixteen750.opmodes.auto.OldAuto;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.technototes.library.command.CommandScheduler;
import com.technototes.library.command.SequentialCommandGroup;
import com.technototes.library.structure.CommandOpMode;
import com.technototes.library.util.Alliance;
import java.util.Set;
import org.firstinspires.ftc.sixteen750.AutoConstants;
import org.firstinspires.ftc.sixteen750.Hardware;
import org.firstinspires.ftc.sixteen750.Robot;
import org.firstinspires.ftc.sixteen750.Setup;
import org.firstinspires.ftc.sixteen750.commands.VisionCommand;
import org.firstinspires.ftc.sixteen750.commands.auto.blue.StagePixelSelection;
import org.firstinspires.ftc.sixteen750.controls.DriverController;
import org.firstinspires.ftc.sixteen750.helpers.StartingPosition;

// The last 4 weird things are '🟥' and '🪶' (wing)
@Autonomous(name = "PixelThenParkBlueStage")
@SuppressWarnings("unused")
public class PixelThenParkBlueStage extends CommandOpMode {

    public Robot robot;
    public DriverController controls;
    public Hardware hardware;

    @Override
    public void uponInit() {
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        hardware = new Hardware(hardwareMap);
        robot = new Robot(hardware, Alliance.BLUE, StartingPosition.Backstage);
        robot.drivebase.setPoseEstimate(AutoConstants.StageBlue.START.toPose());
        CommandScheduler.scheduleForState(
            new SequentialCommandGroup(
                new StagePixelSelection(robot),
                //                    new RecordFinalHeading(robot.drivebase),
                CommandScheduler::terminateOpMode
            ),
            OpModeState.RUN
        );
        if (Setup.Connected.WEBCAM) {
            CommandScheduler.scheduleInit(new VisionCommand(robot.vision));
        }
    }
}
