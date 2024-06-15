package org.firstinspires.ftc.twenty403.commands.auto.red;

import com.technototes.library.command.SequentialCommandGroup;
import com.technototes.path.command.TrajectorySequenceCommand;
import org.firstinspires.ftc.twenty403.AutoConstants;
import org.firstinspires.ftc.twenty403.Robot;
import org.firstinspires.ftc.twenty403.commands.auto.SafetyStartCommand;

public class StagePixelLeft extends SequentialCommandGroup {

    public StagePixelLeft(Robot r) {
        super(
            new SafetyStartCommand(r.safetySubsystem),
            new TrajectorySequenceCommand(
                r.drivebaseSubsystem,
                AutoConstants.StageRed.START_TO_MID_CLEAR
            ),
            new TrajectorySequenceCommand(
                r.drivebaseSubsystem,
                AutoConstants.StageRed.MID_CLEAR_TO_LEFT_SPIKE
            ),
            new TrajectorySequenceCommand(
                r.drivebaseSubsystem,
                AutoConstants.StageRed.LEFT_SPIKE_TO_MID_CLEAR
            ),
            new TrajectorySequenceCommand(
                r.drivebaseSubsystem,
                AutoConstants.StageRed.MID_CLEAR_TO_CLEAR
            ),
            new TrajectorySequenceCommand(
                r.drivebaseSubsystem,
                AutoConstants.StageRed.CLEAR_TO_RIGHT_CLEAR
            ),
            new TrajectorySequenceCommand(
                r.drivebaseSubsystem,
                AutoConstants.StageRed.RIGHT_CLEAR_TO_MID_PARK_CENTER
            )
        );
    }
}
