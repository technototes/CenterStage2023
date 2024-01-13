package org.firstinspires.ftc.sixteen750.commands.placement;

import com.technototes.library.command.SequentialCommandGroup;
import com.technototes.library.command.WaitCommand;

import org.firstinspires.ftc.sixteen750.subsystems.PlacementSubsystem;

public class LiftMediumSequential extends SequentialCommandGroup {

    public LiftMediumSequential(PlacementSubsystem s) {
        super(new ArmHoldCommand(s), new ScoreHoldCommand(s), new WaitCommand(0.5),new LiftMediumCommand(s));
    }
}
