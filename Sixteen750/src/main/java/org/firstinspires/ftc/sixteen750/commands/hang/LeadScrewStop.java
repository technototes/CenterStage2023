package org.firstinspires.ftc.sixteen750.commands.hang;

import com.technototes.library.command.Command;
import org.firstinspires.ftc.sixteen750.subsystems.HangSubsystem;

public class LeadScrewStop implements Command {

    private HangSubsystem leadscrewDown;

    public LeadScrewStop(HangSubsystem j) {
        leadscrewDown = j;
        addControlledSubsystems(j);
    }

    @Override
    public void execute() {
        leadscrewDown.leadScrewStop();
    }
}
