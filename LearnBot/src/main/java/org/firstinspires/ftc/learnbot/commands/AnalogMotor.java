package org.firstinspires.ftc.learnbot.commands;

import com.technototes.library.command.Command;
import com.technototes.library.command.MethodCommand;
import org.firstinspires.ftc.learnbot.subsystems.MotorTestSubsystem;

public class AnalogMotor {

    public static Command IncrementCommand(MotorTestSubsystem motor) {
        return new MethodCommand(motor::MotorInc);
    }

    public static Command DecrementCommand(MotorTestSubsystem motor) {
        return new MethodCommand(motor::MotorDec);
    }

    public static Command ToggleControlCommand(MotorTestSubsystem motor) {
        return new MethodCommand(motor::toggleMotorControlMode);
    }

    public static Command ToggleStopModeCommand(MotorTestSubsystem motor) {
        return new MethodCommand(motor::toggleMotorStopMode);
    }
}
