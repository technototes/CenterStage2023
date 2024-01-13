package org.firstinspires.ftc.sixteen750.commands.placement;

import com.technototes.library.command.SequentialCommandGroup;
import com.technototes.library.command.WaitCommand;

import org.firstinspires.ftc.sixteen750.subsystems.PlacementSubsystem;

public class ServoHold extends SequentialCommandGroup {

    public ServoHold(PlacementSubsystem s) {
        super(new ArmHoldCommand(s), new WaitCommand(0.2), new ScoreHoldCommand(s));
    }
}
