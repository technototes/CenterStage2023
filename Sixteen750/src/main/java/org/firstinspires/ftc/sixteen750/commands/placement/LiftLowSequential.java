package org.firstinspires.ftc.sixteen750.commands.placement;

import com.technototes.library.command.SequentialCommandGroup;

import org.firstinspires.ftc.sixteen750.subsystems.PlacementSubsystem;

public class LiftLowSequential extends SequentialCommandGroup {

    public LiftLowSequential(PlacementSubsystem s) {
        super(new ScoreHoldCommand(s), new ArmHoldCommand(s), new LiftLowCommand(s));
    }
}
