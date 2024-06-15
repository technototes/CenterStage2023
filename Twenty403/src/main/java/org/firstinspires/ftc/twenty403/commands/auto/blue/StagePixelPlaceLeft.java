package org.firstinspires.ftc.twenty403.commands.auto.blue;

import com.technototes.library.command.SequentialCommandGroup;
import com.technototes.path.command.TrajectorySequenceCommand;
import org.firstinspires.ftc.twenty403.AutoConstants.StageBlue;
import org.firstinspires.ftc.twenty403.Robot;
import org.firstinspires.ftc.twenty403.commands.auto.SafetyStartCommand;
import org.firstinspires.ftc.twenty403.commands.auto.SafetyStopCommand;
import org.firstinspires.ftc.twenty403.commands.auto.red.PixelScoring;

public class StagePixelPlaceLeft extends SequentialCommandGroup {

    public StagePixelPlaceLeft(Robot r) {
        super(
            new SafetyStartCommand(r.safetySubsystem),
            new TrajectorySequenceCommand(r.drivebaseSubsystem, StageBlue.START_TO_LEFT_SPIKE),
            new TrajectorySequenceCommand(r.drivebaseSubsystem, StageBlue.LEFT_SPIKE_TO_CLEAR),
            new TrajectorySequenceCommand(r.drivebaseSubsystem, StageBlue.CLEAR_TO_LEFT_CLEAR),
            new TrajectorySequenceCommand(r.drivebaseSubsystem, StageBlue.LEFT_CLEAR_TO_PLACE_LEFT),
            //place command
            new SafetyStopCommand(r.safetySubsystem),
            new PixelScoring(r.armSubsystem),
            new SafetyStartCommand(r.safetySubsystem),
            new TrajectorySequenceCommand(
                r.drivebaseSubsystem,
                StageBlue.PLACE_LEFT_TO_MID_PARK_CENTER
            )
        );
    }
}
