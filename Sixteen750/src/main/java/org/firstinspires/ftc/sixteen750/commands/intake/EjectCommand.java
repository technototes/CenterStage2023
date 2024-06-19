package org.firstinspires.ftc.sixteen750.commands.intake;

import com.technototes.library.command.Command;
import org.firstinspires.ftc.sixteen750.subsystems.IntakeSubsystem;

public class EjectCommand implements Command {

    private IntakeSubsystem intake;

    public EjectCommand(IntakeSubsystem i) {
        intake = i;
        addControlledSubsystems(i);
    }

    @Override
    public void execute() {
        intake.output();
    }
}
