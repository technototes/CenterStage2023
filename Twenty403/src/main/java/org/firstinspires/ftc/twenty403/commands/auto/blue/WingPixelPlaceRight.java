package org.firstinspires.ftc.twenty403.commands.auto.blue;

import com.technototes.library.command.SequentialCommandGroup;
import com.technototes.path.command.TrajectorySequenceCommand;

import org.firstinspires.ftc.twenty403.AutoConstants.WingBlue;
import org.firstinspires.ftc.twenty403.Robot;

public class WingPixelPlaceRight extends SequentialCommandGroup {

    public WingPixelPlaceRight(Robot r) {
        super(
            new TrajectorySequenceCommand(r.drivebaseSubsystem, WingBlue.START_TO_RIGHT_SPIKE)
                .andThen(
                    new TrajectorySequenceCommand(
                        r.drivebaseSubsystem,
                        WingBlue.RIGHT_SPIKE_TO_CLEAR
                    )
                )
                    .andThen(
                            new TrajectorySequenceCommand(
                                    r.drivebaseSubsystem,
                                    WingBlue.ClEAR_TO_LEFT_CLEAR
                            )
                    )
                .andThen(
                    new TrajectorySequenceCommand(
                        r.drivebaseSubsystem,
                        WingBlue.LEFT_CLEAR_TO_PLACE_RIGHT
                    )
                )
                    .andThen(
                            new TrajectorySequenceCommand(
                                    r.drivebaseSubsystem,
                                    WingBlue.PLACE_RIGHT_TO_LEFT_CLEAR
                            )
                    )

        );
    }
}
