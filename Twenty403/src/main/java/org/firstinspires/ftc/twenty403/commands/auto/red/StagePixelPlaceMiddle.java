package org.firstinspires.ftc.twenty403.commands.auto.red;

import com.technototes.library.command.SequentialCommandGroup;
import com.technototes.path.command.TrajectorySequenceCommand;
import org.firstinspires.ftc.twenty403.AutoConstants;
import org.firstinspires.ftc.twenty403.Robot;
import org.firstinspires.ftc.twenty403.subsystems.ArmSubsystem;

public class StagePixelPlaceMiddle extends SequentialCommandGroup {

    public StagePixelPlaceMiddle(Robot r) {
        super(
        new TrajectorySequenceCommand(r.drivebaseSubsystem, AutoConstants.StageRed.START_TO_MIDDLE_SPIKE),
        new TrajectorySequenceCommand(r.drivebaseSubsystem,AutoConstants.StageRed.MIDDLE_SPIKE_TO_CLEAR),
        new TrajectorySequenceCommand(r.drivebaseSubsystem, AutoConstants.StageRed.ClEAR_TO_RIGHT_CLEAR),
        new TrajectorySequenceCommand(r.drivebaseSubsystem, AutoConstants.StageRed.RIGHT_CLEAR_TO_PLACE_MIDDLE),
        //place command
        new PixelScoring(r.armSubsystem),
        new TrajectorySequenceCommand(r.drivebaseSubsystem, AutoConstants.StageRed.PLACE_MIDDLE_TO_MID_PARK_CENTER)
        );
    }
}
