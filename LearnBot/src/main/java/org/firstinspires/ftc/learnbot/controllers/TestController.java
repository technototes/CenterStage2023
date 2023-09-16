package org.firstinspires.ftc.learnbot.controllers;

import com.technototes.library.control.CommandButton;
import com.technototes.library.control.CommandGamepad;

import org.firstinspires.ftc.learnbot.Robot;
import org.firstinspires.ftc.learnbot.commands.TestMotorBackwardCmd;
import org.firstinspires.ftc.learnbot.commands.TestMotorForwardCmd;
import org.firstinspires.ftc.learnbot.commands.TestMotorStopCmd;

public class TestController {
    public Robot robot;
    public CommandGamepad gamepad;
    public CommandButton motorForward;
    public CommandButton motorBackward;
    public CommandButton motorStop;

    public TestController(CommandGamepad g, Robot r){
        this.gamepad = g;
        this.robot = r;
        this.motorForward = gamepad.triangle;
        this.motorBackward = gamepad.cross;
        this.motorStop = gamepad.circle;
        this.motorForward.whenPressed(new TestMotorForwardCmd(r.test));
        this.motorBackward.whenPressed((new TestMotorBackwardCmd(r.test)));
        this.motorStop.whenPressed(new TestMotorStopCmd(r.test));
    }

}
