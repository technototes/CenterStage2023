package org.firstinspires.ftc.sixteen750.commands.auto.blue;

import com.technototes.library.command.SequentialCommandGroup;
import com.technototes.path.command.TrajectorySequenceCommand;

import org.firstinspires.ftc.sixteen750.AutoConstants;
import org.firstinspires.ftc.sixteen750.Robot;

public class StagePlaceLeftTrajectory extends SequentialCommandGroup {
    public StagePlaceLeftTrajectory (Robot r){
        super(new TrajectorySequenceCommand(r.drivebase, AutoConstants.StageBlue.START_TO_LEFT_SPIKE),
                new TrajectorySequenceCommand(r.drivebase, AutoConstants.StageBlue.LEFT_SPIKE_TO_CLEAR),
                new TrajectorySequenceCommand(r.drivebase, AutoConstants.StageBlue.CLEAR_TO_LEFT_CLEAR),
                new TrajectorySequenceCommand(r.drivebase, AutoConstants.StageBlue.LEFT_CLEAR_TO_PLACE_LEFT)
        );
    }
}
