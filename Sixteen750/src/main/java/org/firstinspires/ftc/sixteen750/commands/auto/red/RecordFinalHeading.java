package org.firstinspires.ftc.sixteen750.commands.auto.red;

import com.technototes.library.command.Command;
import org.firstinspires.ftc.sixteen750.subsystems.DrivebaseSubsystem;

public class RecordFinalHeading implements Command {

    private DrivebaseSubsystem subsystem;

    @Override
    public void execute() {
        // TODO: If 16750 picks this up... subsystem.saveHeading();
    }

    public RecordFinalHeading(DrivebaseSubsystem d) {
        subsystem = d;
    }
}
