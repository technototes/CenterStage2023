package org.firstinspires.ftc.twenty403.commands.auto.blue;

import com.technototes.library.command.SequentialCommandGroup;
import com.technototes.library.command.WaitCommand;
import com.technototes.path.command.TrajectorySequenceCommand;
import org.firstinspires.ftc.twenty403.AutoConstants;
import org.firstinspires.ftc.twenty403.Robot;
import org.firstinspires.ftc.twenty403.commands.auto.red.PixelScoring;
import org.firstinspires.ftc.twenty403.subsystems.ArmSubsystem;

public class StagePixelPlaceMiddle extends SequentialCommandGroup {

    public StagePixelPlaceMiddle(Robot r) {
        super(
        new TrajectorySequenceCommand(r.drivebaseSubsystem, AutoConstants.StageBlue.START_TO_MIDDLE_SPIKE),
        new TrajectorySequenceCommand(r.drivebaseSubsystem, AutoConstants.StageBlue.MIDDLE_SPIKE_TO_CLEAR),
        new TrajectorySequenceCommand(r.drivebaseSubsystem, AutoConstants.StageBlue.ClEAR_TO_LEFT_CLEAR),
        new TrajectorySequenceCommand(r.drivebaseSubsystem, AutoConstants.StageBlue.LEFT_CLEAR_TO_PLACE_MIDDLE),
        //place command
        new PixelScoring(r.armSubsystem),
        new WaitCommand(0.5),
        new TrajectorySequenceCommand(r.drivebaseSubsystem, AutoConstants.StageBlue.PLACE_MIDDLE_TO_MID_PARK_CENTER)
        );
    }
}
