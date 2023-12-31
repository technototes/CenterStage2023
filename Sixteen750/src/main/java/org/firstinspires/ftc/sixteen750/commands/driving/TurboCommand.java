package org.firstinspires.ftc.sixteen750.commands.driving;

import com.technototes.library.command.Command;
import org.firstinspires.ftc.sixteen750.subsystems.DrivebaseSubsystem;

public class TurboCommand implements Command {

    public DrivebaseSubsystem subsystem;

    public TurboCommand(DrivebaseSubsystem s) {
        subsystem = s;
    }

    @Override
    public void execute() {
        subsystem.fast();
    }
}
