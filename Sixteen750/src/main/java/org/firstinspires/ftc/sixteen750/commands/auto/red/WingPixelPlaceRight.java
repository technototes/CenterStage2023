package org.firstinspires.ftc.sixteen750.commands.auto.red;

import com.technototes.library.command.SequentialCommandGroup;
import com.technototes.path.command.TrajectorySequenceCommand;

import org.firstinspires.ftc.sixteen750.AutoConstants.WingRed;
import org.firstinspires.ftc.sixteen750.Robot;

public class WingPixelPlaceRight extends SequentialCommandGroup {

    public WingPixelPlaceRight(Robot r) {
        super(
            new TrajectorySequenceCommand(r.drivebase, WingRed.START_TO_MID_CLEAR)
                .andThen(
                    new TrajectorySequenceCommand(
                        r.drivebase,
                        WingRed.MID_CLEAR_TO_RIGHT_SPIKE
                    )
                )
                .andThen(
                    new TrajectorySequenceCommand(
                        r.drivebase,
                        WingRed.RIGHT_SPIKE_TO_MID_CLEAR
                    )
                )
                .andThen(
                    new TrajectorySequenceCommand(r.drivebase, WingRed.MID_CLEAR_TO_CLEAR)
                )
                .andThen(
                    new TrajectorySequenceCommand(
                        r.drivebase,
                        WingRed.ClEAR_TO_RIGHT_CLEAR
                    )
                )
                .andThen(
                    new TrajectorySequenceCommand(
                        r.drivebase,
                        WingRed.RIGHT_CLEAR_TO_PLACE_RIGHT
                    )
                )
                .andThen(
                    new TrajectorySequenceCommand(
                        r.drivebase,
                        WingRed.PLACE_RIGHT_TO_RIGHT_CLEAR
                    )
                )
        );
    }
}
