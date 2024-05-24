package org.firstinspires.ftc.learnbot.controllers;

import com.technototes.library.command.CommandScheduler;
import com.technototes.library.control.CommandAxis;
import com.technototes.library.control.CommandButton;
import com.technototes.library.control.CommandGamepad;
import com.technototes.library.control.Stick;
import org.firstinspires.ftc.learnbot.Robot;
import org.firstinspires.ftc.learnbot.commands.DriveCommand;

public class DriverController {

    public Robot robot;
    public CommandGamepad gamepad;

    public Stick driveLeftStick, driveRightStick;
    public CommandButton resetGyroButton, turboButton, snailButton;
    public CommandButton override;
//    TODO: Add drive straighten
//    public CommandAxis driveStraighten;


    public DriverController(CommandGamepad g, Robot r) {
        this.robot = r;
        gamepad = g;

        AssignNamedControllerButton();
//        if (Setup.Connected.DRIVEBASE) {
            bindDriveControls();
//        }

    }
    private void AssignNamedControllerButton() {
        resetGyroButton = gamepad.ps_options;
        driveLeftStick = gamepad.leftStick;
        driveRightStick = gamepad.rightStick;
//        driveStraighten = gamepad.rightTrigger;
        turboButton = gamepad.leftBumper;
        snailButton = gamepad.rightBumper;
    }


    public void bindDriveControls() {
        CommandScheduler
            .getInstance()
            .scheduleJoystick(
                new DriveCommand(
                        robot.drivebaseSubsystem,
                        driveLeftStick,
                        driveRightStick)
            );
    }
}
