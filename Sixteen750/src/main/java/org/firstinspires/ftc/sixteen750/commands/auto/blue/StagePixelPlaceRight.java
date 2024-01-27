package org.firstinspires.ftc.sixteen750.commands.auto.blue;

import com.technototes.library.command.SequentialCommandGroup;
import com.technototes.path.command.TrajectorySequenceCommand;
import org.firstinspires.ftc.sixteen750.AutoConstants;
import org.firstinspires.ftc.sixteen750.Robot;
import org.firstinspires.ftc.sixteen750.commands.auto.PixelScoring;

public class StagePixelPlaceRight extends SequentialCommandGroup {

    public StagePixelPlaceRight(Robot r) {
        super(
            new TrajectorySequenceCommand(r.drivebase, AutoConstants.StageBlue.START_TO_MID_CLEAR),
            new TrajectorySequenceCommand(
                r.drivebase,
                AutoConstants.StageBlue.MID_CLEAR_TO_RIGHT_SPIKE
            ),
            new TrajectorySequenceCommand(
                r.drivebase,
                AutoConstants.StageBlue.RIGHT_SPIKE_TO_MID_CLEAR
            ),
            new TrajectorySequenceCommand(r.drivebase, AutoConstants.StageBlue.MID_CLEAR_TO_CLEAR),
            new TrajectorySequenceCommand(r.drivebase, AutoConstants.StageBlue.CLEAR_TO_LEFT_CLEAR),
            new TrajectorySequenceCommand(
                r.drivebase,
                AutoConstants.StageBlue.LEFT_CLEAR_TO_PLACE_RIGHT
            ),
            //place command
            new PixelScoring(r.placement),
            new TrajectorySequenceCommand(
                r.drivebase,
                AutoConstants.StageBlue.PLACE_RIGHT_TO_MID_PARK_CENTER
            )
        );
    }
}
