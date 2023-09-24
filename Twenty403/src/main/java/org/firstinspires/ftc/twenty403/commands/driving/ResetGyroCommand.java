package org.firstinspires.ftc.twenty403.commands.driving;

import com.technototes.library.command.Command;
import org.firstinspires.ftc.twenty403.subsystems.DrivebaseSubsystem;

public class ResetGyroCommand implements Command {

    public DrivebaseSubsystem subsystem;

    public ResetGyroCommand(DrivebaseSubsystem s) {
        subsystem = s;
    }

    @Override
    public void execute() {
        subsystem.setExternalHeading(0);
    }
}
