package org.firstinspires.ftc.twenty403.commands.auto.blue;

import com.technototes.library.command.ParallelCommandGroup;
import com.technototes.library.command.SequentialCommandGroup;
import com.technototes.path.command.TrajectorySequenceCommand;

import org.firstinspires.ftc.twenty403.AutoConstants;
import org.firstinspires.ftc.twenty403.Robot;

public class ParallelPlacement extends ParallelCommandGroup {

    public ParallelPlacement(Robot r) {
        super(new TrajectorySequenceCommand(r.drivebaseSubsystem, AutoConstants.WingBlue.LEFT_CLEAR_TO_PLACE_MIDDLE),
              new PixelScoring(r.armSubsystem)
        );
    }
}
