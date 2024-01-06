package org.firstinspires.ftc.sixteen750.commands.auto.red;

import com.technototes.library.command.SequentialCommandGroup;
import com.technototes.path.command.TrajectorySequenceCommand;

import org.firstinspires.ftc.sixteen750.AutoConstants.WingRed;
import org.firstinspires.ftc.sixteen750.Robot;

public class WingPixelPlaceMiddle extends SequentialCommandGroup {

    public WingPixelPlaceMiddle(Robot r) {
        super(
            new TrajectorySequenceCommand(r.drivebaseSubsystem, WingRed.START_TO_MIDDLE_SPIKE)
                .andThen(
                    new TrajectorySequenceCommand(
                        r.drivebaseSubsystem,
                        WingRed.MIDDLE_SPIKE_TO_CLEAR
                    )
                )
                .andThen(
                    new TrajectorySequenceCommand(
                        r.drivebaseSubsystem,
                        WingRed.ClEAR_TO_RIGHT_CLEAR
                    )
                )
                .andThen(
                    new TrajectorySequenceCommand(
                        r.drivebaseSubsystem,
                        WingRed.RIGHT_CLEAR_TO_PLACE_MIDDLE
                    )
                )
                .andThen(
                    new TrajectorySequenceCommand(
                        r.drivebaseSubsystem,
                        WingRed.PLACE_MIDDLE_TO_RIGHT_CLEAR
                    )
                )
        );
    }
}
