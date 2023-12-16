package org.firstinspires.ftc.twenty403.commands.auto.red;

import com.technototes.library.command.SequentialCommandGroup;
import com.technototes.path.command.TrajectorySequenceCommand;

import org.firstinspires.ftc.twenty403.AutoConstants;
import org.firstinspires.ftc.twenty403.Robot;

public class ParkCornerPositioning extends SequentialCommandGroup {
    public ParkCornerPositioning(Robot r){
        super(
                        new TrajectorySequenceCommand(
                                r.drivebaseSubsystem,
                                AutoConstants.StageRed.MID_PARK_CENTER_TO_RIGHT_CLEAR
                        )
                );
    }
}
