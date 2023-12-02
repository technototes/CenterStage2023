package org.firstinspires.ftc.sixteen750.commands.auto;

import com.technototes.library.command.Command;
import org.firstinspires.ftc.sixteen750.subsystems.DrivebaseSubsystem;

public class RecordFinalHeading implements Command {

    private DrivebaseSubsystem subsystem;

    @Override
    public void execute() {
        subsystem.saveHeading();
    }

    public RecordFinalHeading(DrivebaseSubsystem d) {
        subsystem = d;
    }
}
