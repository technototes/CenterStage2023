package org.firstinspires.ftc.sixteen750.commands.auto;

import com.acmerobotics.dashboard.config.Config;
import com.technototes.library.command.SequentialCommandGroup;
import com.technototes.library.command.WaitCommand;

import org.firstinspires.ftc.sixteen750.commands.placement.LiftIntakeSequential;
import org.firstinspires.ftc.sixteen750.commands.placement.LiftMediumSequential;
import org.firstinspires.ftc.sixteen750.commands.placement.ServoOutputs;
import org.firstinspires.ftc.sixteen750.subsystems.PlacementSubsystem;

@Config
public class PixelScoring extends SequentialCommandGroup {
    private static double WAIT1 = 0.1;
    private static double WAIT2 = 0.5;
        public PixelScoring(PlacementSubsystem s) {
            super(new LiftMediumSequential(s),
                    new WaitCommand(WAIT1),
                    new ServoOutputs(s),
                    new WaitCommand(WAIT2),
                    new LiftIntakeSequential(s)

            );
        }
    }
