package org.firstinspires.ftc.twenty403.commands.auto.red;

import com.technototes.library.command.SequentialCommandGroup;
import com.technototes.path.command.TrajectorySequenceCommand;
import org.firstinspires.ftc.twenty403.AutoConstants.WingRed;
import org.firstinspires.ftc.twenty403.Robot;

public class WingPixelRight extends SequentialCommandGroup {

    public WingPixelRight(Robot r) {
        super(
            new TrajectorySequenceCommand(r.drivebaseSubsystem, WingRed.START_TO_RIGHT_STRIKE)
                .andThen(
                    new TrajectorySequenceCommand(
                        r.drivebaseSubsystem,
                        WingRed.RIGHT_STRIKE_TO_CLEAR
                    )
                )
        );
    }
}
