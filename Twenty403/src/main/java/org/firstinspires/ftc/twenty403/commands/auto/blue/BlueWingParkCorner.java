package org.firstinspires.ftc.twenty403.commands.auto.blue;

import com.technototes.library.command.CommandScheduler;
import com.technototes.library.command.SequentialCommandGroup;

import org.firstinspires.ftc.twenty403.Robot;
import org.firstinspires.ftc.twenty403.commands.auto.RecordFinalHeading;
import org.firstinspires.ftc.twenty403.commands.auto.red.WingPixelPlaceSelection;

public class BlueWingParkCorner extends SequentialCommandGroup {
    public BlueWingParkCorner(Robot robot){
        super(new WingPixelPlaceSelection(robot),
                new ParkCorner(robot, robot.position),
                new RecordFinalHeading(robot.drivebaseSubsystem),
                CommandScheduler.getInstance()::terminateOpMode
        );
    }
}
