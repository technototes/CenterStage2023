package org.firstinspires.ftc.learnbot.commands.drivingOutReach;

import com.technototes.library.command.Command;

import org.firstinspires.ftc.learnbot.subsystems.DrivebaseSubsystem;

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
