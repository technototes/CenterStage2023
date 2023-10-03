package org.firstinspires.ftc.learnbot.commands;

import com.technototes.library.command.Command;
import org.firstinspires.ftc.learnbot.subsystems.TestSubsystem;

public class ToggleMotorStopModeCommand implements Command {

    private TestSubsystem ts;

    public ToggleMotorStopModeCommand(TestSubsystem ts) {
        this.ts = ts;
    }

    @Override
    public void execute() {
        ts.toggleMotorStopMode();
    }
}
