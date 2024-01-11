package org.firstinspires.ftc.sixteen750.commands.auto.red;

import com.technototes.library.command.SequentialCommandGroup;
import com.technototes.path.command.TrajectorySequenceCommand;

import org.firstinspires.ftc.sixteen750.AutoConstants;
import org.firstinspires.ftc.sixteen750.Robot;

public class StagePixelPlaceRight extends SequentialCommandGroup {

    public StagePixelPlaceRight(Robot r) {
        super(
                new TrajectorySequenceCommand(r.drivebase, AutoConstants.StageRed.START_TO_RIGHT_SPIKE),
                new TrajectorySequenceCommand(r.drivebase, AutoConstants.StageRed.RIGHT_SPIKE_TO_CLEAR),
                new TrajectorySequenceCommand(r.drivebase, AutoConstants.StageRed.CLEAR_TO_RIGHT_CLEAR),
                new TrajectorySequenceCommand(r.drivebase, AutoConstants.StageRed.RIGHT_CLEAR_TO_PLACE_RIGHT),
                //place command
                new TrajectorySequenceCommand(r.drivebase, AutoConstants.StageRed.PLACE_RIGHT_TO_MID_PARK_CENTER)
        );
    }
}
