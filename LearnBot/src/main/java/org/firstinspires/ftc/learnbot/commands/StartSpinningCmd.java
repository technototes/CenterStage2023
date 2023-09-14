package org.firstinspires.ftc.learnbot.commands;

import com.technototes.library.command.Command;
import org.firstinspires.ftc.learnbot.subsystems.SpinnySubsystem;

public class StartSpinningCmd implements Command {

    private SpinnySubsystem ss;

    public StartSpinningCmd(SpinnySubsystem spin) {
        ss = spin;
        addRequirements(spin);
    }

    @Override
    public void execute() {
        ss.enableSpinning();
    }
}
