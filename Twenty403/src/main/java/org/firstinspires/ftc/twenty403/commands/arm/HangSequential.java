package org.firstinspires.ftc.twenty403.commands.arm;

import com.technototes.library.command.SequentialCommandGroup;

import org.firstinspires.ftc.twenty403.subsystems.ArmSubsystem;

public class HangSequential extends SequentialCommandGroup {

    public HangSequential(ArmSubsystem s) {
        super(new ShoulderVertical(s), new WristNeutralPosition(s), new ShoulderNeutralPosition(s));
    }
}
