package org.firstinspires.ftc.twenty403.commands.auto.claw;

import com.technototes.library.command.Command;
import org.firstinspires.ftc.twenty403.subsystems.ArmSubsystem;

public class ArmDecrementCommand implements Command {

    private ArmSubsystem subsystem;

    public ArmDecrementCommand(ArmSubsystem s) {
        subsystem = s;
        addRequirements(s);
    }

    @Override
    public void execute() {
        subsystem.arm_decrement();
    }
}
