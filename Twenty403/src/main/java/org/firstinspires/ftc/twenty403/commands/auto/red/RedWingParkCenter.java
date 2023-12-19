package org.firstinspires.ftc.twenty403.commands.auto.red;

import com.technototes.library.command.CommandScheduler;
import com.technototes.library.command.SequentialCommandGroup;
import org.firstinspires.ftc.twenty403.Robot;
import org.firstinspires.ftc.twenty403.commands.auto.RecordFinalHeading;
import org.firstinspires.ftc.twenty403.helpers.StartingPosition;

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
