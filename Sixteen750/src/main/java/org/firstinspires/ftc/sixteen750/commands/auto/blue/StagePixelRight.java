package org.firstinspires.ftc.sixteen750.commands.auto.blue;

import com.technototes.library.command.SequentialCommandGroup;
import com.technototes.path.command.TrajectorySequenceCommand;
import org.firstinspires.ftc.sixteen750.AutoConstants;
import org.firstinspires.ftc.sixteen750.Robot;

public class StagePixelRight extends SequentialCommandGroup {

    public StagePixelRight(Robot r) {
        super(
            new TrajectorySequenceCommand(r.drivebase, AutoConstants.StageBlue.START_TO_RIGHT_SPIKE)
                .andThen(
                    new TrajectorySequenceCommand(
                        r.drivebase,
                        AutoConstants.StageBlue.RIGHT_SPIKE_TO_CLEAR
                    )
                )
                .andThen(
                    new TrajectorySequenceCommand(
                        r.drivebase,
                        AutoConstants.StageBlue.CLEAR_TO_PARK_RIGHT
                    )
                )
        );
    }
}
