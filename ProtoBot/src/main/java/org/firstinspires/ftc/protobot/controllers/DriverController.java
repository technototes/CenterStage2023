package org.firstinspires.ftc.protobot.controllers;

import com.technototes.library.command.CommandScheduler;
import com.technototes.library.control.CommandAxis;
import com.technototes.library.control.CommandGamepad;
import com.technototes.library.control.Stick;
import org.firstinspires.ftc.protobot.Robot;
import org.firstinspires.ftc.protobot.commands.DriveCommand;

public class DriverController {

    public Robot robot;
    public Stick driveLeftStick, driveRightStick;
    public CommandAxis driveStraight;

    public DriverController(CommandGamepad g, Robot r) {
        this.driveLeftStick = g.leftStick;
        this.driveRightStick = g.rightStick;
        this.robot = r;
        this.driveStraight = g.leftTrigger;
        this.bindDriveControls();
    }

    public void bindDriveControls() {
        CommandScheduler
            .getInstance()
            .scheduleJoystick(
                new DriveCommand(
                    robot.drivebaseSubsystem,
                    driveLeftStick,
                    driveRightStick,
                    driveStraight
                )
            );
    }
}
