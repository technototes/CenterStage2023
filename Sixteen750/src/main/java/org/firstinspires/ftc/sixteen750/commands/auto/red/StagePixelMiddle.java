package org.firstinspires.ftc.sixteen750.commands.auto.red;

import com.technototes.library.command.SequentialCommandGroup;
import com.technototes.path.command.TrajectorySequenceCommand;

import org.firstinspires.ftc.sixteen750.AutoConstants;
import org.firstinspires.ftc.sixteen750.Robot;


public class StagePixelMiddle extends SequentialCommandGroup {

    public StagePixelMiddle(Robot r) {
        super(
            new TrajectorySequenceCommand(
                r.drivebase,
                AutoConstants.StageRed.START_TO_MIDDLE_SPIKE
            )
                .andThen(
                    new TrajectorySequenceCommand(
                        r.drivebase,
                        AutoConstants.StageRed.MIDDLE_SPIKE_TO_CLEAR
                    )
                )
                .andThen(
                    new TrajectorySequenceCommand(
                        r.drivebase,
                        AutoConstants.StageRed.CLEAR_TO_MID_PARK_CENTER
                    )
                )
                .andThen(
                    new TrajectorySequenceCommand(
                        r.drivebase,
                        AutoConstants.StageRed.MID_PARK_CENTER_TO_PARK_CENTER
                    )
                )
        );
    }
}
