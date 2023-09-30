package org.firstinspires.ftc.sixteen750.controls;

import com.technototes.library.control.CommandButton;
import com.technototes.library.control.CommandGamepad;
import org.firstinspires.ftc.sixteen750.Robot;
import org.firstinspires.ftc.sixteen750.commands.intake.EjectCommand;
import org.firstinspires.ftc.sixteen750.commands.intake.IntakeCommand;
import org.firstinspires.ftc.sixteen750.commands.intake.StopCommand;

public class OperatorController {

    public Robot robot;
    public CommandGamepad gamepad;

    public CommandButton intakeButton;
    public CommandButton ejectButton;
    public CommandButton stopButton;

    public OperatorController(CommandGamepad g, Robot r) {
        robot = r;
        gamepad = g;
        AssignNamedControllerButton();
        bindIntakeControls();
    }

    private void AssignNamedControllerButton() {
        intakeButton = gamepad.ps_triangle;
        stopButton = gamepad.ps_circle;
        ejectButton = gamepad.ps_cross;
    }

    private void bindIntakeControls() {
        intakeButton.whenPressed(new IntakeCommand(robot.intake));
        stopButton.whenPressed(new StopCommand(robot.intake));
        ejectButton.whenPressed(new EjectCommand(robot.intake));
    }
}
