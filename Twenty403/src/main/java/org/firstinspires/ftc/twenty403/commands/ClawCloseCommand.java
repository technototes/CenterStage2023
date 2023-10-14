package org.firstinspires.ftc.twenty403.commands;

import com.technototes.library.command.Command;
import org.firstinspires.ftc.twenty403.subsystems.ClawSubsystem;

public class ClawCloseCommand implements Command {

    private ClawSubsystem subsystem;

    public ClawCloseCommand(ClawSubsystem s) {
        subsystem = s;
        addRequirements(s);
    }

    @Override
    public void execute() {
        subsystem.close();
    }
}
