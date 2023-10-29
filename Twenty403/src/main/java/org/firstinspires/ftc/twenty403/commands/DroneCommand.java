package org.firstinspires.ftc.twenty403.commands;

import com.technototes.library.command.Command;

import org.firstinspires.ftc.twenty403.subsystems.DroneSubsystem;

public class DroneCommand implements Command {
    private DroneSubsystem subsystem;

    public DroneCommand(DroneSubsystem s) {
        this.subsystem = s;
        addRequirements(this.subsystem); // Keeps robot from breaking
    }

    @Override
    public void execute() {
        this.subsystem.launch();
    }
}
