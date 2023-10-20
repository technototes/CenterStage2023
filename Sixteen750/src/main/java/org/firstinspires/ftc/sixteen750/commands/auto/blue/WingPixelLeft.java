package org.firstinspires.ftc.sixteen750.commands.auto.blue;

import com.technototes.library.command.SequentialCommandGroup;
import com.technototes.path.command.TrajectorySequenceCommand;
import org.firstinspires.ftc.sixteen750.AutoConstants.WingBlue;
import org.firstinspires.ftc.sixteen750.Robot;

public class WingPixelLeft extends SequentialCommandGroup {

    public WingPixelLeft(Robot r) {
        super(
            new TrajectorySequenceCommand(r.drivebase, WingBlue.START_TO_MID_CLEAR)
                .andThen(
                    new TrajectorySequenceCommand(r.drivebase, WingBlue.MID_CLEAR_TO_LEFT_SPIKE)
                )
                .andThen(
                    new TrajectorySequenceCommand(r.drivebase, WingBlue.LEFT_SPIKE_TO_MID_CLEAR)
                )
                .andThen(new TrajectorySequenceCommand(r.drivebase, WingBlue.MID_CLEAR_TO_CLEAR))
        );
    }
}
