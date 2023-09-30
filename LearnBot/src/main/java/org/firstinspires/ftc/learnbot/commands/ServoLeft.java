package org.firstinspires.ftc.learnbot.commands;

import com.technototes.library.command.Command;
import org.firstinspires.ftc.learnbot.subsystems.SpinnySubsystem;
import org.firstinspires.ftc.learnbot.subsystems.TestSubsystem;

public class ServoLeft implements Command {

    private TestSubsystem ss;

    public ServoLeft(TestSubsystem t) {
        ss = t;
        addRequirements(t);
    }

    @Override
    public void execute() {
        ss.servoLeft();
    }
}
