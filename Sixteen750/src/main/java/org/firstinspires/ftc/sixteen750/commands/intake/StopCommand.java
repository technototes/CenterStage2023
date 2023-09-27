package org.firstinspires.ftc.sixteen750.commands.intake;

import com.technototes.library.command.Command;

import org.firstinspires.ftc.sixteen750.subsystems.IntakeSubsystem;

public class StopCommand implements Command {
    private IntakeSubsystem intake;
    public StopCommand(IntakeSubsystem i) {
        intake = i;
        addRequirements(i);
    }
    @Override
    public void execute() {intake.stop();}

}
