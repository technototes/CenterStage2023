package org.firstinspires.ftc.sixteen750.commands.auto.blue;

import com.technototes.library.command.ParallelCommandGroup;
import com.technototes.path.command.TrajectorySequenceCommand;

import org.firstinspires.ftc.sixteen750.AutoConstants;
import org.firstinspires.ftc.sixteen750.Robot;
import org.firstinspires.ftc.sixteen750.commands.placement.ArmServoOutputCommand;
import org.firstinspires.ftc.sixteen750.commands.placement.LiftLowCommand;
import org.firstinspires.ftc.sixteen750.commands.placement.ScoreServoOutputCommand;

public class ServoUpParallelLeft extends ParallelCommandGroup {
    public ServoUpParallelLeft(Robot s) {
        super(new TrajectorySequenceCommand(s.drivebase, AutoConstants.WingBlue.LEFT_CLEAR_TO_PLACE_LEFT),
                new ArmServoOutputCommand(s.placement),
                new ScoreServoOutputCommand(s.placement)
        );
    }
}
