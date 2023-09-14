package org.firstinspires.ftc.learnbot.commands;

import com.technototes.library.command.Command;
import org.firstinspires.ftc.learnbot.subsystems.SpinnySubsystem;

public class StopSpinningCmd implements Command {

    private SpinnySubsystem ss;

    public StopSpinningCmd(SpinnySubsystem spin) {
        ss = spin;
        addRequirements(spin);
    }

    @Override
    public void execute() {
        ss.enableSpinning();
    }
}
