package org.firstinspires.ftc.twenty403.commands.auto.blue;

import com.technototes.library.command.SequentialCommandGroup;
import com.technototes.path.command.TrajectorySequenceCommand;
import org.firstinspires.ftc.twenty403.AutoConstants.WingBlue;
import org.firstinspires.ftc.twenty403.Robot;

public class WingPixelMiddle extends SequentialCommandGroup {

    public WingPixelMiddle(Robot r) {
        super(
            new TrajectorySequenceCommand(r.drivebaseSubsystem, WingBlue.START_TO_MIDDLE_STRIKE)
                .andThen(
                    new TrajectorySequenceCommand(
                        r.drivebaseSubsystem,
                        WingBlue.MIDDLE_STRIKE_TO_CLEAR
                    )
                )
        );
    }
}
