package org.firstinspires.ftc.twenty403.commands.auto.red;

import com.technototes.library.command.SequentialCommandGroup;
import com.technototes.path.command.TrajectorySequenceCommand;

import org.firstinspires.ftc.twenty403.AutoConstants;
import org.firstinspires.ftc.twenty403.Robot;
import org.firstinspires.ftc.twenty403.helpers.StartingPosition;

public class ParkCorner extends SequentialCommandGroup {
    public ParkCorner(Robot r, StartingPosition position){
        super(
        new TrajectorySequenceCommand(
                r.drivebaseSubsystem,
                (position == StartingPosition.Wing)
                        ? AutoConstants.WingRed.RIGHT_CLEAR_TO_PARK_CORNER
                        : AutoConstants.StageRed.RIGHT_CLEAR_TO_PARK_CORNER
        )
                );
    }
}
