package org.firstinspires.ftc.learnbot.controllers;

import com.technototes.library.control.CommandButton;
import com.technototes.library.control.CommandGamepad;

import org.firstinspires.ftc.learnbot.Robot;

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
    }

}
