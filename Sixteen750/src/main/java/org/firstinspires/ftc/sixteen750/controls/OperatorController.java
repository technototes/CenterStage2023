package org.firstinspires.ftc.sixteen750.controls;

import com.technototes.library.control.CommandButton;
import com.technototes.library.control.CommandGamepad;
import org.firstinspires.ftc.sixteen750.Robot;
import org.firstinspires.ftc.sixteen750.Setup;
import org.firstinspires.ftc.sixteen750.commands.hang.HangUp;
import org.firstinspires.ftc.sixteen750.commands.hang.LeadScrewDown;
import org.firstinspires.ftc.sixteen750.commands.hang.LeadScrewUp;
import org.firstinspires.ftc.sixteen750.commands.intake.EjectCommand;
import org.firstinspires.ftc.sixteen750.commands.intake.IntakeCommand;
import org.firstinspires.ftc.sixteen750.commands.intake.StopCommand;
import org.firstinspires.ftc.sixteen750.commands.placement.LiftHighCommand;
import org.firstinspires.ftc.sixteen750.commands.placement.LiftIntakeCommand;
import org.firstinspires.ftc.sixteen750.commands.placement.LiftLowCommand;
import org.firstinspires.ftc.sixteen750.commands.placement.LiftMediumCommand;

public class OperatorController {

    public Robot robot;
    public CommandGamepad gamepad;

    public CommandButton intakeButton;
    public CommandButton ejectButton;
    public CommandButton stopButton;

    public CommandButton placementHighButton;
    public CommandButton placementLowButton;
    public CommandButton placementMediumButton;
    public CommandButton placementIntakeButton;

    public CommandButton hangUpButton;
    public CommandButton screwUpButton;
    public CommandButton screwDownButton;

    public OperatorController(CommandGamepad g, Robot r) {
        robot = r;
        gamepad = g;
        AssignNamedControllerButton();
        bindIntakeControls();
        if (Setup.Connected.HANG) {
            bindHangControls();
        }
    }

    private void AssignNamedControllerButton() {
        intakeButton = gamepad.ps_triangle;
        stopButton = gamepad.ps_circle;
        ejectButton = gamepad.ps_cross;

        placementHighButton = gamepad.dpadUp;
        placementIntakeButton = gamepad.dpadRight;
        placementMediumButton = gamepad.dpadLeft;
        placementLowButton = gamepad.dpadDown;

        hangUpButton = gamepad.ps_square;
        screwUpButton = gamepad.rightBumper;
        screwDownButton = gamepad.leftBumper;
    }

    private void bindIntakeControls() {
        intakeButton.whenPressed(new IntakeCommand(robot.intake));
        stopButton.whenPressed(new StopCommand(robot.intake));
        ejectButton.whenPressed(new EjectCommand(robot.intake));
        placementHighButton.whenPressed(new LiftHighCommand(robot.placement));
        placementMediumButton.whenPressed(new LiftMediumCommand(robot.placement));
        placementLowButton.whenPressed(new LiftLowCommand(robot.placement));
        placementIntakeButton.whenPressed(new LiftIntakeCommand(robot.placement));
    }

    private void bindHangControls() {
        hangUpButton.whenPressed(new HangUp(robot.hang));
        screwUpButton.whenPressed(new LeadScrewUp(robot.hang));
        screwDownButton.whenPressed(new LeadScrewDown(robot.hang));
    }
}
