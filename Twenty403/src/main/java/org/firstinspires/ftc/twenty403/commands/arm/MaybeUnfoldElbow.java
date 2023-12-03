package org.firstinspires.ftc.twenty403.commands.arm;

import com.technototes.library.command.Command;
import org.firstinspires.ftc.twenty403.subsystems.ArmSubsystem;

public class MaybeUnfoldElbow implements Command {

    private ArmSubsystem subsystem;

    public MaybeUnfoldElbow(ArmSubsystem s) {
        subsystem = s;
        addRequirements(s);
    }

    @Override
    public void execute() {
        subsystem.maybeUnfoldElbow();
    }
}
