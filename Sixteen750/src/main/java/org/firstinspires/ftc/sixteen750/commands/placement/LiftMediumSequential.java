package org.firstinspires.ftc.sixteen750.commands.placement;

import com.technototes.library.command.SequentialCommandGroup;

import org.firstinspires.ftc.sixteen750.subsystems.PlacementSubsystem;

public class LiftMediumSequential extends SequentialCommandGroup {

    public LiftMediumSequential(PlacementSubsystem s) {
        super(new ScoreHoldCommand(s), new ArmHoldCommand(s), new LiftMediumCommand(s));
    }
}
