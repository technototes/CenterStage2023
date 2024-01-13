package org.firstinspires.ftc.sixteen750.commands.placement;

import com.technototes.library.command.SequentialCommandGroup;

import org.firstinspires.ftc.sixteen750.subsystems.PlacementSubsystem;

public class ScoreHoldSequential extends SequentialCommandGroup {

    public ScoreHoldSequential(PlacementSubsystem s) {
        super(new ArmHoldCommand(s), new ScoreHoldCommand(s));
    }
}
