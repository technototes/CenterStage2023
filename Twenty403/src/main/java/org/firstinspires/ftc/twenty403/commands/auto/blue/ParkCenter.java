package org.firstinspires.ftc.twenty403.commands.auto.blue;

import com.technototes.library.command.SequentialCommandGroup;
import com.technototes.path.command.TrajectorySequenceCommand;

import org.firstinspires.ftc.twenty403.AutoConstants;
import org.firstinspires.ftc.twenty403.Robot;
import org.firstinspires.ftc.twenty403.helpers.StartingPosition;

public class ParkCenter extends SequentialCommandGroup {
    public ParkCenter(Robot r, StartingPosition position){
        super(
                        new TrajectorySequenceCommand(
                                r.drivebaseSubsystem,
                                (position == StartingPosition.Wing)
                                        ? AutoConstants.WingBlue.MID_PARK_CENTER_TO_PARK_CENTER
                                        : AutoConstants.StageBlue.MID_PARK_CENTER_TO_PARK_CENTER
                        )
                );
    }
}
