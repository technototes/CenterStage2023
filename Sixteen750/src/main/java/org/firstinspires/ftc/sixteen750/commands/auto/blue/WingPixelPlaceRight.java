package org.firstinspires.ftc.sixteen750.commands.auto.blue;

import com.technototes.library.command.SequentialCommandGroup;
import com.technototes.path.command.TrajectorySequenceCommand;

import org.firstinspires.ftc.sixteen750.AutoConstants.WingBlue;
import org.firstinspires.ftc.sixteen750.Robot;

public class WingPixelPlaceRight extends SequentialCommandGroup {

    public WingPixelPlaceRight(Robot r) {
        super(
            new TrajectorySequenceCommand(r.drivebase, WingBlue.START_TO_RIGHT_SPIKE)
                .andThen(
                    new TrajectorySequenceCommand(
                        r.drivebase,
                        WingBlue.RIGHT_SPIKE_TO_CLEAR
                    )
                )
                .andThen(
                    new TrajectorySequenceCommand(
                        r.drivebase,
                        WingBlue.ClEAR_TO_LEFT_CLEAR
                    )
                )
                .andThen(
                    new TrajectorySequenceCommand(
                        r.drivebase,
                        WingBlue.LEFT_CLEAR_TO_PLACE_RIGHT
                    )
                )
                .andThen(
                    new TrajectorySequenceCommand(
                        r.drivebase,
                        WingBlue.PLACE_RIGHT_TO_LEFT_CLEAR
                    )
                )
        );
    }
}
