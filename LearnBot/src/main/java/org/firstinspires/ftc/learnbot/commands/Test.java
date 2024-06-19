package org.firstinspires.ftc.learnbot.commands;

import com.technototes.library.command.Command;
import com.technototes.library.command.MethodCommand;
import org.firstinspires.ftc.learnbot.subsystems.TestSubsystem;

public class Test {

    public static Command ServoLeft(TestSubsystem ts) {
        return new MethodCommand(TestSubsystem::servoLeft, ts);
    }

    public static Command ServoRight(TestSubsystem ts) {
        return new MethodCommand(TestSubsystem::servoRight, ts);
    }

    public static Command MotorForward(TestSubsystem ts) {
        return new MethodCommand(TestSubsystem::forwardSpinning, ts);
    }

    public static Command MotorBackward(TestSubsystem ts) {
        return new MethodCommand(TestSubsystem::backwardSpinning, ts);
    }

    public static Command MotorStop(TestSubsystem ts) {
        return new MethodCommand(TestSubsystem::stopSpinning, ts);
    }
}
