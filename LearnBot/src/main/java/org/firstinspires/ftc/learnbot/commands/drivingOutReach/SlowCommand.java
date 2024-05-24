package org.firstinspires.ftc.learnbot.commands.drivingOutReach;

import com.technototes.library.command.Command;

import org.firstinspires.ftc.sixteen750.subsystems.DrivebaseSubsystem;

public class SlowCommand implements Command {

    public DrivebaseSubsystem subsystem;

    public SlowCommand(DrivebaseSubsystem s) {
        subsystem = s;
    }

    @Override
    public void execute() {
        subsystem.slow();
    }
}
