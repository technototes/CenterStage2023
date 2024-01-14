package org.firstinspires.ftc.sixteen750.commands.auto.red;

import android.util.Pair;

import com.technototes.library.command.ChoiceCommand;

import org.firstinspires.ftc.sixteen750.Robot;

public class WingPixelPlaceSelection extends ChoiceCommand {

    public WingPixelPlaceSelection(Robot r) {
        // Each of these commands starts at "START" and ends at "CLEAR"
        // So you can use this command as the first part of a command sequence
        super(
            new Pair<>(r.vision.pipeline::left, new WingPixelPlaceLeft(r)),
            new Pair<>(r.vision.pipeline::middle, new WingPixelPlaceMiddle(r)),
            new Pair<>(r.vision.pipeline::right, new WingPixelPlaceRight(r)) //this has more code than other 2
        );
    }
}
