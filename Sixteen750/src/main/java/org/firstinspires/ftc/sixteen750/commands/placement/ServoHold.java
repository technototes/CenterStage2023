package org.firstinspires.ftc.sixteen750.commands.placement;

import com.technototes.library.command.SequentialCommandGroup;

import org.firstinspires.ftc.sixteen750.subsystems.PlacementSubsystem;

public class ServoHold extends SequentialCommandGroup {

    public ServoHold(PlacementSubsystem s) {
        super(new ArmHoldCommand(s), new ScoreHoldCommand(s));
    }
}
