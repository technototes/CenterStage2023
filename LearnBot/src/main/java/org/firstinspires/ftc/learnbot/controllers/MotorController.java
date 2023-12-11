package org.firstinspires.ftc.learnbot.controllers;

import com.technototes.library.command.CommandScheduler;
import com.technototes.library.control.CommandAxis;
import com.technototes.library.control.CommandButton;
import com.technototes.library.control.CommandGamepad;
import com.technototes.library.logger.Loggable;
import org.firstinspires.ftc.learnbot.OnlyMotorRobot;
import org.firstinspires.ftc.learnbot.Setup;
import org.firstinspires.ftc.learnbot.commands.AnalogMotorControlCmd;
import org.firstinspires.ftc.learnbot.commands.AnalogMotorDecCmd;
import org.firstinspires.ftc.learnbot.commands.AnalogMotorIncCmd;
import org.firstinspires.ftc.learnbot.commands.AnalogMotorToggleControlCmd;
import org.firstinspires.ftc.learnbot.commands.AnalogMotorToggleStopModeCmd;

public class MotorController implements Loggable {

    public CommandAxis motorAxis;
    public CommandButton toggleAnalogControl;
    public CommandButton toggleStopMode;

    public CommandButton motorIncButton;
    public CommandButton motorDecButton;
    public AnalogMotorControlCmd motorMovement;

    public MotorController(CommandGamepad g, OnlyMotorRobot r) {
        this.motorAxis = g.rightStickY;
        this.toggleAnalogControl = g.ps_cross;
        this.toggleStopMode = g.ps_circle;
        this.motorIncButton = g.dpadUp;
        this.motorDecButton = g.dpadDown;

        if (Setup.Connected.MOTOR) {
            this.motorMovement = new AnalogMotorControlCmd(r.motorTestSubsystem, motorAxis);
            CommandScheduler.getInstance().scheduleJoystick(motorMovement);
            toggleAnalogControl.whenPressed(new AnalogMotorToggleControlCmd(r.motorTestSubsystem));
            toggleStopMode.whenPressed(new AnalogMotorToggleStopModeCmd(r.motorTestSubsystem));
            motorDecButton.whenPressed(new AnalogMotorDecCmd(r.motorTestSubsystem));
            motorIncButton.whenPressed(new AnalogMotorIncCmd(r.motorTestSubsystem));
        }
    }
}
