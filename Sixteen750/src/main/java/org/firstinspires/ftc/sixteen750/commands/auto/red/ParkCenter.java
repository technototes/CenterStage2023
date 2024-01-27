package org.firstinspires.ftc.sixteen750.commands.auto.red;

import com.technototes.library.command.SequentialCommandGroup;
import com.technototes.path.command.TrajectorySequenceCommand;
import org.firstinspires.ftc.sixteen750.AutoConstants;
import org.firstinspires.ftc.sixteen750.Robot;
import org.firstinspires.ftc.sixteen750.helpers.StartingPosition;

public class ParkCenter extends SequentialCommandGroup {

    public ParkCenter(Robot r, StartingPosition position) {
        super(
            new TrajectorySequenceCommand(
                r.drivebase,
                (position == StartingPosition.Wing)
                    ? AutoConstants.WingRed.MID_PARK_CENTER_TO_PARK_CENTER
                    : AutoConstants.StageRed.MID_PARK_CENTER_TO_PARK_CENTER
            )
        );
    }
}
