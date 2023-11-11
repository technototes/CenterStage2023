package org.firstinspires.ftc.sixteen750.commands.driving;

import com.technototes.library.command.Command;

import org.firstinspires.ftc.sixteen750.subsystems.DrivebaseSubsystem;

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
