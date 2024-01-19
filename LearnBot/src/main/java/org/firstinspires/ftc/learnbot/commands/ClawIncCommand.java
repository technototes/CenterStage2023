package org.firstinspires.ftc.learnbot.commands;

import com.technototes.library.command.Command;
import org.firstinspires.ftc.learnbot.ClawAndWristBot;
import org.firstinspires.ftc.learnbot.subsystems.ClawAndWristSubsystem;
import org.firstinspires.ftc.learnbot.subsystems.TestSubsystem;

public class ClawIncCommand implements Command {

    private ClawAndWristSubsystem ss;

    public ClawIncCommand(ClawAndWristBot c) {
        ss = c.caw;
        addRequirements(ss);
    }

    @Override
    public void execute() {
        ss.ClawInc();
    }
}
