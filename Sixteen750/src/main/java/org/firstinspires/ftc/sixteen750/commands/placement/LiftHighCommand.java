package org.firstinspires.ftc.sixteen750.commands.placement;

import com.technototes.library.command.Command;
import org.firstinspires.ftc.sixteen750.subsystems.IntakeSubsystem;
import org.firstinspires.ftc.sixteen750.subsystems.PlacementSubsystem;

public class LiftHighCommand implements Command {

    private PlacementSubsystem placement;

    public LiftHighCommand(PlacementSubsystem i) {
        placement = i;
        addControlledSubsystems(i);
    }

    @Override
    public void execute() {
        placement.LiftHeightHigh();
    }
}
