package org.firstinspires.ftc.twenty403.controls;

import com.technototes.library.control.CommandButton;
import com.technototes.library.control.CommandGamepad;
import org.firstinspires.ftc.twenty403.Robot;
import org.firstinspires.ftc.twenty403.Setup;
import org.firstinspires.ftc.twenty403.commands.EZCmd;

public class OperatorController {

    public Robot robot;
    public CommandGamepad gamepad;
    public CommandButton launchDroneButton;
    public CommandButton armIntakeButton;
    public CommandButton shoulderDecrementButton;
    public CommandButton shoulderIncrementButton;
    public CommandButton shoulderResetZero;
    public CommandButton wristDecrementButton;
    public CommandButton wristIncrementButton;
    public CommandButton armFirstLine;
    public CommandButton ScrewExtend;
    public CommandButton ScrewRetract;
    public CommandButton HangButton;
    public CommandButton HangDown;
    public CommandButton armNeutralButton;
    public CommandButton armSecondLine;
    public CommandButton armThirdLine;
    public CommandButton override;
    public CommandButton manualSlurp;
    public CommandButton spit;
    public CommandButton stopIntake;
    public CommandButton largeShoulderInc;
    public CommandButton largeShoulderDec;

    public OperatorController(CommandGamepad g, Robot r) {
        this.robot = r;
        gamepad = g;
        override = g.leftTrigger.getAsButton(0.5);

        AssignNamedControllerButton();
        if (Setup.Connected.ARMSUBSYSTEM) {
            bindClawControls();
        }
        if (Setup.Connected.DRONESUBSYSTEM) {
            bindDroneControls();
        }
    }

    private void AssignNamedControllerButton() {
        armFirstLine = gamepad.ps_circle;
        armSecondLine = gamepad.ps_triangle;
        largeShoulderDec = gamepad.leftStickButton;
        largeShoulderInc = gamepad.rightStickButton;
        armIntakeButton = gamepad.ps_cross;
        shoulderResetZero = gamepad.ps_options;
        shoulderDecrementButton = gamepad.dpadDown;
        shoulderIncrementButton = gamepad.dpadUp;
        wristDecrementButton = gamepad.dpadRight;
        wristIncrementButton = gamepad.dpadLeft;
        armNeutralButton = gamepad.ps_square;
        manualSlurp = gamepad.leftBumper;
        spit = gamepad.rightBumper;

        HangButton = gamepad.rightTrigger.getAsButton(); //put all of hang in here

        launchDroneButton = gamepad.ps_share;
    }

    public void bindClawControls() {
        armIntakeButton.whenPressed(EZCmd.Arm.Intake(robot.armSubsystem));
        armNeutralButton.whenPressed(EZCmd.Arm.Neutral(robot.armSubsystem));
        armFirstLine.whenPressed(EZCmd.Arm.SecondLine(robot.armSubsystem));
        armSecondLine.whenPressed(EZCmd.Arm.SecondLine(robot.armSubsystem));
        armThirdLine.whenPressed(EZCmd.Arm.ThirdLine(robot.armSubsystem));
        shoulderResetZero.whenPressed(EZCmd.Shoulder.Neutral(robot.armSubsystem));
        shoulderIncrementButton.whenPressed(EZCmd.Shoulder.Increment(robot.armSubsystem));
        shoulderDecrementButton.whenPressed(EZCmd.Shoulder.Decrement(robot.armSubsystem));
        wristIncrementButton.whenPressed(EZCmd.Wrist.Increment(robot.armSubsystem));
        wristDecrementButton.whenPressed(EZCmd.Wrist.Decrement(robot.armSubsystem));
        HangButton.whenPressed(EZCmd.Hang.Auto(robot.armSubsystem));
        manualSlurp.whilePressed(EZCmd.Intake.Slurp(robot.armSubsystem));
        manualSlurp.whenReleased(EZCmd.Intake.Stop(robot.armSubsystem));
        spit.whenPressed(EZCmd.Intake.Spit(robot.armSubsystem));
        spit.whenReleased(EZCmd.Intake.Stop(robot.armSubsystem));
        largeShoulderDec.whenPressed(EZCmd.Shoulder.LargeDecrement(robot.armSubsystem));
        largeShoulderInc.whenPressed(EZCmd.Shoulder.LargeIncrement(robot.armSubsystem));
    }

    public void bindDroneControls() {
        launchDroneButton.whenPressed(EZCmd.Drone.Launch(robot.droneSubsystem));
    }
}
