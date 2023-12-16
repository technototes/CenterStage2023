package org.firstinspires.ftc.twenty403.commands.arm;

import com.technototes.library.command.SequentialCommandGroup;

import org.firstinspires.ftc.twenty403.subsystems.ArmSubsystem;

public class ArmFirstLineSequential extends SequentialCommandGroup {

    public ArmFirstLineSequential(ArmSubsystem s) {
        super(new ShoulderFirstLineScoring(s), new WristFirstLineScoring(s));
    }
}
