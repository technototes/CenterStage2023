package org.firstinspires.ftc.sixteen750.controls;

import com.technototes.library.command.CommandScheduler;
import com.technototes.library.control.CommandButton;
import com.technototes.library.control.CommandGamepad;
import com.technototes.library.control.Stick;
import org.firstinspires.ftc.sixteen750.Robot;
import org.firstinspires.ftc.sixteen750.Setup;
import org.firstinspires.ftc.sixteen750.commands.driving.JoystickDriveCommand;
import org.firstinspires.ftc.sixteen750.commands.driving.ResetGyroCommand;
import org.firstinspires.ftc.sixteen750.commands.driving.SlowCommand;
import org.firstinspires.ftc.sixteen750.commands.driving.TurboCommand;
import org.firstinspires.ftc.sixteen750.commands.intake.EjectCommand;
import org.firstinspires.ftc.sixteen750.commands.intake.IntakeCommand;
import org.firstinspires.ftc.sixteen750.commands.intake.StopCommand;
import org.firstinspires.ftc.sixteen750.commands.placement.LiftHighCommand;
import org.firstinspires.ftc.sixteen750.commands.placement.LiftIntakeCommand;
import org.firstinspires.ftc.sixteen750.commands.placement.LiftLowCommand;
import org.firstinspires.ftc.sixteen750.commands.placement.LiftMediumCommand;

public class SingleController {

    public Robot robot;
    public Setup setup;
    public CommandGamepad gamepad;

    public Stick driveLeftStick, driveRightStick;
    public CommandButton resetGyroButton, driveStraight, turboButton;
    public CommandButton intakeButton;
    public CommandButton ejectButton;
    public CommandButton stopButton, pauseButton;

    public CommandButton placementHighButton;
    public CommandButton placementLowButton;
    public CommandButton placementMediumButton;
    public CommandButton placementIntakeButton;

    public SingleController(CommandGamepad g, Robot r, Setup s) {
        this.robot = r;
        this.setup = s;
        gamepad = g;

        AssignNamedControllerButton();

        if (Setup.Connected.DRIVEBASE) {
            bindDriveControls();
        }

        if (Setup.Connected.INTAKE) {
            bindIntakeControls();
        }
            if (Setup.Connected.PLACEMENT) {
                bindLiftControls();
        }
        if (Setup.Connected.WEBCAM) {
            // TODO: bindAlignControls();
        }
    }

    private void AssignNamedControllerButton() {
        //drive buttons
        resetGyroButton = gamepad.rightStickButton;
        driveLeftStick = gamepad.leftStick;
        driveRightStick = gamepad.rightStick;
        turboButton = gamepad.rightBumper;
        driveStraight = gamepad.leftBumper;


        //lift buttons
        placementHighButton = gamepad.dpadUp;
        placementIntakeButton = gamepad.dpadRight;
        placementMediumButton = gamepad.dpadLeft;
        placementLowButton = gamepad.dpadDown;

        // intaking the pixel from ground
        intakeButton = gamepad.ps_triangle;
        ejectButton = gamepad.ps_cross;
        stopButton = gamepad.ps_cross;
        pauseButton = gamepad.ps_triangle;
    }

    public void bindDriveControls() {
        CommandScheduler
            .getInstance()
            .scheduleJoystick(
                new JoystickDriveCommand(robot.drivebase, driveLeftStick, driveRightStick)
            );
        turboButton.whenPressed(new TurboCommand(robot.drivebase));
        turboButton.whenReleased(new SlowCommand(robot.drivebase));
        resetGyroButton.whenPressed(new ResetGyroCommand(robot.drivebase));
    }

    private void bindIntakeControls() {
        intakeButton.whenPressed(new IntakeCommand(robot.intake));
        stopButton.whenReleased(new StopCommand(robot.intake));
        ejectButton.whenPressed(new EjectCommand(robot.intake));
        pauseButton.whenReleased(new StopCommand(robot.intake));
    }

    public void bindLiftControls() {
        placementHighButton.whenPressed(new LiftHighCommand(robot.placement));
        placementMediumButton.whenPressed(new LiftMediumCommand(robot.placement));
        placementLowButton.whenPressed(new LiftLowCommand(robot.placement));
        placementIntakeButton.whenPressed(new LiftIntakeCommand(robot.placement));

    }
}
