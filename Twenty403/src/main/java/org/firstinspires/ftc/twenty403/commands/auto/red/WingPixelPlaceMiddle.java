package org.firstinspires.ftc.twenty403.commands.auto.red;

import com.technototes.library.command.SequentialCommandGroup;
import com.technototes.library.command.WaitCommand;
import com.technototes.path.command.TrajectorySequenceCommand;
import org.firstinspires.ftc.twenty403.AutoConstants.WingRed;
import org.firstinspires.ftc.twenty403.Robot;
import org.firstinspires.ftc.twenty403.commands.auto.SafetyStartCommand;
import org.firstinspires.ftc.twenty403.commands.auto.SafetyStopCommand;
import org.firstinspires.ftc.twenty403.subsystems.ArmSubsystem;

public class WingPixelPlaceMiddle extends SequentialCommandGroup {

    public WingPixelPlaceMiddle(Robot r) {
        super(
            new SafetyStartCommand(r.safetySubsystem),
            new TrajectorySequenceCommand(r.drivebaseSubsystem, WingRed.START_TO_MIDDLE_SPIKE),
            new TrajectorySequenceCommand(r.drivebaseSubsystem, WingRed.MIDDLE_SPIKE_TO_CLEAR),
            new SafetyStopCommand(r.safetySubsystem),
            new WaitCommand(5),
            new SafetyStartCommand(r.safetySubsystem),
            new TrajectorySequenceCommand(r.drivebaseSubsystem, WingRed.ClEAR_TO_RIGHT_CLEAR),
            new TrajectorySequenceCommand(
                r.drivebaseSubsystem,
                WingRed.RIGHT_CLEAR_TO_PLACE_MIDDLE
            ),
            //place command
            new SafetyStopCommand(r.safetySubsystem),
            new PixelScoring(r.armSubsystem),
            new SafetyStartCommand(r.safetySubsystem),
            new TrajectorySequenceCommand(r.drivebaseSubsystem, WingRed.PLACE_MIDDLE_TO_RIGHT_CLEAR)
        );
    }
}
