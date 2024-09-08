package org.firstinspires.ftc.twenty403.commands;

import com.technototes.library.command.Command;
import com.technototes.library.command.MethodCommand;
import com.technototes.library.command.SequentialCommandGroup;
import com.technototes.library.command.WaitCommand;
import org.firstinspires.ftc.twenty403.subsystems.ArmSubsystem;
import org.firstinspires.ftc.twenty403.subsystems.DrivebaseSubsystem;
import org.firstinspires.ftc.twenty403.subsystems.DroneSubsystem;

public class EZCmd {

    public static class Arm {

        public static Command FirstLine(ArmSubsystem arm) {
            return new SequentialCommandGroup(
                Shoulder.FirstLine(arm),
                Wrist.FirstLine(arm),
                Intake.Slurp(arm)
            );
        }

        public static Command SecondLine(ArmSubsystem arm) {
            return new SequentialCommandGroup(
                Shoulder.SecondLine(arm),
                Wrist.SecondLine(arm),
                Intake.Slurp(arm)
            );
        }

        public static Command ThirdLine(ArmSubsystem arm) {
            return new SequentialCommandGroup(
                Shoulder.ThirdLine(arm),
                Wrist.ThirdLine(arm),
                Intake.Slurp(arm)
            );
        }

        public static Command Intake(ArmSubsystem arm) {
            return new SequentialCommandGroup(
                Shoulder.Intake(arm),
                Wrist.Intake(arm),
                Intake.Slurp(arm)
            );
        }

        public static Command Neutral(ArmSubsystem arm) {
            return new SequentialCommandGroup(
                Shoulder.Neutral(arm),
                Wrist.Neutral(arm),
                Intake.Stop(arm)
            );
        }
    }

    public static class Hang {

        public static Command Enter(ArmSubsystem arm) {
            return new MethodCommand(arm::enterHangMode, arm);
        }

        public static Command Down(ArmSubsystem arm) {
            return new MethodCommand(arm::hangDown, arm);
        }

        public static Command Stop(ArmSubsystem arm) {
            return new MethodCommand(arm::hangStop, arm);
        }

        public static Command Up(ArmSubsystem arm) {
            return new MethodCommand(arm::hangUp, arm);
        }

        public static Command Leave(ArmSubsystem arm) {
            return new MethodCommand(arm::leaveHangMode, arm);
        }

        public static Command Auto(ArmSubsystem arm) {
            return new SequentialCommandGroup(
                Shoulder.Vertical(arm),
                Wrist.Neutral(arm),
                Shoulder.Neutral(arm)
            );
        }
    }

    public static class Intake {

        public static Command Slurp(ArmSubsystem arm) {
            return new MethodCommand(arm::slurpIntake, arm);
        }

        public static Command Spit(ArmSubsystem arm) {
            return new MethodCommand(arm::spitIntake, arm);
        }

        public static Command Stop(ArmSubsystem arm) {
            return new MethodCommand(arm::stopIntake, arm);
        }
    }

    public static class Shoulder {

        public static Command Decrement(ArmSubsystem arm) {
            return new MethodCommand(arm::shoulder_decrement, arm);
        }

        public static Command LargeDecrement(ArmSubsystem arm) {
            return new MethodCommand(arm::shoulder_largeDecrement, arm);
        }

        public static Command Increment(ArmSubsystem arm) {
            return new MethodCommand(arm::shoulder_increment, arm);
        }

        public static Command LargeIncrement(ArmSubsystem arm) {
            return new MethodCommand(arm::shoulder_largeIncrement, arm);
        }

        public static Command FirstLine(ArmSubsystem arm) {
            return new MethodCommand(arm::shoulderFirstLineScoring, arm);
        }

        public static Command SecondLine(ArmSubsystem arm) {
            return new MethodCommand(arm::shoulderSecondLineScoring, arm);
        }

        public static Command ThirdLine(ArmSubsystem arm) {
            return new MethodCommand(arm::shoulderThirdLineScoring, arm);
        }

        public static Command Neutral(ArmSubsystem arm) {
            return new MethodCommand(arm::shoulderNeutralArmPosition, arm);
        }

        public static Command ResetNeutral(ArmSubsystem arm) {
            return new MethodCommand(arm::resetArmNeutral, arm);
        }

        public static Command Vertical(ArmSubsystem arm) {
            return new MethodCommand(arm::shoulderVertical, arm);
        }

        public static Command Intake(ArmSubsystem arm) {
            return new MethodCommand(arm::shoulderIntake, arm);
        }
    }

    public static class Wrist {

        public static Command FirstLine(ArmSubsystem arm) {
            return new MethodCommand(arm::wristFirstLineScoring, arm);
        }

        public static Command SecondLine(ArmSubsystem arm) {
            return new MethodCommand(arm::wristSecondLineScoring, arm);
        }

        public static Command ThirdLine(ArmSubsystem arm) {
            return new MethodCommand(arm::wristThirdLineScoring, arm);
        }

        public static Command Intake(ArmSubsystem arm) {
            return new MethodCommand(arm::wristIntake, arm);
        }

        public static Command Neutral(ArmSubsystem arm) {
            return new MethodCommand(arm::wristNeutralArmPosition, arm);
        }

        public static Command Increment(ArmSubsystem arm) {
            return new MethodCommand(arm::wrist_increment, arm);
        }

        public static Command Decrement(ArmSubsystem arm) {
            return new MethodCommand(arm::wrist_decrement, arm);
        }
    }

    public static class Drone {

        private static Command Start(DroneSubsystem drone) {
            return new MethodCommand(drone::launch, drone);
        }

        private static Command Stop(DroneSubsystem drone) {
            return new MethodCommand(drone::unlaunch, drone);
        }

        public static Command Launch(DroneSubsystem drone) {
            return new SequentialCommandGroup(Start(drone), new WaitCommand(0.5), Stop(drone));
        }
    }

    public static class Drive {

        public static Command NormalMode(DrivebaseSubsystem drive) {
            return new MethodCommand(drive::setNormalMode);
        }

        public static Command SnailMode(DrivebaseSubsystem drive) {
            return new MethodCommand(drive::setSnailMode);
        }

        public static Command TurboMode(DrivebaseSubsystem drive) {
            return new MethodCommand(drive::setTurboMode);
        }

        public static Command ResetGyro(DrivebaseSubsystem drive) {
            return new MethodCommand(drive::setExternalHeading, 0.0);
        }
    }
}
