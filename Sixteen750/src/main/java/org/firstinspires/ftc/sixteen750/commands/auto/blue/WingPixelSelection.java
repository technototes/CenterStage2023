package org.firstinspires.ftc.sixteen750.commands.auto.blue;

import android.util.Pair;

import com.technototes.library.command.ChoiceCommand;

import org.firstinspires.ftc.twenty403.Robot;

public class WingPixelSelection extends ChoiceCommand {

    public WingPixelSelection(Robot r) {
        // Each of these commands starts at "START" and ends at "CLEAR"
        // So you can use this command as the first part of a command sequence
        super(
            new Pair<>(r.vision.pipeline::left, new WingPixelLeft(r)),
            new Pair<>(r.vision.pipeline::middle, new WingPixelMiddle(r)),
            new Pair<>(r.vision.pipeline::right, new WingPixelRight(r))
        );
    }
}
