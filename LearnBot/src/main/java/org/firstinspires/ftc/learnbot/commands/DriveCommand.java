package org.firstinspires.ftc.learnbot.commands;

import com.technototes.library.command.Command;
import com.technototes.library.control.Stick;
import com.technototes.library.logger.Loggable;
import java.util.function.DoubleSupplier;
import org.firstinspires.ftc.learnbot.subsystems.DrivebaseSubsystem;

public class DriveCommand implements Command, Loggable {

    public DrivebaseSubsystem subsystem;
    public DoubleSupplier x, y, r;

    public DriveCommand(DrivebaseSubsystem sub, Stick stick1, Stick stick2) {
        addRequirements(sub);
        subsystem = sub;
        x = stick1.getXSupplier();
        y = stick1.getYSupplier();
        r = stick2.getXSupplier();
    }

    @Override
    public void execute() {
        subsystem.joystickDrive(x.getAsDouble(), y.getAsDouble(), r.getAsDouble());
    }
}
