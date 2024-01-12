package org.firstinspires.ftc.twenty403.commands.auto.blue;

import com.technototes.library.command.ParallelCommandGroup;
import com.technototes.path.command.TrajectorySequenceCommand;

import org.firstinspires.ftc.twenty403.AutoConstants;
import org.firstinspires.ftc.twenty403.Robot;
import org.firstinspires.ftc.twenty403.commands.arm.ArmIntakeCommand;

public class ParallelIntake extends ParallelCommandGroup {

    public ParallelIntake(Robot r) {
        super(new TrajectorySequenceCommand(r.drivebaseSubsystem, AutoConstants.WingBlue.MID_PARK_CENTER_TO_PIXEL_INTAKE),
              new ArmIntakeCommand(r.armSubsystem)
        );
    }
}
