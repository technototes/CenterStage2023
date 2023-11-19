package org.firstinspires.ftc.twenty403.commands.driving;

import com.technototes.library.command.Command;
import org.firstinspires.ftc.twenty403.helpers.HeadingHelper;
import org.firstinspires.ftc.twenty403.subsystems.DrivebaseSubsystem;

/**
 * This is for loading the heading from the previous (auto?) run so the
 * bot's position doesn't have to be the same as what we start auto with
 */
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
