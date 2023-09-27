package org.firstinspires.ftc.sixteen750.commands.intake;

import com.technototes.library.command.Command;

import org.firstinspires.ftc.sixteen750.subsystems.IntakeSubsystem;

public class IntakeCommand implements Command {
    private IntakeSubsystem intake;
    public IntakeCommand(IntakeSubsystem i) {
        intake = i;
        addRequirements(i);
    }
    @Override
    public void execute() {intake.intake();}

}
