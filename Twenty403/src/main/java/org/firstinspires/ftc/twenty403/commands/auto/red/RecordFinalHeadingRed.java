package org.firstinspires.ftc.twenty403.commands.auto.red;

import org.firstinspires.ftc.twenty403.commands.auto.RecordFinalHeading;
import org.firstinspires.ftc.twenty403.subsystems.DrivebaseSubsystem;

public class RecordFinalHeadingRed extends RecordFinalHeading {

    public RecordFinalHeadingRed(DrivebaseSubsystem d) {
        super(d);
        offset = 90; // Red heading is 90 deg offset from driving start (0)
    }
}
