package org.firstinspires.ftc.learnbot.commands;

import com.technototes.library.command.Command;
import com.technototes.library.command.MethodCommand;
import org.firstinspires.ftc.learnbot.subsystems.ClawAndWristSubsystem;
import org.firstinspires.ftc.learnbot.subsystems.DrivebaseSubsystem;
import org.firstinspires.ftc.learnbot.subsystems.MotorTestSubsystem;
import org.firstinspires.ftc.learnbot.subsystems.PlacementSubsystem;
import org.firstinspires.ftc.learnbot.subsystems.TestSubsystem;

public class EZCmd {

    public static class AnalogMotor {

        public static Command ToggleControlMode(MotorTestSubsystem mts) {
            return new MethodCommand(mts::toggleMotorControlMode);
        }

        public static Command ToggleStopMode(MotorTestSubsystem mts) {
            return new MethodCommand(mts::toggleMotorStopMode);
        }

        public static Command MotorInc(MotorTestSubsystem mts) {
            return mts.MotorInc();
        }

        public static Command MotorDec(MotorTestSubsystem mts) {
            return mts.MotorDec();
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

    public static class ClawAndWrist {

        public static Command ClawDec(ClawAndWristSubsystem cws) {
            return new MethodCommand(cws::ClawDec, cws);
        }

        public static Command ClawInc(ClawAndWristSubsystem cws) {
            return new MethodCommand(cws::ClawInc, cws);
        }

        public static Command WristDec(ClawAndWristSubsystem cws) {
            return new MethodCommand(cws::WristDec, cws);
        }

        public static Command WristInc(ClawAndWristSubsystem cws) {
            return new MethodCommand(cws::WristInc, cws);
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

    public static class Drive {

        public static Command TurboMode(DrivebaseSubsystem db) {
            return new MethodCommand(db::setTurboMode);
        }

        public static Command NormalMode(DrivebaseSubsystem db) {
            return new MethodCommand(db::setNormalMode);
        }

        public static Command SnailMode(DrivebaseSubsystem db) {
            return new MethodCommand(db::setSnailMode);
        }

        public static Command ResetGyro(DrivebaseSubsystem db) {
            return new MethodCommand(db::setExternalHeading, 0.0);
        }
    }
}
