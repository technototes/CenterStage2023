package org.firstinspires.ftc.sixteen750.commands.auto.blue;

import com.technototes.library.command.CommandScheduler;
import com.technototes.library.command.SequentialCommandGroup;

import org.firstinspires.ftc.sixteen750.Robot;

public class BlueStageParkCorner extends SequentialCommandGroup {

    public BlueStageParkCorner(Robot robot) {
        super(
            new StagePixelPlaceSelection(robot),
            new ParkCornerPositioning(robot),
            new ParkCorner(robot, robot.position),
            CommandScheduler.getInstance()::terminateOpMode
        );
    }
}
