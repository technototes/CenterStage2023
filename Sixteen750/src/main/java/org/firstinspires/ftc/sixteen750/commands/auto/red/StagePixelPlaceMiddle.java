package org.firstinspires.ftc.sixteen750.commands.auto.red;

import com.technototes.library.command.SequentialCommandGroup;
import com.technototes.path.command.TrajectorySequenceCommand;
import org.firstinspires.ftc.sixteen750.AutoConstants;
import org.firstinspires.ftc.sixteen750.Robot;
import org.firstinspires.ftc.sixteen750.commands.auto.PixelScoring;

public class StagePixelPlaceMiddle extends SequentialCommandGroup {

    public StagePixelPlaceMiddle(Robot r) {
        super(
            new TrajectorySequenceCommand(
                r.drivebase,
                AutoConstants.StageRed.START_TO_MIDDLE_SPIKE
            ),
            new TrajectorySequenceCommand(
                r.drivebase,
                AutoConstants.StageRed.MIDDLE_SPIKE_TO_CLEAR
            ),
            new TrajectorySequenceCommand(r.drivebase, AutoConstants.StageRed.ClEAR_TO_RIGHT_CLEAR),
            new TrajectorySequenceCommand(
                r.drivebase,
                AutoConstants.StageRed.RIGHT_CLEAR_TO_PLACE_MIDDLE
            ),
            //place command
            new PixelScoring(r.placement),
            new TrajectorySequenceCommand(
                r.drivebase,
                AutoConstants.StageRed.PLACE_MIDDLE_TO_MID_PARK_CENTER
            )
        );
    }
}
