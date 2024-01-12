package org.firstinspires.ftc.sixteen750.commands.placement;

import com.technototes.library.command.SequentialCommandGroup;
import com.technototes.library.command.WaitCommand;

import org.firstinspires.ftc.sixteen750.subsystems.PlacementSubsystem;

public class LiftIntakeSequential extends SequentialCommandGroup {

    public LiftIntakeSequential(PlacementSubsystem s) {
        super(new ServoHold(s), new LiftIntakeCommand(s), new WaitCommand(0.3), new ServoIntakes(s));
    }
}
