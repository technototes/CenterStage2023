package org.firstinspires.ftc.sixteen750.commands.auto.red;

import com.technototes.library.command.SequentialCommandGroup;
import com.technototes.path.command.TrajectorySequenceCommand;
import org.firstinspires.ftc.sixteen750.AutoConstants;
import org.firstinspires.ftc.sixteen750.Robot;

public class ParkCornerPositioning extends SequentialCommandGroup {

    public ParkCornerPositioning(Robot r) {
        super(
            new TrajectorySequenceCommand(
                r.drivebase,
                AutoConstants.StageRed.MID_PARK_CENTER_TO_RIGHT_CLEAR
            )
        );
    }
}
