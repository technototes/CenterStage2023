package org.firstinspires.ftc.twenty403.controls;

import com.technototes.library.command.CommandScheduler;
import com.technototes.library.command.ConditionalCommand;
import com.technototes.library.control.CommandButton;
import com.technototes.library.control.CommandGamepad;
import com.technototes.library.control.Stick;

import org.firstinspires.ftc.twenty403.Robot;
import org.firstinspires.ftc.twenty403.Setup;

public class SingleDriver {
    public Robot robot;
    public Setup setup;
    public CommandGamepad gamepad;

    public Stick driveLeftStick, driveRightStick;
    public CommandButton resetGyroButton, driveStraight, turboButton;

    public SingleDriver(CommandGamepad g, Robot r, Setup s) {
        this.robot = r;
        this.setup = s;
        gamepad = g;


        AssignNamedControllerButton();

        if (Setup.Connected.DRIVEBASE) {
            bindDriveControls();
        }

        if (Setup.Connected.WEBCAM) {
            // TODO: bindAlignControls();
        }
    }

    private void AssignNamedControllerButton() {
        resetGyroButton = gamepad.rightStickButton;
        driveLeftStick = gamepad.leftStick;
        driveRightStick = gamepad.rightStick;
        turboButton = gamepad.leftStickButton;
        driveStraight = gamepad.rightTrigger.getAsButton(0.5);


        watchButton = gamepad.options;
        // TODO: Identify other controls for
    }

    public void bindDriveControls() {
        CommandScheduler
                .getInstance()
                .scheduleJoystick(
                        new DriveCommand(
                                robot.drivebaseSubsystem,
                                driveLeftStick,
                                driveRightStick,
                                driveStraight,
                                watchButton,
                                robot.visionSystem.visionPipeline
                        )
                );
        turboButton.whenPressed(new TurboCommand(robot.drivebaseSubsystem));
        turboButton.whenReleased(new SlowCommand(robot.drivebaseSubsystem));
        // TODO: We probably want buttons to reset the Gyro...
        resetGyroButton.whenPressed(new ResetGyroCommand(robot.drivebaseSubsystem));
    }

    public void bindClawControls() {
        // TODO: Name & Bind claw controls
        clawOpenButton.whenPressed(new ClawOpenCommand(robot.clawSubsystem));
        clawCloseButton.whenReleased(new ClawCloseCommand(robot.clawSubsystem));
    }

    public void bindLiftControls() {
        // TODO: Name & Bind lift controls
        liftUpButton.whenPressed(new LiftUpCommand(robot.liftSubsystem));
        liftDownButton.whenPressed(new LiftDownCommand(robot.liftSubsystem));
        liftIntakePos.whenPressed(new LiftIntakeCommand(robot.liftSubsystem));
        liftOverrideZeroButton.whenPressed(
                new ConditionalCommand(override, new LiftSetZeroCommand(robot.liftSubsystem))
        );

        liftGroundOrOverrideDown.whenPressed(
                new ConditionalCommand(
                        override,
                        new LiftMoveDownOverrideCommand(robot.liftSubsystem),
                        new LiftGroundJunctionCommand(robot.liftSubsystem)
                )
        );
        liftLowOrOverrideUp.whenPressed(
                new ConditionalCommand(
                        override,
                        new LiftMoveUpOverrideCommand(robot.liftSubsystem),
                        new LiftLowJunctionCommand(robot.liftSubsystem)
                )
        );
        liftMedium.whenPressed(new LiftMidJunctionCommand(robot.liftSubsystem));
        liftHigh.whenPressed(new LiftHighJunctionCommand(robot.liftSubsystem));
    }
}
