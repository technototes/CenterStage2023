package org.firstinspires.ftc.sixteen750.commands.auto.red;

import android.util.Pair;

import com.technototes.library.command.ChoiceCommand;

import org.firstinspires.ftc.sixteen750.Robot;

public class StagePixelPlaceSelection extends ChoiceCommand {

    public StagePixelPlaceSelection(Robot r) {
        // Each of these commands starts at "START" and ends at "CLEAR"
        // So you can use this command as the first part of a command sequence
        super(
            new Pair<>(r.vision.pipeline::left, new StagePixelPlaceLeft(r)),
            new Pair<>(r.vision.pipeline::middle, new StagePixelPlaceMiddle(r)),
            new Pair<>(r.vision.pipeline::right, new StagePixelPlaceRight(r))
        );
    }
}
