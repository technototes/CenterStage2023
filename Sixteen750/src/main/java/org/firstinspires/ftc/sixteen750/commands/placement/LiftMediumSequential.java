package org.firstinspires.ftc.sixteen750.commands.placement;

import com.acmerobotics.dashboard.config.Config;
import com.technototes.library.command.SequentialCommandGroup;
import com.technototes.library.command.WaitCommand;

import org.firstinspires.ftc.sixteen750.subsystems.PlacementSubsystem;
@Config
public class LiftMediumSequential extends SequentialCommandGroup {
    public static double WAIT1 = 0.3;

    public LiftMediumSequential(PlacementSubsystem s) {
        super(new ServoHold(s), new WaitCommand(0.5),new LiftMediumCommand(s));
    }
}
