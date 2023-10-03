package org.firstinspires.ftc.learnbot.commands;

import com.technototes.library.command.Command;
import org.firstinspires.ftc.learnbot.subsystems.TestSubsystem;

public class TestMotorStopCmd implements Command {

    private TestSubsystem ss;

    public TestMotorStopCmd(TestSubsystem test) {
        ss = test;
        addRequirements(test);
    }

    @Override
    public void execute() {
        ss.stopSpinning();
    }
}
