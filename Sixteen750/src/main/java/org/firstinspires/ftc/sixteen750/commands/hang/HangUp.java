package org.firstinspires.ftc.sixteen750.commands.hang;

import com.technototes.library.command.Command;
import org.firstinspires.ftc.sixteen750.subsystems.HangSubsystem;
import org.firstinspires.ftc.sixteen750.subsystems.IntakeSubsystem;

public class HangUp implements Command {

    private HangSubsystem hang;

    public HangUp(HangSubsystem h) {
        hang = h;
        addControlledSubsystems(h);
    }

    @Override
    public void execute() {
        hang.ElbowUp();
    }
}
