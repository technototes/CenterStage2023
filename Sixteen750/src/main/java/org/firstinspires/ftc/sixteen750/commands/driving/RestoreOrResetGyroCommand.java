package org.firstinspires.ftc.sixteen750.commands.driving;

import com.technototes.library.command.Command;
import org.firstinspires.ftc.sixteen750.helpers.HeadingHelper;
import org.firstinspires.ftc.sixteen750.subsystems.DrivebaseSubsystem;

public class RestoreOrResetGyroCommand implements Command {

    public DrivebaseSubsystem subsystem;

    public RestoreOrResetGyroCommand(DrivebaseSubsystem s) {
        subsystem = s;
    }

    @Override
    public void execute() {
        double heading = 0;
        if (HeadingHelper.validHeading()) {
            heading = HeadingHelper.getSavedHeading();
        }
        subsystem.setExternalHeading(heading);
    }
}
