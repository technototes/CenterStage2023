package org.firstinspires.ftc.twenty403.commands.arm;

import com.technototes.library.command.Command;
import com.technototes.library.command.SequentialCommandGroup;
import com.technototes.library.command.WaitCommand;
import org.firstinspires.ftc.twenty403.subsystems.ArmSubsystem;

public class ArmIntakeSequential extends SequentialCommandGroup {

    public ArmIntakeSequential(ArmSubsystem s) {
        super(new ShoulderIntakeCommand(s), new ElbowIntakeCommand(s));
    }
}
