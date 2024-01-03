package org.firstinspires.ftc.sixteen750.controls;

import com.technototes.library.control.CommandButton;
import com.technototes.library.control.CommandGamepad;
import java.util.Set;
import org.firstinspires.ftc.sixteen750.Robot;
import org.firstinspires.ftc.sixteen750.Setup;
import org.firstinspires.ftc.sixteen750.commands.hang.HangDown;
import org.firstinspires.ftc.sixteen750.commands.hang.HangStop;
import org.firstinspires.ftc.sixteen750.commands.hang.HangUp;
import org.firstinspires.ftc.sixteen750.commands.hang.LeadScrewDown;
import org.firstinspires.ftc.sixteen750.commands.hang.LeadScrewStop;
import org.firstinspires.ftc.sixteen750.commands.hang.LeadScrewUp;
import org.firstinspires.ftc.sixteen750.commands.intake.EjectCommand;
import org.firstinspires.ftc.sixteen750.commands.intake.IntakeCommand;
import org.firstinspires.ftc.sixteen750.commands.intake.StopCommand;
import org.firstinspires.ftc.sixteen750.commands.placement.ArmServoOutputCommand;
import org.firstinspires.ftc.sixteen750.commands.placement.LiftHighCommand;
import org.firstinspires.ftc.sixteen750.commands.placement.LiftIntakeCommand;
import org.firstinspires.ftc.sixteen750.commands.placement.LiftIntakeSequential;
import org.firstinspires.ftc.sixteen750.commands.placement.LiftLowCommand;
import org.firstinspires.ftc.sixteen750.commands.placement.LiftLowSequential;
import org.firstinspires.ftc.sixteen750.commands.placement.LiftMediumCommand;
import org.firstinspires.ftc.sixteen750.commands.placement.LiftMediumSequential;
import org.firstinspires.ftc.sixteen750.commands.placement.ScoreServoOutputCommand;
import org.firstinspires.ftc.sixteen750.commands.placement.ServoOutputs;

public class OperatorController {

    public Robot robot;
    public CommandGamepad gamepad;

    public CommandButton intakeButton, ejectButton,stopButton,pauseButton;
    public CommandButton placementHighButton,placementLowButton,placementMediumButton,placementIntakeButton;
    public CommandButton armServoOutputButton, scoreServoOutputButton, servoOutputButton;

    public CommandButton hangUpButton,hangDownButton,screwUpButton,screwDownButton;
    public OperatorController(CommandGamepad g, Robot r) {
        robot = r;
        gamepad = g;
        AssignNamedControllerButton();
        if (Setup.Connected.INTAKE) {
            bindIntakeControls();
        }
        if (Setup.Connected.HANG) {
            bindHangControls();
        }
        if (Setup.Connected.PLACEMENT) {
            bindPlacementControls();
        }
    }

    private void AssignNamedControllerButton() {
        intakeButton = gamepad.ps_square;
        ejectButton = gamepad.ps_cross;

//        placementHighButton = gamepad.dpadUp;
        placementIntakeButton = gamepad.dpadDown;
        placementMediumButton = gamepad.dpadRight;
        placementLowButton = gamepad.dpadLeft;

        armServoOutputButton = gamepad.ps_triangle;
        scoreServoOutputButton = gamepad.ps_circle;
        servoOutputButton = gamepad.leftBumper;

        hangUpButton = gamepad.leftStickButton;
        hangDownButton = gamepad.rightStickButton;

//        screwUpButton = gamepad.rightBumper;
//        screwDownButton = gamepad.leftBumper;
    }

    private void bindIntakeControls() {
        intakeButton.whenPressed(new IntakeCommand(robot.intake));
        intakeButton.whenReleased(new StopCommand(robot.intake));
        ejectButton.whenPressed(new EjectCommand(robot.intake));
        ejectButton.whenReleased(new StopCommand(robot.intake));
    }

    private void bindPlacementControls() {
//        placementHighButton.whenPressed(new LiftHighCommand(robot.placement));
        placementMediumButton.whenPressed(new LiftMediumCommand(robot.placement));
        placementLowButton.whenPressed(new LiftLowCommand(robot.placement));
        placementIntakeButton.whenPressed(new LiftIntakeSequential(robot.placement));
        armServoOutputButton.whenPressed(new ArmServoOutputCommand(robot.placement));
        scoreServoOutputButton.whenPressed(new ScoreServoOutputCommand(robot.placement));
        servoOutputButton.whenPressed(new ServoOutputs(robot.placement));
    }

    private void bindHangControls() {
        hangUpButton.whenPressed(new HangUp(robot.hang));
//        screwUpButton.whenPressed(new LeadScrewUp(robot.hang));
//        screwDownButton.whenPressed(new LeadScrewDown(robot.hang));
//        screwDownButton.whenReleased(new LeadScrewStop(robot.hang));
//        screwUpButton.whenReleased(new LeadScrewStop(robot.hang));
        hangDownButton.whenPressed(new HangDown(robot.hang));
    }
}
