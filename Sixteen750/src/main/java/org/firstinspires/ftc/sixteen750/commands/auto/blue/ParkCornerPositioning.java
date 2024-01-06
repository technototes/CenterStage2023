package org.firstinspires.ftc.sixteen750.commands.auto.blue;

import com.technototes.library.command.SequentialCommandGroup;
import com.technototes.path.command.TrajectorySequenceCommand;

import org.firstinspires.ftc.sixteen750.AutoConstants;
import org.firstinspires.ftc.sixteen750.Robot;

public class ParkCornerPositioning extends SequentialCommandGroup {

    public ParkCornerPositioning(Robot r) {
        super(
            new TrajectorySequenceCommand(
                r.drivebaseSubsystem,
                AutoConstants.StageBlue.MID_PARK_CENTER_TO_LEFT_CLEAR
            )
        );
    }
}
