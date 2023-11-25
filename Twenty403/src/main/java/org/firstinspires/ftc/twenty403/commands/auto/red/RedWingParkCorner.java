package org.firstinspires.ftc.twenty403.commands.auto.red;

import com.technototes.library.command.CommandScheduler;
import com.technototes.library.command.SequentialCommandGroup;
import org.firstinspires.ftc.twenty403.Robot;

public class RedWingParkCorner extends SequentialCommandGroup {

    public RedWingParkCorner(Robot robot) {
        super(
            new WingPixelPlaceSelection(robot),
            new ParkCorner(robot, robot.position),
            new RecordFinalHeadingRed(robot.drivebaseSubsystem),
            CommandScheduler.getInstance()::terminateOpMode
        );
    }
}
