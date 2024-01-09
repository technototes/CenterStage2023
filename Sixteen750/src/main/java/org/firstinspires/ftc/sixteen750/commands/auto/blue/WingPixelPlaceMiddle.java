package org.firstinspires.ftc.sixteen750.commands.auto.blue;

import com.technototes.library.command.SequentialCommandGroup;
import com.technototes.path.command.TrajectorySequenceCommand;

import org.firstinspires.ftc.sixteen750.AutoConstants.WingBlue;
import org.firstinspires.ftc.sixteen750.Robot;

public class WingPixelPlaceMiddle extends SequentialCommandGroup {

    public WingPixelPlaceMiddle(Robot r) {
        super(
            new TrajectorySequenceCommand(r.drivebase, WingBlue.START_TO_MIDDLE_SPIKE)
                .andThen(
                    new TrajectorySequenceCommand(
                        r.drivebase,
                        WingBlue.MIDDLE_SPIKE_TO_CLEAR
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
                        WingBlue.LEFT_CLEAR_TO_PLACE_MIDDLE
                    )
                )
                .andThen(
                    new TrajectorySequenceCommand(
                        r.drivebase,
                        WingBlue.PLACE_MIDDLE_TO_LEFT_CLEAR
                    )
                )
        );
    }
}
