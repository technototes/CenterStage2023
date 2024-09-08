package org.firstinspires.ftc.twenty403.commands.auto.blue;

import com.technototes.library.command.CommandScheduler;
import com.technototes.library.command.SequentialCommandGroup;
import org.firstinspires.ftc.twenty403.Robot;
import org.firstinspires.ftc.twenty403.commands.auto.RecordFinalHeading;

public class BlueStageParkCorner extends SequentialCommandGroup {

    public BlueStageParkCorner(Robot robot) {
        super(
            new StagePixelPlaceSelection(robot),
            new ParkCornerPositioning(robot),
            new ParkCorner(robot, robot.position),
            new RecordFinalHeading(robot.drivebaseSubsystem),
            CommandScheduler::terminateOpMode
        );
    }
}
