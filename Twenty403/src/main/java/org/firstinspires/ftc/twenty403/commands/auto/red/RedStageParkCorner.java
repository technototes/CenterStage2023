package org.firstinspires.ftc.twenty403.commands.auto.red;

import com.technototes.library.command.CommandScheduler;
import com.technototes.library.command.SequentialCommandGroup;

import org.firstinspires.ftc.twenty403.Robot;
import org.firstinspires.ftc.twenty403.commands.auto.RecordFinalHeading;
import org.firstinspires.ftc.twenty403.commands.auto.blue.StagePixelPlaceSelection;
import org.firstinspires.ftc.twenty403.helpers.StartingPosition;

public class RedStageParkCorner extends SequentialCommandGroup {
    public RedStageParkCorner(Robot robot){
        super(new StagePixelPlaceSelection(robot),
                new ParkCornerPositioning(robot),
                new ParkCorner(robot, robot.position),
                new RecordFinalHeading(robot.drivebaseSubsystem),
                CommandScheduler.getInstance()::terminateOpMode
        );
    }
}
