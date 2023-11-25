package org.firstinspires.ftc.twenty403.commands.auto.blue;

import com.technototes.library.command.CommandScheduler;
import com.technototes.library.command.SequentialCommandGroup;
import org.firstinspires.ftc.twenty403.Robot;

public class BlueWingParkCenter extends SequentialCommandGroup {

    public BlueWingParkCenter(Robot robot) {
        super(
            new WingPixelPlaceSelection(robot),
            new ParkCenterPositioning(robot),
            new ParkCenter(robot, robot.position),
            new RecordFinalHeadingBlue(robot.drivebaseSubsystem),
            CommandScheduler.getInstance()::terminateOpMode
        );
    }
}
