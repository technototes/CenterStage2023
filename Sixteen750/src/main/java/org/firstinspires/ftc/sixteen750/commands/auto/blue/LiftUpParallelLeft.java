package org.firstinspires.ftc.sixteen750.commands.auto.blue;

import com.technototes.library.command.ParallelCommandGroup;
import com.technototes.library.command.SequentialCommandGroup;
import com.technototes.path.command.TrajectorySequenceCommand;

import org.firstinspires.ftc.sixteen750.AutoConstants;
import org.firstinspires.ftc.sixteen750.Robot;
import org.firstinspires.ftc.sixteen750.commands.placement.LiftLowCommand;

public class LiftUpParallelLeft extends ParallelCommandGroup {
    public LiftUpParallelLeft(Robot s) {
        super(new TrajectorySequenceCommand(s.drivebase, AutoConstants.WingBlue.ClEAR_TO_LEFT_CLEAR),
                new LiftLowCommand(s.placement)
        );
    }
}
