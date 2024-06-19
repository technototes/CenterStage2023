package org.firstinspires.ftc.twenty403.commands.auto.blue;

import com.technototes.library.command.SequentialCommandGroup;
import com.technototes.library.command.WaitCommand;
import com.technototes.path.command.TrajectorySequenceCommand;
import org.firstinspires.ftc.twenty403.AutoConstants;
import org.firstinspires.ftc.twenty403.Robot;

public class WingPixelIntake extends SequentialCommandGroup {

    public WingPixelIntake(Robot r) {
        super(
            new ParallelIntake(r),
            new WaitCommand(2),
            //neutral position
            new ParallelCarry(r),
            new TrajectorySequenceCommand(
                r.drivebaseSubsystem,
                AutoConstants.WingBlue.MID_PARK_CENTER_TO_PLACE_MIDDLE
            ),
            // pixel scoring
            new PixelScoring(r.armSubsystem),
            new TrajectorySequenceCommand(
                r.drivebaseSubsystem,
                AutoConstants.WingBlue.PLACE_MIDDLE_TO_MID_PARK_CENTER
            )
        );
    }
}
