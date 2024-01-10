package org.firstinspires.ftc.twenty403.commands.auto.blue;

import com.technototes.library.command.SequentialCommandGroup;
import com.technototes.path.command.TrajectorySequenceCommand;
import org.firstinspires.ftc.twenty403.AutoConstants.WingBlue;
import org.firstinspires.ftc.twenty403.Robot;

public class WingPixelLeft extends SequentialCommandGroup {

    public WingPixelLeft(Robot r) {
        super(
        new TrajectorySequenceCommand(r.drivebaseSubsystem, WingBlue.START_TO_MID_CLEAR),
        new TrajectorySequenceCommand(r.drivebaseSubsystem, WingBlue.MID_CLEAR_TO_LEFT_SPIKE),
        new TrajectorySequenceCommand(r.drivebaseSubsystem, WingBlue.LEFT_SPIKE_TO_MID_CLEAR),
        new TrajectorySequenceCommand(r.drivebaseSubsystem, WingBlue.MID_CLEAR_TO_CLEAR),
        new TrajectorySequenceCommand(r.drivebaseSubsystem, WingBlue.CLEAR_TO_PARK_CORNER)
        );
    }
}
