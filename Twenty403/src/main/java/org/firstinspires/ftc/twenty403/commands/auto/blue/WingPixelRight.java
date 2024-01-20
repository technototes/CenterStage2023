package org.firstinspires.ftc.twenty403.commands.auto.blue;

import com.technototes.library.command.SequentialCommandGroup;
import com.technototes.library.command.WaitCommand;
import com.technototes.path.command.TrajectorySequenceCommand;
import org.firstinspires.ftc.twenty403.AutoConstants.WingBlue;
import org.firstinspires.ftc.twenty403.Robot;
import org.firstinspires.ftc.twenty403.commands.auto.SafetyStartCommand;
import org.firstinspires.ftc.twenty403.commands.auto.SafetyStopCommand;

public class WingPixelRight extends SequentialCommandGroup {

    public WingPixelRight(Robot r) {
            super(
            new SafetyStartCommand(r.safetySubsystem),
            new TrajectorySequenceCommand(r.drivebaseSubsystem, WingBlue.START_TO_RIGHT_SPIKE),
            new TrajectorySequenceCommand(r.drivebaseSubsystem, WingBlue.RIGHT_SPIKE_TO_CLEAR),
            new SafetyStopCommand(r.safetySubsystem),
            new WaitCommand(5),
            new SafetyStartCommand(r.safetySubsystem),
            new TrajectorySequenceCommand(r.drivebaseSubsystem, WingBlue.CLEAR_TO_PARK_CORNER)
            );
    }
}
