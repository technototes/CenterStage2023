package org.firstinspires.ftc.twenty403.commands.auto;

import com.technototes.library.command.SequentialCommandGroup;
import com.technototes.path.command.TrajectorySequenceCommand;

import org.firstinspires.ftc.twenty403.AutoConstants.WingRed;
import org.firstinspires.ftc.twenty403.Robot;

public class ForwardBackwardCommand extends SequentialCommandGroup {

    public ForwardBackwardCommand(Robot r) {
        super(
                new TrajectorySequenceCommand(r.drivebaseSubsystem, WingRed.SIDE_LEFT_TO_SIDE_RIGHT)
                        .andThen(new TrajectorySequenceCommand(r.drivebaseSubsystem, WingRed.SIDE_RIGHT_TO_SIDE_LEFT))
        );
//        super(
//                new TrajectorySequenceCommand(r.drivebaseSubsystem, WingRed.SIDE_LEFT_TO_SIDE_RIGHT)
//                        .andThen(new TrajectorySequenceCommand(r.drivebaseSubsystem, WingRed.SIDE_RIGHT_TO_SIDE_LEFT))
//        );
    }
}
