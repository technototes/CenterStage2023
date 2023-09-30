package org.firstinspires.ftc.learnbot.controllers;

import com.technototes.library.control.CommandButton;
import com.technototes.library.control.CommandGamepad;
import org.firstinspires.ftc.learnbot.Robot;
import org.firstinspires.ftc.learnbot.commands.ServoLeft;
import org.firstinspires.ftc.learnbot.commands.ServoRight;
import org.firstinspires.ftc.learnbot.commands.TestMotorBackwardCmd;
import org.firstinspires.ftc.learnbot.commands.TestMotorForwardCmd;
import org.firstinspires.ftc.learnbot.commands.TestMotorStopCmd;

public class TestController {

    public Robot robot;
    public CommandGamepad gamepad;
    public CommandButton motorForward;
    public CommandButton motorBackward;
    public CommandButton motorStop;

    public CommandButton servoleft;

    public CommandButton servoright;

    public TestController(CommandGamepad g, Robot r) {
        this.gamepad = g;
        this.robot = r;
        this.servoleft = gamepad.ps_triangle;
        this.servoright = gamepad.ps_cross;
        this.motorStop = gamepad.ps_circle;
        this.servoleft.whenPressed(new ServoLeft(r.test));
        this.servoright.whenPressed((new ServoRight(r.test)));
        this.motorStop.whenPressed(new TestMotorStopCmd(r.test));
    }
}
