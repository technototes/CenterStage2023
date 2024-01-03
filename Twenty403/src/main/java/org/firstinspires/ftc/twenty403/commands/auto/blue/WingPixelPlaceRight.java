package org.firstinspires.ftc.twenty403.commands.auto.blue;

import com.technototes.library.command.SequentialCommandGroup;
import com.technototes.path.command.TrajectorySequenceCommand;

import org.firstinspires.ftc.twenty403.AutoConstants.WingBlue;
import org.firstinspires.ftc.twenty403.Robot;
import org.firstinspires.ftc.twenty403.commands.auto.red.PixelScoring;
import org.firstinspires.ftc.twenty403.subsystems.ArmSubsystem;

public class WingPixelPlaceRight extends SequentialCommandGroup {

    public WingPixelPlaceRight(Robot r) {
        super(
        new TrajectorySequenceCommand(r.drivebaseSubsystem, WingBlue.START_TO_RIGHT_SPIKE),
        new TrajectorySequenceCommand(r.drivebaseSubsystem, WingBlue.RIGHT_SPIKE_TO_CLEAR),
        new TrajectorySequenceCommand(r.drivebaseSubsystem, WingBlue.ClEAR_TO_LEFT_CLEAR),
        new TrajectorySequenceCommand(r.drivebaseSubsystem, WingBlue.LEFT_CLEAR_TO_PLACE_RIGHT),
        //place command
        new PixelScoring(new ArmSubsystem()),
        new TrajectorySequenceCommand(r.drivebaseSubsystem, WingBlue.PLACE_RIGHT_TO_LEFT_CLEAR)
        );
    }
}
