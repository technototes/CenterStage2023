package org.firstinspires.ftc.twenty403.commands.drone;

import com.technototes.library.command.SequentialCommandGroup;
import com.technototes.library.command.WaitCommand;
import org.firstinspires.ftc.twenty403.subsystems.DroneSubsystem;

public class DroneSequential extends SequentialCommandGroup {

    public DroneSequential(DroneSubsystem s) {
        super(new DroneCommand(s), new WaitCommand(0.5), new DroneStopCommand(s));
    }
}
