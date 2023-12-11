package org.firstinspires.ftc.learnbot.commands;

import com.technototes.library.command.Command;
import org.firstinspires.ftc.learnbot.subsystems.MotorTestSubsystem;
import org.firstinspires.ftc.learnbot.subsystems.TestSubsystem;

public class AnalogMotorToggleControlCmd implements Command {

    private MotorTestSubsystem ss;

    public AnalogMotorToggleControlCmd(MotorTestSubsystem test) {
        ss = test;
    }

    @Override
    public void execute() {
        ss.toggleMotorControlMode();
    }
}
