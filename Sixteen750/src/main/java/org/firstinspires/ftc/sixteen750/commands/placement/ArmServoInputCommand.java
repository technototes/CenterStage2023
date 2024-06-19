package org.firstinspires.ftc.sixteen750.commands.placement;

import com.technototes.library.command.Command;
import org.firstinspires.ftc.sixteen750.subsystems.PlacementSubsystem;

public class ArmServoInputCommand implements Command {

    private PlacementSubsystem placement;

    public ArmServoInputCommand(PlacementSubsystem i) {
        placement = i;
        addControlledSubsystems(i);
    }

    @Override
    public void execute() {
        placement.ArmServoInput();
    }
}
