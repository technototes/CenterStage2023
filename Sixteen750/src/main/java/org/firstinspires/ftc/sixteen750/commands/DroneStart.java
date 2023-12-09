package org.firstinspires.ftc.sixteen750.commands;

import com.technototes.library.command.Command;

import org.firstinspires.ftc.sixteen750.subsystems.DroneSubsystem;

public class DroneStart implements Command {

    private DroneSubsystem DroneStart;
    public DroneStart(DroneSubsystem j) {
        DroneStart = j;
        addRequirements(j);
    }

    @Override
    public void execute() {
        DroneStart.Start();
    }

}
