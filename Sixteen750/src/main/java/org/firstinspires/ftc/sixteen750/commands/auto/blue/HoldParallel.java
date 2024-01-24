package org.firstinspires.ftc.sixteen750.commands.auto.blue;

import com.technototes.library.command.ParallelCommandGroup;
import com.technototes.path.command.TrajectorySequenceCommand;

import org.firstinspires.ftc.sixteen750.AutoConstants;
import org.firstinspires.ftc.sixteen750.Robot;
import org.firstinspires.ftc.sixteen750.commands.placement.LiftLowCommand;

public class HoldParallel extends ParallelCommandGroup {
    public HoldParallel(Robot s) {
        super(new TrajectorySequenceCommand(s.drivebase, AutoConstants.WingBlue.),
                new LiftLowCommand(s.placement)
        );
    }
}
