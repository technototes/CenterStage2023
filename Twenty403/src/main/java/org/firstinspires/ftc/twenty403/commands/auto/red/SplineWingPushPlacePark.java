package org.firstinspires.ftc.twenty403.commands.auto.red;

import com.technototes.library.command.SequentialCommandGroup;
import com.technototes.library.command.WaitCommand;
import com.technototes.path.command.TrajectorySequenceCommand;
import org.firstinspires.ftc.twenty403.AutoConstants.WingRed;
import org.firstinspires.ftc.twenty403.Robot;
import org.firstinspires.ftc.twenty403.commands.auto.SafetyStartCommand;
import org.firstinspires.ftc.twenty403.commands.auto.SafetyStopCommand;

public class SplineWingPushPlacePark extends SequentialCommandGroup {

    public SplineWingPushPlacePark(Robot r) {
        super(
            new TrajectorySequenceCommand(
                r.drivebaseSubsystem,
                WingRed.SPLINE_START_TO_RIGHT_SPIKE
            ),
            new TrajectorySequenceCommand(r.drivebaseSubsystem, WingRed.RIGHT_SPIKE_TO_STAGE)
        );
    }
}
