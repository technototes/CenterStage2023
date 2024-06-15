package org.firstinspires.ftc.sixteen750.commands.auto;

import com.acmerobotics.dashboard.config.Config;
import com.technototes.library.command.SequentialCommandGroup;
import com.technototes.library.command.WaitCommand;
import org.firstinspires.ftc.sixteen750.commands.placement.LiftIntakeSequential;
import org.firstinspires.ftc.sixteen750.commands.placement.LiftLowSequential;
import org.firstinspires.ftc.sixteen750.commands.placement.LiftMediumSequential;
import org.firstinspires.ftc.sixteen750.commands.placement.ServoOutputs;
import org.firstinspires.ftc.sixteen750.subsystems.PlacementSubsystem;

@Config
public class PixelScoring extends SequentialCommandGroup {

    public static double WAIT1 = 2;
    public static double WAIT2 = 2;

    public PixelScoring(PlacementSubsystem s) {
        super(
            new LiftLowSequential(s),
            new WaitCommand(WAIT1),
            new ServoOutputs(s),
            new WaitCommand(WAIT2),
            new LiftIntakeSequential(s)
        );
    }
}
