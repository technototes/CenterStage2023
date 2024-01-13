package org.firstinspires.ftc.twenty403.commands.auto.blue;

import com.technototes.library.command.SequentialCommandGroup;
import com.technototes.library.command.WaitCommand;

import org.firstinspires.ftc.twenty403.commands.arm.ArmNeutralCommand;
import org.firstinspires.ftc.twenty403.commands.arm.IntakeSpitCommand;
import org.firstinspires.ftc.twenty403.commands.arm.ShoulderFirstLineScoring;
import org.firstinspires.ftc.twenty403.commands.arm.ShoulderNeutralCommand;
import org.firstinspires.ftc.twenty403.commands.arm.WristFirstLineScoring;
import org.firstinspires.ftc.twenty403.subsystems.ArmSubsystem;

public class PixelScoring extends SequentialCommandGroup {
        public PixelScoring(ArmSubsystem s) {
            super(
                    new ShoulderFirstLineScoring(s),
                    new WristFirstLineScoring(s),
                    new WaitCommand(1),
                    new IntakeSpitCommand(s),
                    new WaitCommand(2),
                    new ArmNeutralCommand(s)
            );
        }
    }
