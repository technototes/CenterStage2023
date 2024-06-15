package org.firstinspires.ftc.sixteen750.controls;

import com.technototes.library.control.CommandButton;
import com.technototes.library.control.CommandGamepad;
import org.firstinspires.ftc.sixteen750.Robot;
import org.firstinspires.ftc.sixteen750.Setup;
import org.firstinspires.ftc.sixteen750.commands.DroneLaunch;
import org.firstinspires.ftc.sixteen750.commands.hang.HangStop;
import org.firstinspires.ftc.sixteen750.commands.hang.HangUp;
import org.firstinspires.ftc.sixteen750.commands.hang.LeadScrewDown;
import org.firstinspires.ftc.sixteen750.commands.hang.LeadScrewUp;
import org.firstinspires.ftc.sixteen750.commands.intake.EjectCommand;
import org.firstinspires.ftc.sixteen750.commands.intake.IntakeCommand;
import org.firstinspires.ftc.sixteen750.commands.intake.StopCommand;
import org.firstinspires.ftc.sixteen750.commands.placement.ArmServoInputCommand;
import org.firstinspires.ftc.sixteen750.commands.placement.ArmServoOutputCommand;
import org.firstinspires.ftc.sixteen750.commands.placement.LiftHighCommand;
import org.firstinspires.ftc.sixteen750.commands.placement.LiftIntakeCommand;
import org.firstinspires.ftc.sixteen750.commands.placement.LiftLowCommand;
import org.firstinspires.ftc.sixteen750.commands.placement.LiftMediumCommand;
import org.firstinspires.ftc.sixteen750.commands.placement.ScoreServoInputCommand;
import org.firstinspires.ftc.sixteen750.commands.placement.ScoreServoOutputCommand;
import org.firstinspires.ftc.sixteen750.subsystems.DroneSubsystem;

public class TestingController {

    public Robot robot;
    public CommandGamepad gamepad;

    public CommandButton intakeButton;
    public CommandButton ejectButton;
    public CommandButton stopButton;

    public CommandButton pauseButton;

    public CommandButton placementHighButton;
    public CommandButton placementLowButton;
    public CommandButton placementMediumButton;
    public CommandButton placementIntakeButton;

    public CommandButton armServoOutputButton, scoreServoOutputButton, armServoIntakeButton, scoreServoIntakeButton;

    public CommandButton hangUpButton;
    public CommandButton screwUpButton, screwDownButton;

    public CommandButton DroneReleaseButton;

    public TestingController(CommandGamepad g, Robot r) {
        robot = r;
        gamepad = g;
        AssignNamedControllerButton();
        if (Setup.Connected.INTAKE) {
            //            bindIntakeControls();
        }
        if (Setup.Connected.HANG) {
            //            bindHangControls();
        }
        if (Setup.Connected.PLACEMENT) {
            bindPlacementControls();
        }

        if (Setup.Connected.DRONE) {
            bindDroneControls();
        }
    }

    private void AssignNamedControllerButton() {
        //        intakeButton = gamepad.ps_triangle;
        //        stopButton = gamepad.ps_cross;
        //        ejectButton = gamepad.ps_cross;
        //        pauseButton = gamepad.ps_triangle;

        placementHighButton = gamepad.dpadUp;
        placementIntakeButton = gamepad.dpadRight;
        placementMediumButton = gamepad.dpadLeft;
        placementLowButton = gamepad.dpadDown;

        armServoIntakeButton = gamepad.ps_triangle;
        armServoOutputButton = gamepad.ps_square;
        //        scoreServoIntakeButton = gamepad.ps_circle;
        scoreServoOutputButton = gamepad.ps_cross;

        //hangUpButton = gamepad.ps_square;
        //screwUpButton = gamepad.rightBumper;
        //screwDownButton = gamepad.leftBumper;
        DroneReleaseButton = gamepad.ps_circle;
    }

    private void bindIntakeControls() {
        intakeButton.whenPressed(new IntakeCommand(robot.intake));
        stopButton.whenReleased(new StopCommand(robot.intake));
        ejectButton.whenPressed(new EjectCommand(robot.intake));
        pauseButton.whenReleased(new StopCommand(robot.intake));
    }

    private void bindPlacementControls() {
        placementHighButton.whenPressed(new LiftHighCommand(robot.placement));
        placementMediumButton.whenPressed(new LiftMediumCommand(robot.placement));
        placementLowButton.whenPressed(new LiftLowCommand(robot.placement));
        placementIntakeButton.whenPressed(new LiftIntakeCommand(robot.placement));

        armServoOutputButton.whenPressed(new ArmServoOutputCommand(robot.placement));
        armServoIntakeButton.whenPressed(new ArmServoInputCommand(robot.placement));
        //        scoreServoIntakeButton.whenPressed(new ScoreServoInputCommand(robot.placement));
        scoreServoOutputButton.whenPressed(new ScoreServoOutputCommand(robot.placement));
    }

    private void bindHangControls() {
        hangUpButton.whenPressed(new HangUp(robot.hang));
        screwUpButton.whenPressed(new LeadScrewUp(robot.hang));
        screwDownButton.whenPressed(new LeadScrewDown(robot.hang));
        screwDownButton.whenReleased(new HangStop(robot.hang));
        screwUpButton.whenReleased(new HangStop(robot.hang));
    }

    private void bindDroneControls() {
        DroneReleaseButton.whenPressed((new DroneLaunch(robot.drone)));
    }
}
