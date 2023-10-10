package org.firstinspires.ftc.sixteen750.commands.auto.red;

import com.technototes.library.command.SequentialCommandGroup;
import com.technototes.path.command.TrajectorySequenceCommand;
import org.firstinspires.ftc.sixteen750.AutoConstants.WingRed;
import org.firstinspires.ftc.sixteen750.Robot;

public class WingPixelRight extends SequentialCommandGroup {

    public WingPixelRight(Robot r) {
        super(
            new TrajectorySequenceCommand(r.drivebase, WingRed.START_TO_RIGHT_SPIKE)
                .andThen(new TrajectorySequenceCommand(r.drivebase, WingRed.RIGHT_SPIKE_TO_CLEAR))
                .andThen(new TrajectorySequenceCommand(r.drivebase, WingRed.CLEAR_TO_PARK_RIGHT))
        );
    }
}
