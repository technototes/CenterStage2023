package org.firstinspires.ftc.twenty403.commands.auto.red;

import com.technototes.library.command.SequentialCommandGroup;
import com.technototes.path.command.TrajectorySequenceCommand;
import org.firstinspires.ftc.twenty403.AutoConstants;
import org.firstinspires.ftc.twenty403.Robot;
import org.firstinspires.ftc.twenty403.commands.EZCmd;

public class WingPixelIntake extends SequentialCommandGroup {

    public WingPixelIntake(Robot r) {
        super(
            new TrajectorySequenceCommand(
                r.drivebaseSubsystem,
                AutoConstants.WingRed.MID_PARK_CENTER_TO_PIXEL_INTAKE
            ),
            // intake
            EZCmd.Arm.Intake(r.armSubsystem),
            //neutral position
            EZCmd.Arm.Neutral(r.armSubsystem),
            //trajectories to place
            new TrajectorySequenceCommand(
                r.drivebaseSubsystem,
                AutoConstants.WingRed.PIXEL_INTAKE_MID_PARK_CENTER
            ),
            new TrajectorySequenceCommand(
                r.drivebaseSubsystem,
                AutoConstants.WingRed.MID_PARK_CENTER_TO_PLACE_MIDDLE
            ),
            // pixel scoring
            new PixelScoring(r.armSubsystem),
            new TrajectorySequenceCommand(
                r.drivebaseSubsystem,
                AutoConstants.WingRed.PLACE_MIDDLE_TO_MID_PARK_CENTER
            )
        );
    }
}
