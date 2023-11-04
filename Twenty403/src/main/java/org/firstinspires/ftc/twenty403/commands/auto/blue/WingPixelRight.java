package org.firstinspires.ftc.twenty403.commands.auto.blue;

import com.technototes.library.command.SequentialCommandGroup;
import com.technototes.path.command.TrajectorySequenceCommand;
import org.firstinspires.ftc.twenty403.AutoConstants.WingBlue;
import org.firstinspires.ftc.twenty403.Robot;

public class WingPixelRight extends SequentialCommandGroup {

    public WingPixelRight(Robot r) {
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
                                    WingBlue.CLEAR_TO_PARK_CORNER
                            )
                    )
        );
    }
}
