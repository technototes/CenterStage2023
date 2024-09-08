package org.firstinspires.ftc.sixteen750.commands.placement;

import com.technototes.library.command.Command;
import org.firstinspires.ftc.sixteen750.subsystems.PlacementSubsystem;

public class ScoreServoOutputCommand implements Command {

    private PlacementSubsystem placement;

    public ScoreServoOutputCommand(PlacementSubsystem i) {
        placement = i;
        addRequirements(i);
    }

    @Override
    public void execute() {
        placement.ScoreServoOutput();
    }
}
