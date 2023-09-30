package org.firstinspires.ftc.sixteen750.commands.auto.red;

import com.technototes.library.command.SequentialCommandGroup;
import com.technototes.path.command.TrajectorySequenceCommand;
import org.firstinspires.ftc.sixteen750.AutoConstants;
import org.firstinspires.ftc.sixteen750.Robot;

public class WingPixelRight extends SequentialCommandGroup {

    public WingPixelRight(Robot r) {
        super(
            new TrajectorySequenceCommand(r.drivebase, AutoConstants.Wing.Red.START_TO_RIGHT_STRIKE)
                .andThen(
                    new TrajectorySequenceCommand(
                        r.drivebase,
                        AutoConstants.Wing.Red.RIGHT_STRIKE_TO_CLEAR
                    )
                )
        );
    }
}
