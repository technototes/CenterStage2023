package org.firstinspires.ftc.sixteen750.commands.auto.red;

import com.technototes.library.command.SequentialCommandGroup;
import com.technototes.path.command.TrajectorySequenceCommand;

import org.firstinspires.ftc.sixteen750.AutoConstants;
import org.firstinspires.ftc.sixteen750.Robot;

public class ParkCenterPositioning extends SequentialCommandGroup {

    public ParkCenterPositioning(Robot r) {
        super(
            new TrajectorySequenceCommand(
                r.drivebaseSubsystem,
                AutoConstants.WingRed.RIGHT_CLEAR_TO_MID_PARK_CENTER
            )
        );
    }
}
