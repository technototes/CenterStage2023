package org.firstinspires.ftc.learnbot.controllers;

import com.technototes.library.control.CommandAxis;
import com.technototes.library.control.CommandButton;
import com.technototes.library.control.CommandGamepad;
import com.technototes.library.logger.Loggable;
import org.firstinspires.ftc.learnbot.Robot;
import org.firstinspires.ftc.learnbot.commands.AnalogMotorControlCmd;
import org.firstinspires.ftc.learnbot.commands.liftCommands.LiftHighCommand;
import org.firstinspires.ftc.learnbot.commands.liftCommands.LiftLowCommand;
import org.firstinspires.ftc.learnbot.commands.liftCommands.LiftMidCommand;

public class TestController implements Loggable {

    public Robot robot;
    public CommandGamepad gamepad;

    public CommandButton servoleft;
    public CommandButton servoright;
    public CommandAxis motorAxis;
    public CommandButton modeToggle;

    public CommandButton liftLow, liftMid, liftHigh;

    public CommandAxis trigger;
    public CommandButton threshold;

    public AnalogMotorControlCmd motorMovement;

    public TestController(CommandGamepad g, Robot r) {
        this.gamepad = g;
        this.robot = r;
        this.liftLow = gamepad.ps_triangle;
        this.liftMid = gamepad.ps_cross;
        this.liftHigh = gamepad.ps_circle;
        //        this.servoleft.whenPressed(new ServoLeft(r.test));
        //        this.servoright.whenPressed((new ServoRight(r.test)));
        this.motorAxis = gamepad.rightStickY;
        this.modeToggle = gamepad.rightStickButton;
        //        this.motorMovement = new MotorMovementCommand(r.test, this.motorAxis);
        //        this.modeToggle.whenPressed(new ToggleMotorStopModeCommand(r.test));
        //        CommandScheduler.getInstance().scheduleJoystick(motorMovement);
        this.trigger = gamepad.leftTrigger;
        this.threshold = gamepad.rightTrigger.getAsButton(0.5);
    }

    public void bindControls() {
        liftLow.whenPressed(new LiftLowCommand(robot.placementSubsystem));
        liftMid.whenPressed(new LiftMidCommand(robot.placementSubsystem));
        liftHigh.whenPressed(new LiftHighCommand(robot.placementSubsystem));
    }
}
