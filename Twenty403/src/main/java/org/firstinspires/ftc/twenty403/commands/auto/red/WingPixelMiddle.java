package org.firstinspires.ftc.twenty403.commands.auto.red;

import com.technototes.library.command.SequentialCommandGroup;
import com.technototes.path.command.TrajectorySequenceCommand;
import org.firstinspires.ftc.twenty403.AutoConstants.WingRed;
import org.firstinspires.ftc.twenty403.Robot;

public class WingPixelMiddle extends SequentialCommandGroup {

    public WingPixelMiddle(Robot r) {
            super(
            new TrajectorySequenceCommand(r.drivebaseSubsystem, WingRed.START_TO_MIDDLE_SPIKE),
            new TrajectorySequenceCommand(r.drivebaseSubsystem, WingRed.MIDDLE_SPIKE_TO_CLEAR),
            new TrajectorySequenceCommand(r.drivebaseSubsystem, WingRed.CLEAR_TO_PARK_CORNER)
            );
    }
}
