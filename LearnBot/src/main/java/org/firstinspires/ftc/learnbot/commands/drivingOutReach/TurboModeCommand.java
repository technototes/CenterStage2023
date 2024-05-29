package org.firstinspires.ftc.learnbot.commands.drivingOutReach;

import com.technototes.library.command.Command;
import org.firstinspires.ftc.learnbot.subsystems.DrivebaseSubsystem;

public class TurboModeCommand implements Command {

    public DrivebaseSubsystem subsystem;

    public TurboModeCommand(DrivebaseSubsystem s) {
        subsystem = s;
    }

    @Override
    public void execute() {
        subsystem.setTurboMode();
    }
}
