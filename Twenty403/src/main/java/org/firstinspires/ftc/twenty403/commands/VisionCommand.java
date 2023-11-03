package org.firstinspires.ftc.twenty403.commands;

import com.technototes.library.command.Command;
import org.firstinspires.ftc.twenty403.subsystems.VisionSubsystem;

public class VisionCommand implements Command {

    public VisionSubsystem subsystem;

    public VisionCommand(VisionSubsystem s) {
        subsystem = s;
        addRequirements(subsystem);
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
