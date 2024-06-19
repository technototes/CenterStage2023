package org.firstinspires.ftc.twenty403.commands.auto;

import com.technototes.library.command.SequentialCommandGroup;
import com.technototes.path.command.TrajectorySequenceCommand;
import org.firstinspires.ftc.twenty403.AutoConstants;
import org.firstinspires.ftc.twenty403.Robot;

public class SideAndBackCommand extends SequentialCommandGroup {

    public SideAndBackCommand(Robot r) {
        super(
            new TrajectorySequenceCommand(
                r.drivebaseSubsystem,
                AutoConstants.WingRed.SIDE_LEFT_TO_SIDE_RIGHT
            ).andThen(
                new TrajectorySequenceCommand(
                    r.drivebaseSubsystem,
                    AutoConstants.WingRed.SIDE_RIGHT_TO_SIDE_LEFT
                )
            )
        );
    }
}
