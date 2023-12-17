package org.firstinspires.ftc.twenty403.commands.auto.blue;

import com.technototes.library.command.SequentialCommandGroup;
import com.technototes.path.command.TrajectorySequenceCommand;
import org.firstinspires.ftc.twenty403.AutoConstants.WingBlue;
import org.firstinspires.ftc.twenty403.Robot;

public class WingPixelPlaceMiddle extends SequentialCommandGroup {

    public WingPixelPlaceMiddle(Robot r) {
        super(
            new TrajectorySequenceCommand(r.drivebaseSubsystem, WingBlue.START_TO_MIDDLE_SPIKE)
                .andThen(
                    new TrajectorySequenceCommand(
                        r.drivebaseSubsystem,
                        WingBlue.MIDDLE_SPIKE_TO_CLEAR
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
                        WingBlue.LEFT_CLEAR_TO_PLACE_MIDDLE
                    )
                )
                .andThen(
                    new TrajectorySequenceCommand(
                        r.drivebaseSubsystem,
                        WingBlue.PLACE_MIDDLE_TO_LEFT_CLEAR
                    )
                )
        );
    }
}
