package org.firstinspires.ftc.twenty403.commands.arm;

import com.acmerobotics.dashboard.config.Config;
import com.technototes.library.command.SequentialCommandGroup;
import com.technototes.library.command.WaitCommand;
import org.firstinspires.ftc.twenty403.subsystems.ArmSubsystem;

@Config
public class ArmNeutralSequential extends SequentialCommandGroup {

    public static double waitDuration = 0.3;

    public ArmNeutralSequential(ArmSubsystem s) {
        super(
            new ShoulderVertical(s),
            new WaitCommand(ArmNeutralSequential.waitDuration),
            new ElbowNeutralPosition(s),
            new ShoulderNeutralPosition(s)
        );
    }
}
