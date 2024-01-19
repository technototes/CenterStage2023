package org.firstinspires.ftc.learnbot.commands;

import com.technototes.library.command.Command;
import org.firstinspires.ftc.learnbot.ClawAndWristBot;
import org.firstinspires.ftc.learnbot.subsystems.ClawAndWristSubsystem;

public class ClawDecCommand implements Command {

    private ClawAndWristSubsystem ss;

    public ClawDecCommand(ClawAndWristBot c) {
        ss = c.caw;
        addRequirements(ss);
    }

    @Override
    public void execute() {
        ss.ClawDec();
    }
}
