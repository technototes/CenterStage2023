package org.firstinspires.ftc.sixteen750.commands;

import com.technototes.library.command.Command;
import org.firstinspires.ftc.sixteen750.subsystems.VisionSubsystem;

public class VisionCommand implements Command {

    public VisionSubsystem subsystem;

    public VisionCommand(VisionSubsystem s) {
        subsystem = s;
        addControlledSubsystems(subsystem);
    }

    @Override
    public void initialize() {
        subsystem.startVisionPipeline();
    }

    @Override
    public void execute() {}

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean cancel) {
        subsystem.stopVisionPipeline();
    }
}
