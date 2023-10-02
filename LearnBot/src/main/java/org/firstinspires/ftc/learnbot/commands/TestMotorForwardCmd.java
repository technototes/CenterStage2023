package org.firstinspires.ftc.learnbot.commands;

import com.technototes.library.command.Command;
import org.firstinspires.ftc.learnbot.subsystems.TestSubsystem;

public class TestMotorForwardCmd implements Command {

    private TestSubsystem ss;

    public TestMotorForwardCmd(TestSubsystem test) {
        ss = test;
        addRequirements(test);
    }

    @Override
    public void execute() {
        ss.forwardSpinning();
    }
}
