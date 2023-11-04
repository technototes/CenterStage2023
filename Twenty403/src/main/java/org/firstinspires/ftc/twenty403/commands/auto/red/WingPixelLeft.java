package org.firstinspires.ftc.twenty403.commands.auto.red;

import com.technototes.library.command.SequentialCommandGroup;
import com.technototes.path.command.TrajectorySequenceCommand;

import org.firstinspires.ftc.twenty403.AutoConstants;
import org.firstinspires.ftc.twenty403.AutoConstants.WingRed;
import org.firstinspires.ftc.twenty403.Robot;

public class WingPixelLeft extends SequentialCommandGroup {

    public WingPixelLeft(Robot r) {
        super(
            new TrajectorySequenceCommand(r.drivebaseSubsystem, WingRed.START_TO_LEFT_SPIKE)
                .andThen(
                    new TrajectorySequenceCommand(r.drivebaseSubsystem, WingRed.LEFT_SPIKE_TO_CLEAR)
                )
                    .andThen(
                            new TrajectorySequenceCommand(
                                    r.drivebaseSubsystem,
                                    AutoConstants.WingRed.CLEAR_TO_PARK_CORNER
                            )
                    )
        );

    }
}
