package org.firstinspires.ftc.sixteen750.controls;

import com.technototes.library.control.CommandButton;
import com.technototes.library.control.CommandGamepad;

import org.firstinspires.ftc.sixteen750.Robot;
import org.firstinspires.ftc.sixteen750.Setup;
import org.firstinspires.ftc.sixteen750.commands.intake.EjectCommand;
import org.firstinspires.ftc.sixteen750.commands.intake.IntakeCommand;
import org.firstinspires.ftc.sixteen750.commands.intake.StopCommand;
import org.firstinspires.ftc.sixteen750.commands.placement.ArmServoInputCommand;
import org.firstinspires.ftc.sixteen750.commands.placement.ArmServoOutputCommand;
import org.firstinspires.ftc.sixteen750.commands.placement.LiftIntakeSequential;
import org.firstinspires.ftc.sixteen750.commands.placement.LiftLowSequential;
import org.firstinspires.ftc.sixteen750.commands.placement.LiftMediumCommand;
import org.firstinspires.ftc.sixteen750.commands.placement.ScoreHoldCommand;
import org.firstinspires.ftc.sixteen750.commands.placement.ServoHold;
import org.firstinspires.ftc.sixteen750.commands.placement.ScoreServoFlatCommand;
import org.firstinspires.ftc.sixteen750.commands.placement.ScoreServoInputCommand;
import org.firstinspires.ftc.sixteen750.commands.placement.ScoreServoOutputCommand;
import org.firstinspires.ftc.sixteen750.commands.placement.ServoIntakes;
import org.firstinspires.ftc.sixteen750.commands.placement.ServoOutputs;

public class OperatorController {

    public Robot robot;
    public CommandGamepad gamepad;
    public CommandButton intakeButton, ejectButton,stopButton,pauseButton;
    public CommandButton placementHighButton,placementLowButton,placementMediumButton,placementIntakeButton;
    public CommandButton armServoOutputButton, scoreServoOutputButton, servoOutputButton, scoreServoIntakeButton, armServoIntakeButton, servoIntakeButton;
    public CommandButton servoHoldButton, scoreServoFlatButton, scoreServoHoldButton;
    public CommandButton DroneReleaseButton;
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
        if (Setup.Connected.DRONE) {
//            bindDroneControls();
        }
    }

    private void AssignNamedControllerButton() {
        intakeButton = gamepad.leftBumper;
        ejectButton = gamepad.rightBumper;

//        placementHighButton = gamepad.dpadUp;
        placementIntakeButton = gamepad.dpadDown;
        placementMediumButton = gamepad.dpadRight;
        placementLowButton = gamepad.dpadLeft;

        armServoIntakeButton = gamepad.ps_triangle;
        armServoOutputButton = gamepad.ps_square;
        scoreServoHoldButton = gamepad.leftStickButton;

        scoreServoIntakeButton = gamepad.ps_circle;
        scoreServoOutputButton = gamepad.ps_cross;
        servoHoldButton = gamepad.rightStickButton;

        scoreServoFlatButton = gamepad.dpadUp;

        servoIntakeButton = gamepad.ps_share;
        //TODO: separate the output button
        servoOutputButton = gamepad.ps_options;

//        hangUpButton = gamepad.leftStickButton;
//        hangDownButton = gamepad.rightStickButton;

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
        placementLowButton.whenPressed(new LiftLowSequential(robot.placement));
        placementIntakeButton.whenPressed(new LiftIntakeSequential(robot.placement));

        armServoIntakeButton.whenPressed(new ArmServoInputCommand(robot.placement));
        armServoOutputButton.whenPressed(new ArmServoOutputCommand(robot.placement));
        scoreServoHoldButton.whenPressed(new ScoreHoldCommand(robot.placement));
        scoreServoIntakeButton.whenPressed(new ScoreServoInputCommand(robot.placement));
        scoreServoOutputButton.whenPressed(new ScoreServoOutputCommand(robot.placement));
        servoHoldButton.whenPressed(new ServoHold(robot.placement));
        scoreServoFlatButton.whenPressed(new ScoreServoFlatCommand(robot.placement));
        servoIntakeButton.whenPressed(new ServoIntakes(robot.placement));
        servoOutputButton.whenPressed(new ServoOutputs(robot.placement));
    }

    private void bindHangControls() {
//        hangUpButton.whenPressed(new HangUp(robot.hang));
//        screwUpButton.whenPressed(new LeadScrewUp(robot.hang));
//        screwDownButton.whenPressed(new LeadScrewDown(robot.hang));
//        screwDownButton.whenReleased(new LeadScrewStop(robot.hang));
//        screwUpButton.whenReleased(new LeadScrewStop(robot.hang));
//        hangDownButton.whenPressed(new HangDown(robot.hang));
    }
    private void bindDroneControls() {
//        DroneReleaseButton.whenPressed((new DroneLaunch(robot.drone)));
    }

}
