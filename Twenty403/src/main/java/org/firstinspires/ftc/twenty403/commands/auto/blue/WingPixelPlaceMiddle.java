package org.firstinspires.ftc.twenty403.commands.auto.blue;

import com.technototes.library.command.SequentialCommandGroup;
import com.technototes.library.command.WaitCommand;
import com.technototes.path.command.TrajectorySequenceCommand;
import org.firstinspires.ftc.twenty403.AutoConstants.WingBlue;
import org.firstinspires.ftc.twenty403.Robot;
import org.firstinspires.ftc.twenty403.commands.auto.SafetyStartCommand;
import org.firstinspires.ftc.twenty403.commands.auto.SafetyStopCommand;
import org.firstinspires.ftc.twenty403.commands.auto.red.PixelScoring;
import org.firstinspires.ftc.twenty403.subsystems.ArmSubsystem;

public class WingPixelPlaceMiddle extends SequentialCommandGroup {

    public WingPixelPlaceMiddle(Robot r) {
        super(
        new SafetyStartCommand(r.safetySubsystem),
        new TrajectorySequenceCommand(r.drivebaseSubsystem, WingBlue.START_TO_MIDDLE_SPIKE),
        new TrajectorySequenceCommand(r.drivebaseSubsystem, WingBlue.MIDDLE_SPIKE_TO_CLEAR),
        new SafetyStopCommand(r.safetySubsystem),
        new WaitCommand(5),
        new SafetyStartCommand(r.safetySubsystem),
        new TrajectorySequenceCommand(r.drivebaseSubsystem, WingBlue.ClEAR_TO_LEFT_CLEAR),
        new TrajectorySequenceCommand(r.drivebaseSubsystem, WingBlue.LEFT_CLEAR_TO_PLACE_MIDDLE),
        //place command
        new SafetyStopCommand(r.safetySubsystem),
        new PixelScoring(r.armSubsystem), // og
        new SafetyStartCommand(r.safetySubsystem),
        //new TrajectorySequenceCommand(r.drivebaseSubsystem, WingBlue.PLACE_MIDDLE_TO_MID_PARK_CENTER), // new
        //new WingPixelIntake(r), // new
        //new TrajectorySequenceCommand(r.drivebaseSubsystem, WingBlue.MID_PARK_CENTER_TO_LEFT_CLEAR) // new
        new TrajectorySequenceCommand(r.drivebaseSubsystem, WingBlue.PLACE_MIDDLE_TO_LEFT_CLEAR)// og
        );
    }
}
