package org.firstinspires.ftc.twenty403.commands.arm;

import com.technototes.library.command.Command;
import org.firstinspires.ftc.twenty403.subsystems.ArmSubsystem;

public class ShoulderNeutralPosition implements Command {

    private ArmSubsystem subsystem;

    public ShoulderNeutralPosition(ArmSubsystem n) {
        subsystem = n;
        addRequirements(n);
    }

    @Override
    public void execute() {
        subsystem.shoulderNeutralArmPosition();
    }
}
