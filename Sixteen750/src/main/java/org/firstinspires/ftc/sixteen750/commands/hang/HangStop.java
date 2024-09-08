package org.firstinspires.ftc.sixteen750.commands.hang;

import com.technototes.library.command.Command;
import org.firstinspires.ftc.sixteen750.subsystems.HangSubsystem;

public class HangStop implements Command {

    private HangSubsystem hang;

    public HangStop(HangSubsystem h) {
        hang = h;
        addRequirements(h);
    }

    @Override
    public void execute() {
        hang.ElbowStop();
    }
}
