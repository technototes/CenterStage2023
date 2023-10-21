package org.firstinspires.ftc.twenty403.commands.claw;

import com.technototes.library.command.Command;
import org.firstinspires.ftc.twenty403.subsystems.ClawSubsystem;

public class ArmSecondLineScoring implements Command {

    private ClawSubsystem subsystem;

    public ArmSecondLineScoring(ClawSubsystem s) {
        subsystem = s;
        addRequirements(s);
    }

    @Override
    public void execute() {
        subsystem.secondLineScoring();
    }
}
