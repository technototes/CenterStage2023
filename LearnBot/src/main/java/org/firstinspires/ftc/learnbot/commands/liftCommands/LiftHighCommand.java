package org.firstinspires.ftc.learnbot.commands.liftCommands;

import com.technototes.library.command.Command;
import org.firstinspires.ftc.learnbot.subsystems.PlacementSubsystem;

public class LiftHighCommand implements Command {

    private PlacementSubsystem ss;

    public LiftHighCommand(PlacementSubsystem t) {
        ss = t;
        addRequirements(t);
    }

    @Override
    public void execute() {
        ss.liftHeightHigh();
    }
}
