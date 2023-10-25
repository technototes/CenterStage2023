package org.firstinspires.ftc.twenty403.commands.auto.blue;

import com.technototes.library.command.SequentialCommandGroup;
import com.technototes.path.command.TrajectorySequenceCommand;

import org.firstinspires.ftc.twenty403.AutoConstants;
import org.firstinspires.ftc.twenty403.Robot;

public class StagePixelRight extends SequentialCommandGroup {

    public StagePixelRight(Robot r) {
        super(
            new TrajectorySequenceCommand(r.drivebaseSubsystem, AutoConstants.StageBlue.START_TO_MID_CLEAR)
                .andThen(
                    new TrajectorySequenceCommand(
                        r.drivebaseSubsystem,
                        AutoConstants.StageBlue.MID_CLEAR_TO_RIGHT_SPIKE
                    )
                )
                .andThen(
                    new TrajectorySequenceCommand(
                        r.drivebaseSubsystem,
                        AutoConstants.StageBlue.RIGHT_SPIKE_TO_MID_CLEAR
                    )
                )
                .andThen(
                    new TrajectorySequenceCommand(
                        r.drivebaseSubsystem,
                        AutoConstants.StageBlue.MID_CLEAR_TO_CLEAR
                    )
                )
                .andThen(
                    new TrajectorySequenceCommand(
                        r.drivebaseSubsystem,
                        AutoConstants.StageBlue.CLEAR_TO_PARK_CENTER
                    )
                )
        );
    }
}
