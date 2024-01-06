package org.firstinspires.ftc.sixteen750.commands.auto.red;

import com.technototes.library.command.CommandScheduler;
import com.technototes.library.command.SequentialCommandGroup;

import org.firstinspires.ftc.sixteen750.Robot;

public class RedWingParkCenter extends SequentialCommandGroup {

    public RedWingParkCenter(Robot robot) {
        super(
            new WingPixelPlaceSelection(robot),
            new ParkCenterPositioning(robot),
            new ParkCenter(robot, robot.position),
            new RecordFinalHeading(robot.drivebaseSubsystem),
            CommandScheduler.getInstance()::terminateOpMode
        );
    }
}
