package org.firstinspires.ftc.protobot.commands;

import com.technototes.library.command.Command;
import org.firstinspires.ftc.protobot.subsystems.DrivebaseSubsystem;

public class TurboModeCommand implements Command {

    public DrivebaseSubsystem subsystem;

    public TurboModeCommand(DrivebaseSubsystem s) {
        subsystem = s;
    }

    @Override
    public void execute() {
        subsystem.Turbo = true;
    }
}
