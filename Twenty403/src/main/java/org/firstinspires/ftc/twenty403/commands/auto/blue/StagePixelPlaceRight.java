package org.firstinspires.ftc.twenty403.commands.auto.blue;

import com.technototes.library.command.SequentialCommandGroup;
import com.technototes.path.command.TrajectorySequenceCommand;
import org.firstinspires.ftc.twenty403.AutoConstants;
import org.firstinspires.ftc.twenty403.Robot;
import org.firstinspires.ftc.twenty403.commands.auto.SafetyStartCommand;
import org.firstinspires.ftc.twenty403.commands.auto.SafetyStopCommand;
import org.firstinspires.ftc.twenty403.commands.auto.red.PixelScoring;
import org.firstinspires.ftc.twenty403.subsystems.ArmSubsystem;

public class StagePixelPlaceRight extends SequentialCommandGroup {

    public StagePixelPlaceRight(Robot r) {
        super(
        new SafetyStartCommand(r.safetySubsystem),
        new TrajectorySequenceCommand(r.drivebaseSubsystem, AutoConstants.StageBlue.START_TO_MID_CLEAR),
        new TrajectorySequenceCommand(r.drivebaseSubsystem, AutoConstants.StageBlue.MID_CLEAR_TO_RIGHT_SPIKE),
        new TrajectorySequenceCommand(r.drivebaseSubsystem, AutoConstants.StageBlue.RIGHT_SPIKE_TO_MID_CLEAR),
        new TrajectorySequenceCommand(r.drivebaseSubsystem, AutoConstants.StageBlue.MID_CLEAR_TO_CLEAR),
        new TrajectorySequenceCommand(r.drivebaseSubsystem, AutoConstants.StageBlue.CLEAR_TO_LEFT_CLEAR),
        new TrajectorySequenceCommand(r.drivebaseSubsystem, AutoConstants.StageBlue.LEFT_CLEAR_TO_PLACE_RIGHT),
        //place command
        new SafetyStopCommand(r.safetySubsystem),
        new PixelScoring(r.armSubsystem),
        new SafetyStartCommand(r.safetySubsystem),
        new TrajectorySequenceCommand(r.drivebaseSubsystem, AutoConstants.StageBlue.PLACE_RIGHT_TO_MID_PARK_CENTER)
        );
    }
}
