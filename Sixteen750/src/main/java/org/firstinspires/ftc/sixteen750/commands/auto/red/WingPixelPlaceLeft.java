package org.firstinspires.ftc.sixteen750.commands.auto.red;

import com.technototes.library.command.SequentialCommandGroup;
import com.technototes.path.command.TrajectorySequenceCommand;
import org.firstinspires.ftc.sixteen750.AutoConstants.WingRed;
import org.firstinspires.ftc.sixteen750.Robot;
import org.firstinspires.ftc.sixteen750.commands.auto.PixelScoring;

public class WingPixelPlaceLeft extends SequentialCommandGroup {

    public WingPixelPlaceLeft(Robot r) {
        super(
            new TrajectorySequenceCommand(r.drivebase, WingRed.START_TO_LEFT_SPIKE),
            new TrajectorySequenceCommand(r.drivebase, WingRed.LEFT_SPIKE_TO_CLEAR),
            new TrajectorySequenceCommand(r.drivebase, WingRed.ClEAR_TO_RIGHT_CLEAR),
            new TrajectorySequenceCommand(r.drivebase, WingRed.RIGHT_CLEAR_TO_PLACE_LEFT),
            // place command
            new PixelScoring(r.placement),
            new TrajectorySequenceCommand(r.drivebase, WingRed.PLACE_LEFT_TO_RIGHT_CLEAR)
        );
    }
}
