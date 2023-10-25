package org.firstinspires.ftc.twenty403.commands.auto.red;

import com.technototes.library.command.SequentialCommandGroup;
import com.technototes.path.command.TrajectorySequenceCommand;

import org.firstinspires.ftc.twenty403.AutoConstants;
import org.firstinspires.ftc.twenty403.Robot;

public class StagePixelLeft extends SequentialCommandGroup {

    public StagePixelLeft(Robot r) {
        super(
            new TrajectorySequenceCommand(r.drivebaseSubsystem, AutoConstants.StageRed.START_TO_MID_CLEAR)
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
