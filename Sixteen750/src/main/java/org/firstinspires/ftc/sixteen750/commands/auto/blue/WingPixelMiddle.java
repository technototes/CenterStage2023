package org.firstinspires.ftc.sixteen750.commands.auto.blue;

import com.technototes.library.command.SequentialCommandGroup;
import com.technototes.path.command.TrajectorySequenceCommand;
import org.firstinspires.ftc.sixteen750.AutoConstants.WingBlue;
import org.firstinspires.ftc.sixteen750.Robot;

public class WingPixelMiddle extends SequentialCommandGroup {

    public WingPixelMiddle(Robot r) {
        super(
            new TrajectorySequenceCommand(r.drivebase, WingBlue.START_TO_MIDDLE_SPIKE)
                .andThen(new TrajectorySequenceCommand(r.drivebase, WingBlue.MIDDLE_SPIKE_TO_CLEAR))
                .andThen(new TrajectorySequenceCommand(r.drivebase, WingBlue.CLEAR_TO_PARK_CORNER))
        );
    }
}
