package org.firstinspires.ftc.twenty403.commands.arm;

import com.technototes.library.command.Command;
import com.technototes.library.command.SequentialCommandGroup;
import com.technototes.library.command.WaitCommand;
import com.technototes.path.command.TrajectorySequenceCommand;

import org.firstinspires.ftc.twenty403.subsystems.ArmSubsystem;

public class ArmSecondLineSequential extends SequentialCommandGroup {

    public ArmSecondLineSequential(ArmSubsystem s) {
        super(new ShoulderSecondLineScoring(s),
                new ElbowSecondLineScoring(s)
        );
    }
}
