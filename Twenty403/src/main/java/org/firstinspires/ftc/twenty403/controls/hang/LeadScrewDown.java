package org.firstinspires.ftc.twenty403.controls.hang;

import com.technototes.library.command.Command;
import org.firstinspires.ftc.twenty403.subsystems.HangSubsystem;

public class LeadScrewDown implements Command {

    private HangSubsystem leadscrewDown;

    public LeadScrewDown(HangSubsystem j) {
        leadscrewDown = j;
        addRequirements(j);
    }

    @Override
    public void execute() {
        leadscrewDown.leadScrewRetract();
    }
}
