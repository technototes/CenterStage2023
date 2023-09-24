package org.firstinspires.ftc.twenty403.controls;

import com.technototes.library.command.CommandScheduler;
import com.technototes.library.control.CommandButton;
import com.technototes.library.control.CommandGamepad;
import com.technototes.library.control.Stick;
import org.firstinspires.ftc.twenty403.Robot;
import org.firstinspires.ftc.twenty403.Setup;
import org.firstinspires.ftc.twenty403.commands.driving.JoystickDriveCommand;
import org.firstinspires.ftc.twenty403.commands.driving.ResetGyroCommand;

public class DriverController {

    public Robot robot;
    public CommandGamepad gamepad;

    public Stick driveLeftStick, driveRightStick;
    public CommandButton resetGyroButton;
    public CommandButton straighten;

    public DriverController(CommandGamepad g, Robot r) {
        this.robot = r;
        gamepad = g;
        AssignNamedControllerButton();
        if (Setup.Connected.DRIVEBASE) {
            bindDriveControls();
        }
    }

    private void AssignNamedControllerButton() {
        resetGyroButton = gamepad.ps_options;
        driveLeftStick = gamepad.leftStick;
        driveRightStick = gamepad.rightStick;
        straighten = gamepad.ps_share;
    }

    public void bindDriveControls() {
        CommandScheduler
            .getInstance()
            .scheduleJoystick(
                new JoystickDriveCommand(
                    robot.drivebaseSubsystem,
                    driveLeftStick,
                    driveRightStick,
                    () -> straighten.getAsBoolean()
                )
            );
        /*
            turboButton.whenPressed(new TurboCommand(robot.drivebaseSubsystem));
            turboButton.whenReleased(new SlowCommand(robot.drivebaseSubsystem));
            */
        resetGyroButton.whenPressed(new ResetGyroCommand(robot.drivebaseSubsystem));
    }
}
