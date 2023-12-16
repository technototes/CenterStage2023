package org.firstinspires.ftc.twenty403.commands.auto.blue;

import com.technototes.library.command.SequentialCommandGroup;
import com.technototes.library.util.Alliance;
import com.technototes.path.command.TrajectorySequenceCommand;

import org.firstinspires.ftc.twenty403.AutoConstants;
import org.firstinspires.ftc.twenty403.Robot;
import org.firstinspires.ftc.twenty403.helpers.StartingPosition;
import org.firstinspires.ftc.twenty403.subsystems.VisionPipeline;

public class ParkCorner extends SequentialCommandGroup {
    public ParkCorner(Robot r, StartingPosition position) {
        super(

                new TrajectorySequenceCommand(
                        r.drivebaseSubsystem,
                        (position == StartingPosition.Wing)
                                ? AutoConstants.WingBlue.LEFT_CLEAR_TO_PARK_CORNER
                                : AutoConstants.StageBlue.LEFT_CLEAR_TO_PARK_CORNER
                )
        );
    }
}
