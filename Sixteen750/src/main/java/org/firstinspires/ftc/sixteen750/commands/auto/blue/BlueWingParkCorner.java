package org.firstinspires.ftc.sixteen750.commands.auto.blue;

import com.technototes.library.command.CommandScheduler;
import com.technototes.library.command.SequentialCommandGroup;

import org.firstinspires.ftc.sixteen750.Robot;

public class BlueWingParkCorner extends SequentialCommandGroup {

    public BlueWingParkCorner(Robot robot) {
        super(
            new WingPixelPlaceSelection(robot),
            new ParkCorner(robot, robot.position),
            CommandScheduler.getInstance()::terminateOpMode
        );
    }
}
