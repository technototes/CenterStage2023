package org.firstinspires.ftc.learnbot.commands;

import com.technototes.library.command.Command;
import org.firstinspires.ftc.learnbot.ClawAndWristBot;
import org.firstinspires.ftc.learnbot.subsystems.ClawAndWristSubsystem;

public class WristIncCommand implements Command {

    private ClawAndWristSubsystem ss;

    public WristIncCommand(ClawAndWristBot c) {
        ss = c.caw;
        addRequirements(ss);
    }

    @Override
    public void execute() {
        ss.WristInc();
    }
}
