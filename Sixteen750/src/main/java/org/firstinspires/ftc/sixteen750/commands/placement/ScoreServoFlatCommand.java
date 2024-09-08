package org.firstinspires.ftc.sixteen750.commands.placement;

import com.technototes.library.command.Command;
import org.firstinspires.ftc.sixteen750.subsystems.PlacementSubsystem;

public class ScoreServoFlatCommand implements Command {

    private PlacementSubsystem placement;

    public ScoreServoFlatCommand(PlacementSubsystem i) {
        placement = i;
        addControlledSubsystems(i);
    }

    @Override
    public void execute() {
        placement.ScoreServoFlat();
    }
}
