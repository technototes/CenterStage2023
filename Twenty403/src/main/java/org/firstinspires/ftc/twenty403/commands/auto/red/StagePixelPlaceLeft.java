package org.firstinspires.ftc.twenty403.commands.auto.red;

import com.technototes.library.command.SequentialCommandGroup;
import com.technototes.path.command.TrajectorySequenceCommand;

import org.firstinspires.ftc.twenty403.AutoConstants;
import org.firstinspires.ftc.twenty403.Robot;

public class StagePixelPlaceLeft extends SequentialCommandGroup {

    public StagePixelPlaceLeft(Robot r) {
        super(
            new TrajectorySequenceCommand(
                r.drivebaseSubsystem,
                AutoConstants.StageRed.START_TO_MID_CLEAR
            )
                .andThen(
                    new TrajectorySequenceCommand(
                        r.drivebaseSubsystem,
                        AutoConstants.StageRed.MID_CLEAR_TO_LEFT_SPIKE
                    )
                )
                .andThen(
                    new TrajectorySequenceCommand(
                        r.drivebaseSubsystem,
                        AutoConstants.StageRed.LEFT_SPIKE_TO_MID_CLEAR
                    )
                )
                .andThen(
                    new TrajectorySequenceCommand(
                        r.drivebaseSubsystem,
                        AutoConstants.StageRed.MID_CLEAR_TO_CLEAR
                    )
                )
                .andThen(
                    new TrajectorySequenceCommand(
                        r.drivebaseSubsystem,
                        AutoConstants.StageRed.CLEAR_TO_RIGHT_CLEAR
                    )
                )
                .andThen(
                    new TrajectorySequenceCommand(
                        r.drivebaseSubsystem,
                        AutoConstants.StageRed.RIGHT_CLEAR_TO_PLACE_LEFT
                    )
                )
                    .andThen(
                            new TrajectorySequenceCommand(
                                    r.drivebaseSubsystem,
                                    AutoConstants.StageRed.PLACE_LEFT_TO_MID_PARK_CENTER
                            )
                    )

        );
    }
}
