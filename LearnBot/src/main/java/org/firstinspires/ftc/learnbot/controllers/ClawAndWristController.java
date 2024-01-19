package org.firstinspires.ftc.learnbot.controllers;

import com.technototes.library.control.CommandGamepad;
import org.firstinspires.ftc.learnbot.ClawAndWristBot;
import org.firstinspires.ftc.learnbot.commands.ClawDecCommand;
import org.firstinspires.ftc.learnbot.commands.ClawIncCommand;
import org.firstinspires.ftc.learnbot.commands.WristDecCommand;
import org.firstinspires.ftc.learnbot.commands.WristIncCommand;

public class ClawAndWristController {

    public ClawAndWristBot robot;

    public ClawAndWristController(CommandGamepad pad, ClawAndWristBot bot) {
        robot = bot;
        pad.dpad.up.whenPressed(new WristIncCommand(bot));
        pad.dpad.down.whenPressed(new WristDecCommand(bot));
        pad.dpad.left.whenPressed(new ClawIncCommand(bot));
        pad.dpad.right.whenPressed(new ClawDecCommand(bot));
    }
}
