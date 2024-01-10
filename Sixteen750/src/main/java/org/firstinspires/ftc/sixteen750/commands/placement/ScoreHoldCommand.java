package org.firstinspires.ftc.sixteen750.commands.placement;

import com.technototes.library.command.Command;
import com.technototes.library.command.SequentialCommandGroup;
import com.technototes.library.command.WaitCommand;

import org.firstinspires.ftc.sixteen750.subsystems.PlacementSubsystem;

public class ScoreHoldCommand extends SequentialCommandGroup {

    public ScoreHoldCommand(PlacementSubsystem s) {
        super(new ArmHoldCommand(s), new ScoreHoldCommand(s));
    }
}
