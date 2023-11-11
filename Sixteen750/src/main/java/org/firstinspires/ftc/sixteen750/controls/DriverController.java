package org.firstinspires.ftc.sixteen750.controls;

import com.technototes.library.command.CommandScheduler;
import com.technototes.library.control.CommandAxis;
import com.technototes.library.control.CommandButton;
import com.technototes.library.control.CommandGamepad;
import com.technototes.library.control.Stick;
import org.firstinspires.ftc.sixteen750.Robot;
import org.firstinspires.ftc.sixteen750.Setup;
import org.firstinspires.ftc.sixteen750.commands.driving.JoystickDriveCommand;
import org.firstinspires.ftc.sixteen750.commands.driving.NormalModeCommand;
import org.firstinspires.ftc.sixteen750.commands.driving.ResetGyroCommand;
import org.firstinspires.ftc.sixteen750.commands.driving.SnailModeCommand;
import org.firstinspires.ftc.sixteen750.commands.driving.TurboModeCommand;

public class DriverController {

    public Robot robot;
    public CommandGamepad gamepad;

    public Stick driveLeftStick, driveRightStick;
    public CommandButton resetGyroButton, rotationStraighten, turboButton, snailButton;
    public CommandButton override;
    public CommandAxis driveStraighten;
    public DriverController(CommandGamepad g, Robot r) {
        this.robot = r;
        gamepad = g;
        override = g.leftTrigger.getAsButton(0.5);

        AssignNamedControllerButton();
        if (Setup.Connected.DRIVEBASE) {
            bindDriveControls();
        }
    }

    private void AssignNamedControllerButton() {
        resetGyroButton = gamepad.ps_options;
        driveLeftStick = gamepad.leftStick;
        driveRightStick = gamepad.rightStick;
        rotationStraighten = gamepad.ps_share;
        driveStraighten = gamepad.rightTrigger;
        turboButton = gamepad.rightBumper;
        snailButton = gamepad.leftBumper;


    }

    public void bindDriveControls() {
        CommandScheduler
            .getInstance()
            .scheduleJoystick(
                new JoystickDriveCommand(
                    robot.drivebase,
                    driveLeftStick,
                    driveRightStick,
                    rotationStraighten,
                        driveStraighten
                )
            );

        turboButton.whenPressed(new TurboModeCommand(robot.drivebase));
        turboButton.whenReleased(new NormalModeCommand(robot.drivebase));
        snailButton.whenPressed(new SnailModeCommand(robot.drivebase));
        snailButton.whenReleased(new NormalModeCommand(robot.drivebase));

        resetGyroButton.whenPressed(new ResetGyroCommand(robot.drivebase));
    }
}
