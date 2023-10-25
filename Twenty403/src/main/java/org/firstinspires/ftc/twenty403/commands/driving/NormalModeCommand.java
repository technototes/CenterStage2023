package org.firstinspires.ftc.twenty403.commands.driving;

import com.technototes.library.command.Command;
import org.firstinspires.ftc.twenty403.subsystems.DrivebaseSubsystem;

public class NormalModeCommand implements Command {

    public DrivebaseSubsystem subsystem;

    public NormalModeCommand(DrivebaseSubsystem s) {
        subsystem = s;
    }

    @Override
    public void execute() {
        subsystem.setNormalMode();
    }
}
