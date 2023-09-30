package org.firstinspires.ftc.sixteen750.opmodes.auto;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.technototes.library.command.CommandScheduler;
import com.technototes.library.command.SequentialCommandGroup;
import com.technototes.library.structure.CommandOpMode;
import com.technototes.library.util.Alliance;
import org.firstinspires.ftc.sixteen750.AutoConstants;
import org.firstinspires.ftc.sixteen750.Hardware;
import org.firstinspires.ftc.sixteen750.Robot;
import org.firstinspires.ftc.sixteen750.Setup;
import org.firstinspires.ftc.sixteen750.commands.VisionCommand;
import org.firstinspires.ftc.sixteen750.commands.auto.red.WingPixelSelection;
import org.firstinspires.ftc.sixteen750.controls.DriverController;
import org.firstinspires.ftc.sixteen750.helpers.StartingPosition;

// The last 4 weird things are '🟥' and '🪶' (wing)
@Autonomous(name = "PixelThenPark\uD83D\uDFE5\uD83E\uDEB6")
@SuppressWarnings("unused")
public class PixelThenParkRedWing extends CommandOpMode {

    public Robot robot;
    public DriverController controls;
    public Hardware hardware;

    @Override
    public void uponInit() {
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        hardware = new Hardware(hardwareMap);
        robot = new Robot(hardware, Alliance.RED, StartingPosition.Wing);
        robot.drivebase.setPoseEstimate(AutoConstants.WingRed.START.toPose());
        CommandScheduler
            .getInstance()
            .scheduleForState(
                new SequentialCommandGroup(
                    new WingPixelSelection(robot),
                    CommandScheduler.getInstance()::terminateOpMode
                ),
                OpModeState.RUN
            );
        if (Setup.Connected.WEBCAM) {
            CommandScheduler.getInstance().scheduleInit(new VisionCommand(robot.vision));
        }
    }
}
