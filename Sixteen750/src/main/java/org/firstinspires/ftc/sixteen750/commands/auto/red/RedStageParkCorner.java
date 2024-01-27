package org.firstinspires.ftc.sixteen750.commands.auto.red;

import com.technototes.library.command.CommandScheduler;
import com.technototes.library.command.SequentialCommandGroup;
import org.firstinspires.ftc.sixteen750.Robot;

public class RedStageParkCorner extends SequentialCommandGroup {

    public RedStageParkCorner(Robot robot) {
        super(
            new StagePixelPlaceSelection(robot),
            new ParkCornerPositioning(robot),
            new ParkCorner(robot, robot.position),
            new RecordFinalHeading(robot.drivebase),
            CommandScheduler::terminateOpMode
        );
    }
}
