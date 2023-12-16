package org.firstinspires.ftc.twenty403.commands.arm;

import com.technototes.library.command.SequentialCommandGroup;

import org.firstinspires.ftc.twenty403.subsystems.ArmSubsystem;

public class ArmSecondLineSequential extends SequentialCommandGroup {

    public ArmSecondLineSequential(ArmSubsystem s) {
        super(new ShoulderSecondLineScoring(s), new WristSecondLineScoring(s));
    }
}
