package org.firstinspires.ftc.twenty403.commands.auto.blue;

import com.technototes.library.command.SequentialCommandGroup;
import com.technototes.library.command.WaitCommand;
import org.firstinspires.ftc.twenty403.commands.EZCmd;
import org.firstinspires.ftc.twenty403.subsystems.ArmSubsystem;

public class PixelScoring extends SequentialCommandGroup {

    public PixelScoring(ArmSubsystem s) {
        super(
            EZCmd.Shoulder.FirstLine(s),
            EZCmd.Wrist.FirstLine(s),
            new WaitCommand(1),
            EZCmd.Intake.Spit(s),
            new WaitCommand(2),
            EZCmd.Arm.Neutral(s)
        );
    }
}
