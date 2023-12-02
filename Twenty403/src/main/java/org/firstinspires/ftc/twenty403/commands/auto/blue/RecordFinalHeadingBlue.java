package org.firstinspires.ftc.twenty403.commands.auto.blue;

import org.firstinspires.ftc.twenty403.commands.auto.RecordFinalHeading;
import org.firstinspires.ftc.twenty403.subsystems.DrivebaseSubsystem;

public class RecordFinalHeadingBlue extends RecordFinalHeading {

    public RecordFinalHeadingBlue(DrivebaseSubsystem d) {
        super(d);
        offset = -90; // Red heading is -90 deg offset from driving start (0)
    }
}
