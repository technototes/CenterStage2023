package org.firstinspires.ftc.sixteen750.commands.auto.blue;

import com.technototes.library.command.SequentialCommandGroup;
import com.technototes.path.command.TrajectorySequenceCommand;
import org.firstinspires.ftc.sixteen750.AutoConstants;
import org.firstinspires.ftc.sixteen750.Robot;

public class WingPixelRight extends SequentialCommandGroup {

    public WingPixelRight(Robot r) {
        super(
            new TrajectorySequenceCommand(r.drivebase, AutoConstants.WingBlue.START_TO_RIGHT_SPIKE)
                .andThen(
                    new TrajectorySequenceCommand(
                        r.drivebase,
                        AutoConstants.WingBlue.RIGHT_SPIKE_TO_CLEAR
                    )
                )
                .andThen(
                    new TrajectorySequenceCommand(
                        r.drivebase,
                        AutoConstants.WingBlue.CLEAR_TO_PARK_CORNER
                    )
                )
        );
    }
}
