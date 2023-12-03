package org.firstinspires.ftc.twenty403.commands.arm;

import com.technototes.library.command.Command;
import org.firstinspires.ftc.twenty403.subsystems.ArmSubsystem;

public class ElbowNeutralPosition implements Command {

    private ArmSubsystem subsystem;

    public ElbowNeutralPosition(ArmSubsystem n) {
        subsystem = n;
        addRequirements(n);
    }

    @Override
    public void execute() {
        subsystem.elbowNeutralArmPosition();
    }
}
