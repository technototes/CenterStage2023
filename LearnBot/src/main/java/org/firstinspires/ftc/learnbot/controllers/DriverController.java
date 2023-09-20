package org.firstinspires.ftc.learnbot.controllers;

import com.technototes.library.command.CommandScheduler;
import com.technototes.library.control.CommandGamepad;
import com.technototes.library.control.Stick;
import org.firstinspires.ftc.learnbot.Robot;
import org.firstinspires.ftc.learnbot.commands.DriveCommand;

public class DriverController {

    public Robot robot;
    public Stick driveLeftStick, driveRightStick;

    public DriverController(CommandGamepad g, Robot r) {
        this.driveLeftStick = g.leftStick;
        this.driveRightStick = g.rightStick;
        this.robot = r;
        this.bindDriveControls();
    }

    public void bindDriveControls() {
        CommandScheduler
            .getInstance()
            .scheduleJoystick(
                new DriveCommand(robot.drivebaseSubsystem, driveLeftStick, driveRightStick)
            );
    }
}
