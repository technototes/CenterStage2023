package org.firstinspires.ftc.sixteen750.commands.auto.blue;

import com.technototes.library.command.CommandScheduler;
import com.technototes.library.command.SequentialCommandGroup;
import org.firstinspires.ftc.sixteen750.Robot;

public class BlueWingParkCenter extends SequentialCommandGroup {

    public BlueWingParkCenter(Robot robot) {
        super(
            new WingPixelPlaceSelection(robot),
            new ParkCenterPositioning(robot),
            new ParkCenter(robot, robot.position),
            CommandScheduler.getInstance()::terminateOpMode
        );
    }
}
