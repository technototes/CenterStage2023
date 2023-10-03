package org.firstinspires.ftc.learnbot.commands;

import com.technototes.library.command.Command;
import org.firstinspires.ftc.learnbot.subsystems.TestSubsystem;

public class TestMotorBackwardCmd implements Command {

    private TestSubsystem ss;

    public TestMotorBackwardCmd(TestSubsystem test) {
        ss = test;
        addRequirements(test);
    }

    @Override
    public void execute() {
        ss.backwardSpinning();
    }
}
