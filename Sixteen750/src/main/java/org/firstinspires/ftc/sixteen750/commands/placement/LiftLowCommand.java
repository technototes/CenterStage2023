package org.firstinspires.ftc.sixteen750.commands.placement;

import com.technototes.library.command.Command;
import org.firstinspires.ftc.sixteen750.subsystems.PlacementSubsystem;

public class LiftLowCommand implements Command {

    private PlacementSubsystem placement;

    public LiftLowCommand(PlacementSubsystem i) {
        placement = i;
        addControlledSubsystems(i);
    }

    @Override
    public void execute() {
        placement.LiftHeightLow();
    }
}
