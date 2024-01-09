package org.firstinspires.ftc.sixteen750.commands.auto.red;

import com.technototes.library.command.CommandScheduler;
import com.technototes.library.command.SequentialCommandGroup;

import org.firstinspires.ftc.sixteen750.Robot;

public class RedWingParkCorner extends SequentialCommandGroup {

    public RedWingParkCorner(Robot robot) {
        super(
            new WingPixelPlaceSelection(robot),
            new ParkCorner(robot, robot.position),
            new RecordFinalHeading(robot.drivebase),
            CommandScheduler.getInstance()::terminateOpMode
        );
    }
}
