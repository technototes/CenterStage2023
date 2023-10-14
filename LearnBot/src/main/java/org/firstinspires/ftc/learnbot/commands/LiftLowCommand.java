package org.firstinspires.ftc.learnbot.commands;

import com.technototes.library.command.Command;

import org.firstinspires.ftc.learnbot.subsystems.PlacementSubsystem;
import org.firstinspires.ftc.learnbot.subsystems.TestSubsystem;

public class LiftLowCommand implements Command {

    private PlacementSubsystem ss;

    public LiftLowCommand(PlacementSubsystem t) {
        ss = t;
        addRequirements(t);
    }

    @Override
    public void execute() {ss.liftHeightLow();}
}
