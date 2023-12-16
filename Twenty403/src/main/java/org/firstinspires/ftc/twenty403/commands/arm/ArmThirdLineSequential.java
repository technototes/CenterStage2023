package org.firstinspires.ftc.twenty403.commands.arm;

import com.technototes.library.command.SequentialCommandGroup;

import org.firstinspires.ftc.twenty403.subsystems.ArmSubsystem;

public class ArmThirdLineSequential extends SequentialCommandGroup {

    public ArmThirdLineSequential(ArmSubsystem s) {
        super(new ShoulderThirdLineScoring(s), new WristThirdLineScoring(s));
    }
}
