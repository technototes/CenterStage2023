package org.firstinspires.ftc.sixteen750.commands.auto.red;

import com.technototes.library.command.SequentialCommandGroup;
import com.technototes.path.command.TrajectorySequenceCommand;
import org.firstinspires.ftc.sixteen750.AutoConstants.WingRed;
import org.firstinspires.ftc.sixteen750.Robot;

public class WingPixelLeft extends SequentialCommandGroup {

    public WingPixelLeft(Robot r) {
        super(
            new TrajectorySequenceCommand(r.drivebase, WingRed.START_TO_LEFT_SPIKE)
                .andThen(new TrajectorySequenceCommand(r.drivebase, WingRed.LEFT_SPIKE_TO_CLEAR))
        );
    }
}
