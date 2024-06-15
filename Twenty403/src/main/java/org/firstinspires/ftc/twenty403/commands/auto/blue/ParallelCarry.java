package org.firstinspires.ftc.twenty403.commands.auto.blue;

import com.technototes.library.command.ParallelCommandGroup;
import com.technototes.path.command.TrajectorySequenceCommand;
import org.firstinspires.ftc.twenty403.AutoConstants;
import org.firstinspires.ftc.twenty403.Robot;
import org.firstinspires.ftc.twenty403.commands.arm.ArmNeutralCommand;

public class ParallelCarry extends ParallelCommandGroup {

    public ParallelCarry(Robot r) {
        super(
            new TrajectorySequenceCommand(
                r.drivebaseSubsystem,
                AutoConstants.WingBlue.PIXEL_INTAKE_MID_PARK_CENTER
            ),
            new ArmNeutralCommand(r.armSubsystem)
        );
    }
}
