package org.firstinspires.ftc.learnbot.commands;

import com.technototes.library.command.Command;
import com.technototes.library.command.MethodCommand;
import org.firstinspires.ftc.learnbot.subsystems.MotorTestSubsystem;
import org.firstinspires.ftc.learnbot.subsystems.PlacementSubsystem;
import org.firstinspires.ftc.learnbot.subsystems.TestSubsystem;

public class EZCmd {

    public static class AnalogMotor {

        public static Command Increment(MotorTestSubsystem ss) {
            return new MethodCommand(ss::motorInc, ss);
        }

        public static Command Decrement(MotorTestSubsystem ss) {
            return new MethodCommand(ss::motorDec, ss);
        }

        public static Command ToggleControlMode(MotorTestSubsystem ss) {
            return new MethodCommand(ss::toggleMotorControlMode);
        }

        public static Command ToggleStopMode(MotorTestSubsystem ss) {
            return new MethodCommand(ss::toggleMotorStopMode);
        }
    }

    public static class Placement {

        public static Command LiftHigh(PlacementSubsystem ss) {
            return new MethodCommand(ss::liftHeightHigh, ss);
        }

        public static Command LiftMedium(PlacementSubsystem ss) {
            return new MethodCommand(ss::liftHeightMedium, ss);
        }

        public static Command LiftLow(PlacementSubsystem ss) {
            return new MethodCommand(ss::liftHeightLow, ss);
        }
    }

    public static class Test {

        public static Command MotorBackward(TestSubsystem ts) {
            return new MethodCommand(ts::backwardSpinning, ts);
        }

        public static Command MotorForward(TestSubsystem ts) {
            return new MethodCommand(ts::forwardSpinning, ts);
        }

        public static Command StopMotor(TestSubsystem ts) {
            return new MethodCommand(ts::stopSpinning, ts);
        }

        public static Command ToggleMotorStopMode(TestSubsystem ts) {
            return new MethodCommand(ts::toggleMotorStopMode);
        }

        public static Command ServoLeft(TestSubsystem ts) {
            return new MethodCommand(ts::servoLeft, ts);
        }

        public static Command ServoRight(TestSubsystem ts) {
            return new MethodCommand(ts::servoRight, ts);
        }
    }
}
