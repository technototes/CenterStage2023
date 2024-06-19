package org.firstinspires.ftc.sixteen750.commands.hang;

import com.technototes.library.command.Command;
import org.firstinspires.ftc.sixteen750.subsystems.HangSubsystem;

public class HangNeutral implements Command {

    private HangSubsystem hang;

    public HangNeutral(HangSubsystem h) {
        hang = h;
        addControlledSubsystems(h);
    }

    @Override
    public void execute() {
        hang.ElbowNeutral();
    }
}
