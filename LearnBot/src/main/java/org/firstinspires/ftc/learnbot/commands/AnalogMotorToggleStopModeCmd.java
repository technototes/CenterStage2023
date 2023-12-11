package org.firstinspires.ftc.learnbot.commands;

import com.technototes.library.command.Command;
import org.firstinspires.ftc.learnbot.subsystems.MotorTestSubsystem;

public class AnalogMotorToggleStopModeCmd implements Command {

    private MotorTestSubsystem ss;

    public AnalogMotorToggleStopModeCmd(MotorTestSubsystem test) {
        ss = test;
    }

    @Override
    public void execute() {
        ss.toggleMotorStopMode();
    }
}
