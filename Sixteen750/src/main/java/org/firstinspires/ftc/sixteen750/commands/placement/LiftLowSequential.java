package org.firstinspires.ftc.sixteen750.commands.placement;

import com.technototes.library.command.SequentialCommandGroup;
import com.technototes.library.command.WaitCommand;

import org.firstinspires.ftc.sixteen750.subsystems.PlacementSubsystem;

public class LiftLowSequential extends SequentialCommandGroup {

    public LiftLowSequential(PlacementSubsystem s) {
        super( new ArmHoldCommand(s), new ScoreHoldCommand(s), new WaitCommand(0.5), new LiftLowCommand(s));
    }
}
