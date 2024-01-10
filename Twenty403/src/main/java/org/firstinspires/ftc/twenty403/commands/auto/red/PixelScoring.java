package org.firstinspires.ftc.twenty403.commands.auto.red;

import com.technototes.library.command.SequentialCommandGroup;
import com.technototes.library.command.WaitCommand;

import org.firstinspires.ftc.twenty403.commands.arm.ArmNeutralCommand;
import org.firstinspires.ftc.twenty403.commands.arm.IntakeSpitCommand;
import org.firstinspires.ftc.twenty403.commands.arm.ShoulderFirstLineScoring;
import org.firstinspires.ftc.twenty403.commands.arm.ShoulderNeutralCommand;
import org.firstinspires.ftc.twenty403.commands.arm.ShoulderSecondLineScoring;
import org.firstinspires.ftc.twenty403.commands.arm.WristFirstLineScoring;
import org.firstinspires.ftc.twenty403.commands.arm.WristSecondLineScoring;
import org.firstinspires.ftc.twenty403.subsystems.ArmSubsystem;

public class PixelScoring extends SequentialCommandGroup {
        public PixelScoring(ArmSubsystem s) {
            super(
                    new ShoulderSecondLineScoring(s),
                    new WristSecondLineScoring(s),
                    new WaitCommand(1),
                    new IntakeSpitCommand(s),
                    new WaitCommand(2),
                    new ArmNeutralCommand(s)
            );
        }
    }
