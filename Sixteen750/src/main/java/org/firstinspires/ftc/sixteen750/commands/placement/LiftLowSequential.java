package org.firstinspires.ftc.sixteen750.commands.placement;

import com.acmerobotics.dashboard.config.Config;
import com.technototes.library.command.SequentialCommandGroup;
import com.technototes.library.command.WaitCommand;

import org.firstinspires.ftc.sixteen750.subsystems.PlacementSubsystem;
@Config
public class LiftLowSequential extends SequentialCommandGroup {
public static double WAIT1 = 0.5;
    public LiftLowSequential(PlacementSubsystem s) {
        super(new ScoreServoFlatCommand(s),new WaitCommand(WAIT1), new ServoHold(s), new WaitCommand(0.5), new LiftLowCommand(s));
    }
}
