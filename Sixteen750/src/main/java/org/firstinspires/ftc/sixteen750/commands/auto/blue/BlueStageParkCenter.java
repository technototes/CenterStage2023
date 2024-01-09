package org.firstinspires.ftc.sixteen750.commands.auto.blue;

import com.technototes.library.command.CommandScheduler;
import com.technototes.library.command.SequentialCommandGroup;

import org.firstinspires.ftc.sixteen750.Robot;


public class BlueStageParkCenter extends SequentialCommandGroup {

    public BlueStageParkCenter(Robot robot) {
        super(
            new StagePixelPlaceSelection(robot),
            new ParkCenter(robot, robot.position),
            CommandScheduler.getInstance()::terminateOpMode
        );
    }
}
