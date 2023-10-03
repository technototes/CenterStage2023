package org.firstinspires.ftc.sixteen750.commands.auto.blue;

import com.technototes.library.command.SequentialCommandGroup;
import com.technototes.path.command.TrajectorySequenceCommand;
import org.firstinspires.ftc.sixteen750.AutoConstants.WingBlue;
import org.firstinspires.ftc.sixteen750.Robot;

public class WingPixelRight extends SequentialCommandGroup {

    public WingPixelRight(Robot r) {
        super(
            new TrajectorySequenceCommand(r.drivebase, WingBlue.START_TO_RIGHT_SPIKE)
                .andThen(new TrajectorySequenceCommand(r.drivebase, WingBlue.RIGHT_SPIKE_TO_CLEAR))
        );
    }
}
