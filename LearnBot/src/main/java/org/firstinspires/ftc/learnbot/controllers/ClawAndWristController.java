package org.firstinspires.ftc.learnbot.controllers;

import com.technototes.library.control.CommandGamepad;
import org.firstinspires.ftc.learnbot.ClawAndWristBot;
import org.firstinspires.ftc.learnbot.commands.EZCmd;

public class ClawAndWristController {

    public ClawAndWristBot robot;

    public ClawAndWristController(CommandGamepad pad, ClawAndWristBot bot) {
        robot = bot;
        pad.dpad.up.whenPressed(EZCmd.ClawAndWrist.WristInc(bot.caw));
        pad.dpad.down.whenPressed(EZCmd.ClawAndWrist.WristDec(bot.caw));
        pad.dpad.left.whenPressed(EZCmd.ClawAndWrist.ClawInc(bot.caw));
        pad.dpad.right.whenPressed(EZCmd.ClawAndWrist.ClawDec(bot.caw));
    }
}
