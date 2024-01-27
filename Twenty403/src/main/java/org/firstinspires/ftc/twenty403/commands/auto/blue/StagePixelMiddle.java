package org.firstinspires.ftc.twenty403.commands.auto.blue;

import com.technototes.library.command.SequentialCommandGroup;
import com.technototes.path.command.TrajectorySequenceCommand;
import org.firstinspires.ftc.twenty403.AutoConstants;
import org.firstinspires.ftc.twenty403.Robot;
import org.firstinspires.ftc.twenty403.commands.auto.SafetyStartCommand;

public class StagePixelMiddle extends SequentialCommandGroup {

    public StagePixelMiddle(Robot r) {
        super(
            new SafetyStartCommand(r.safetySubsystem),
            new TrajectorySequenceCommand(
                r.drivebaseSubsystem,
                AutoConstants.StageBlue.START_TO_MIDDLE_SPIKE
            ),
            new TrajectorySequenceCommand(
                r.drivebaseSubsystem,
                AutoConstants.StageBlue.MIDDLE_SPIKE_TO_CLEAR
            ),
            new TrajectorySequenceCommand(
                r.drivebaseSubsystem,
                AutoConstants.StageBlue.CLEAR_TO_MID_PARK_CENTER
            ),
            new TrajectorySequenceCommand(
                r.drivebaseSubsystem,
                AutoConstants.StageBlue.MID_PARK_CENTER_TO_PARK_CENTER
            )
        );
    }
}
