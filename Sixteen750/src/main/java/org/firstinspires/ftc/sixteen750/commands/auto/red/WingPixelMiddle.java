package org.firstinspires.ftc.sixteen750.commands.auto.red;

import com.technototes.library.command.SequentialCommandGroup;
import com.technototes.path.command.TrajectorySequenceCommand;

import org.firstinspires.ftc.sixteen750.AutoConstants;
import org.firstinspires.ftc.sixteen750.Robot;


public class WingPixelMiddle extends SequentialCommandGroup {

    public WingPixelMiddle(Robot r) {
        super(
            new TrajectorySequenceCommand(r.drivebase, AutoConstants.WingRed.START_TO_MIDDLE_SPIKE)
                .andThen(
                    new TrajectorySequenceCommand(
                        r.drivebase,
                        AutoConstants.WingRed.MIDDLE_SPIKE_TO_CLEAR
                    )
                )
                .andThen(
                    new TrajectorySequenceCommand(
                        r.drivebase,
                        AutoConstants.WingRed.CLEAR_TO_PARK_CORNER
                    )
                )
        );
    }
}
