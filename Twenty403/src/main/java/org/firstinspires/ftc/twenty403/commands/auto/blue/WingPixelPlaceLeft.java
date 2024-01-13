package org.firstinspires.ftc.twenty403.commands.auto.blue;

import com.technototes.library.command.SequentialCommandGroup;
import com.technototes.library.command.WaitCommand;
import com.technototes.path.command.TrajectorySequenceCommand;
import org.firstinspires.ftc.twenty403.AutoConstants.WingBlue;
import org.firstinspires.ftc.twenty403.Robot;
import org.firstinspires.ftc.twenty403.commands.auto.SafetyStartCommand;
import org.firstinspires.ftc.twenty403.commands.auto.SafetyStopCommand;
import org.firstinspires.ftc.twenty403.commands.auto.red.PixelScoring;
import org.firstinspires.ftc.twenty403.commands.auto.red.WingPixelPlaceMiddle;
import org.firstinspires.ftc.twenty403.subsystems.ArmSubsystem;

public class WingPixelPlaceLeft extends SequentialCommandGroup {

    public WingPixelPlaceLeft(Robot r) {
        super(
        new SafetyStartCommand(r.safetySubsystem),
        new TrajectorySequenceCommand(r.drivebaseSubsystem, WingBlue.START_TO_MID_CLEAR),
        new TrajectorySequenceCommand(r.drivebaseSubsystem, WingBlue.MID_CLEAR_TO_LEFT_SPIKE),
        new TrajectorySequenceCommand(r.drivebaseSubsystem, WingBlue.LEFT_SPIKE_TO_MID_CLEAR),
        new TrajectorySequenceCommand(r.drivebaseSubsystem, WingBlue.MID_CLEAR_TO_CLEAR),
        new SafetyStopCommand(r.safetySubsystem),
        new WaitCommand(5),
        new SafetyStopCommand(r.safetySubsystem),
        new TrajectorySequenceCommand(r.drivebaseSubsystem, WingBlue.ClEAR_TO_LEFT_CLEAR),
        new TrajectorySequenceCommand(r.drivebaseSubsystem, WingBlue.LEFT_CLEAR_TO_PLACE_LEFT),
        //place command
        new SafetyStopCommand(r.safetySubsystem),
        new PixelScoring(r.armSubsystem),
        new SafetyStartCommand(r.safetySubsystem),
        new TrajectorySequenceCommand(r.drivebaseSubsystem, WingBlue.PLACE_LEFT_TO_LEFT_CLEAR)
        );
    }
}
