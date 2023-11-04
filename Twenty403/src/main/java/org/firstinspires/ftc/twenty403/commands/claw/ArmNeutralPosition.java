package org.firstinspires.ftc.twenty403.commands.claw;

import com.technototes.library.command.Command;
import org.firstinspires.ftc.twenty403.subsystems.ClawSubsystem;

public class ArmNeutralPosition implements Command {

    private ClawSubsystem subsystem;

    public ArmNeutralPosition(ClawSubsystem n) {
        subsystem = n;
        addRequirements(n);
    }

    @Override
    public void execute() {
        subsystem.neutralArmPosition();
    }
}
