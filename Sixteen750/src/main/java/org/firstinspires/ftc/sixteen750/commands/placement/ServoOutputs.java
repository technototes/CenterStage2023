package org.firstinspires.ftc.sixteen750.commands.placement;

import com.technototes.library.command.SequentialCommandGroup;

import org.firstinspires.ftc.sixteen750.subsystems.PlacementSubsystem;

public class ServoOutputs extends SequentialCommandGroup {
    public ServoOutputs (PlacementSubsystem s){
        super(new ArmServoOutputCommand(s), new ScoreServoOutputCommand(s));
    }
}
