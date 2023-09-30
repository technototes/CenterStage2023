package org.firstinspires.ftc.sixteen750.commands.auto.red;

import com.technototes.library.command.SequentialCommandGroup;
import com.technototes.path.command.TrajectorySequenceCommand;
import org.firstinspires.ftc.sixteen750.AutoConstants;
import org.firstinspires.ftc.sixteen750.Robot;

public class WingPixelLeft extends SequentialCommandGroup {

    public WingPixelLeft(Robot r) {
        super(
            new TrajectorySequenceCommand(r.drivebase, AutoConstants.Wing.Red.START_TO_LEFT_STRIKE)
                .andThen(
                    new TrajectorySequenceCommand(
                        r.drivebase,
                        AutoConstants.Wing.Red.LEFT_STRIKE_TO_CLEAR
                    )
                )
        );
    }
}
