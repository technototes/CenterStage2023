package org.firstinspires.ftc.twenty403.controls;

import com.technototes.library.control.CommandButton;
import com.technototes.library.control.CommandGamepad;

import org.firstinspires.ftc.twenty403.Robot;
import org.firstinspires.ftc.twenty403.Setup;
import org.firstinspires.ftc.twenty403.commands.claw.ClawCloseCommand;
import org.firstinspires.ftc.twenty403.commands.claw.ClawOpenCommand;

public class OperatorController {
    public Robot robot;
    public CommandGamepad gamepad;


    public CommandButton clawOpenButton, clawCloseButton;

    public OperatorController(CommandGamepad g, Robot r) {
        this.robot = r;
        gamepad = g;
        AssignNamedControllerButton();
        if (Setup.Connected.CLAWSUBSYSTEM) {
            bindClawControls();
        }
    }

    private void AssignNamedControllerButton() {
        clawOpenButton = gamepad.leftBumper;
        clawCloseButton = gamepad.leftBumper;
    }


    public void bindClawControls() {
        clawOpenButton.whenPressed(new ClawOpenCommand(robot.clawSubsystem));
        clawCloseButton.whenReleased(new ClawCloseCommand(robot.clawSubsystem));
    }
}