package org.firstinspires.ftc.sixteen750.commands.auto.red;

import com.technototes.library.command.CommandScheduler;
import com.technototes.library.command.SequentialCommandGroup;

import org.firstinspires.ftc.sixteen750.Robot;

public class RedStageParkCenter extends SequentialCommandGroup {

    public RedStageParkCenter(Robot robot) {
        super(
            new StagePixelPlaceSelection(robot),
            new ParkCenter(robot, robot.position),
            new RecordFinalHeading(robot.drivebase),
            CommandScheduler.getInstance()::terminateOpMode
        );
    }
}
