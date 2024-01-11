package org.firstinspires.ftc.sixteen750.commands.auto.blue;

import com.technototes.library.command.SequentialCommandGroup;
import com.technototes.path.command.TrajectorySequenceCommand;

import org.firstinspires.ftc.sixteen750.AutoConstants.WingBlue;
import org.firstinspires.ftc.sixteen750.Robot;

public class WingPixelPlaceLeft extends SequentialCommandGroup {

    public WingPixelPlaceLeft(Robot r) {
        super(
                new TrajectorySequenceCommand(r.drivebase, WingBlue.START_TO_MID_CLEAR),
                new TrajectorySequenceCommand(r.drivebase, WingBlue.MID_CLEAR_TO_LEFT_SPIKE),
                new TrajectorySequenceCommand(r.drivebase, WingBlue.LEFT_SPIKE_TO_MID_CLEAR),
                new TrajectorySequenceCommand(r.drivebase, WingBlue.MID_CLEAR_TO_CLEAR),
                new TrajectorySequenceCommand(r.drivebase, WingBlue.ClEAR_TO_LEFT_CLEAR),
                new TrajectorySequenceCommand(r.drivebase, WingBlue.LEFT_CLEAR_TO_PLACE_LEFT),
                //place command
                new TrajectorySequenceCommand(r.drivebase, WingBlue.PLACE_LEFT_TO_LEFT_CLEAR)
        );
    }
}
