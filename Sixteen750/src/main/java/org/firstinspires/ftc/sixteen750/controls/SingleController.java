package org.firstinspires.ftc.sixteen750.controls;

import com.technototes.library.command.CommandScheduler;
import com.technototes.library.control.CommandButton;
import com.technototes.library.control.CommandGamepad;
import com.technototes.library.control.Stick;
import org.firstinspires.ftc.sixteen750.Robot;
import org.firstinspires.ftc.sixteen750.Setup;
import org.firstinspires.ftc.sixteen750.commands.driving.JoystickDriveCommand;
import org.firstinspires.ftc.sixteen750.commands.driving.ResetGyroCommand;
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
    public CommandButton clawOpenButton, clawCloseButton;
    public CommandButton intakeButton;
    public CommandButton ejectButton;
    public CommandButton stopButton;

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
        turboButton = gamepad.leftStickButton;
        driveStraight = gamepad.rightTrigger.getAsButton(0.5);


        //lift buttons
        placementHighButton = gamepad.dpadUp;
        placementIntakeButton = gamepad.dpadRight;
        placementMediumButton = gamepad.dpadLeft;
        placementLowButton = gamepad.dpadDown;

        // intaking the pixel from ground
        intakeButton = gamepad.ps_triangle;
        stopButton = gamepad.ps_circle;
        ejectButton = gamepad.ps_cross;
    }

    public void bindDriveControls() {
        CommandScheduler
            .getInstance()
            .scheduleJoystick(
                new JoystickDriveCommand(robot.drivebase, driveLeftStick, driveRightStick)
            );
        //turboButton.whenPressed(new TurboCommand(robot.drivebaseSubsystem));
        //turboButton.whenReleased(new SlowCommand(robot.drivebaseSubsystem));
        resetGyroButton.whenPressed(new ResetGyroCommand(robot.drivebase));
    }

    private void bindIntakeControls() {
        intakeButton.whenPressed(new IntakeCommand(robot.intake));
        stopButton.whenPressed(new StopCommand(robot.intake));
        ejectButton.whenPressed(new EjectCommand(robot.intake));
    }

    public void bindLiftControls() {
        placementHighButton.whenPressed(new LiftHighCommand(robot.placement));
        placementMediumButton.whenPressed(new LiftMediumCommand(robot.placement));
        placementLowButton.whenPressed(new LiftLowCommand(robot.placement));
        placementIntakeButton.whenPressed(new LiftIntakeCommand(robot.placement));

    }
}
