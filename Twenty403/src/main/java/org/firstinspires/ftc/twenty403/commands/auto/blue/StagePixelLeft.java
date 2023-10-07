package org.firstinspires.ftc.twenty403.commands.auto.blue;

import com.technototes.library.command.SequentialCommandGroup;
import com.technototes.path.command.TrajectorySequenceCommand;
import org.firstinspires.ftc.twenty403.AutoConstants.StageBlue;
import org.firstinspires.ftc.twenty403.Robot;

public class StagePixelLeft extends SequentialCommandGroup {

    public StagePixelLeft(Robot r) {
        super(
            new TrajectorySequenceCommand(r.drivebaseSubsystem, StageBlue.START_TO_LEFT_SPIKE)
                .andThen(
                    new TrajectorySequenceCommand(
                        r.drivebaseSubsystem,
                        StageBlue.LEFT_SPIKE_TO_CLEAR
                    )
                )
        );
    }
}
