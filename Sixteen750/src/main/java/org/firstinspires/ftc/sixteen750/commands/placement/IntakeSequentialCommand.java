package org.firstinspires.ftc.sixteen750.commands.placement;

import com.technototes.library.command.SequentialCommandGroup;

import org.firstinspires.ftc.sixteen750.subsystems.PlacementSubsystem;

public class IntakeSequentialCommand extends SequentialCommandGroup {

    public IntakeSequentialCommand(PlacementSubsystem s) {
        super(new ArmServoInputCommand(s), new ScoreServoInputCommand(s));
    }
}
