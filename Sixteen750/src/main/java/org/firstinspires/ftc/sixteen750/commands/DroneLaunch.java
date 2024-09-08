package org.firstinspires.ftc.sixteen750.commands;

import com.technototes.library.command.Command;
import org.firstinspires.ftc.sixteen750.subsystems.DroneSubsystem;

public class DroneLaunch implements Command {

    private DroneSubsystem DroneLaunch;

    public DroneLaunch(DroneSubsystem j) {
        DroneLaunch = j;
        addRequirements(j);
    }

    @Override
    public void execute() {
        DroneLaunch.Launch();
    }
}
