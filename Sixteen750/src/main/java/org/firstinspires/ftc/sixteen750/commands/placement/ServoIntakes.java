package org.firstinspires.ftc.sixteen750.commands.placement;

import com.technototes.library.command.SequentialCommandGroup;

import org.firstinspires.ftc.sixteen750.subsystems.PlacementSubsystem;

public class ServoIntakes extends SequentialCommandGroup {
    public ServoIntakes (PlacementSubsystem s){
        super(new ArmServoInputCommand(s), new ScoreServoInputCommand(s));
    }
}
