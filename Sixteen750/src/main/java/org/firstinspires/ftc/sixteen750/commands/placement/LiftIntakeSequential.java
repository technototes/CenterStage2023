package org.firstinspires.ftc.sixteen750.commands.placement;

import com.technototes.library.command.SequentialCommandGroup;

import org.firstinspires.ftc.sixteen750.subsystems.PlacementSubsystem;

public class LiftIntakeSequential extends SequentialCommandGroup {

    public LiftIntakeSequential(PlacementSubsystem s) {
        super(new ScoreHoldCommand(s), new ArmHoldCommand(s), new LiftIntakeCommand(s), new ArmServoInputCommand(s), new ScoreServoInputCommand(s));
    }
}
