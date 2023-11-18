package org.firstinspires.ftc.twenty403.commands.auto.claw;

import com.technototes.library.command.Command;
import org.firstinspires.ftc.twenty403.subsystems.ArmSubsystem;

public class ArmNeutralPosition implements Command {

    private ArmSubsystem subsystem;

    public ArmNeutralPosition(ArmSubsystem n) {
        subsystem = n;
        addRequirements(n);
    }

    @Override
    public void execute() {
        subsystem.neutralArmPosition();
    }
}
