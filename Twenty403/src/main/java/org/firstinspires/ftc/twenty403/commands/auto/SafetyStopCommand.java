package org.firstinspires.ftc.twenty403.commands.auto;

import com.technototes.library.command.Command;
import org.firstinspires.ftc.twenty403.subsystems.SafetySubsystem;

public class SafetyStopCommand implements Command {

    private SafetySubsystem subsystem;

    @Override
    public void execute() {
        subsystem.stopMonitoring();
    }

    public SafetyStopCommand(SafetySubsystem d) {
        subsystem = d;
        addRequirements(d);
    }
}
