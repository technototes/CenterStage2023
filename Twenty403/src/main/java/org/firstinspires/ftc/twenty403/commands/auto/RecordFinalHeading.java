package org.firstinspires.ftc.twenty403.commands.auto;

import com.technototes.library.command.Command;
import org.firstinspires.ftc.twenty403.subsystems.DrivebaseSubsystem;

public class RecordFinalHeading implements Command {

    private DrivebaseSubsystem subsystem;
    protected double offset = 0;

    @Override
    public void execute() {
        subsystem.saveHeading(offset);
    }

    protected RecordFinalHeading(DrivebaseSubsystem d) {
        subsystem = d;
    }
}
