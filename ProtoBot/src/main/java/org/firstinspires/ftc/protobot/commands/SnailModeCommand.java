package org.firstinspires.ftc.protobot.commands;

import com.technototes.library.command.Command;
import org.firstinspires.ftc.protobot.subsystems.DrivebaseSubsystem;

public class SnailModeCommand implements Command {

    public DrivebaseSubsystem subsystem;

    public SnailModeCommand(DrivebaseSubsystem s) {
        subsystem = s;
    }

    @Override
    public void execute() {
        subsystem.Snail = true;
    }
}
