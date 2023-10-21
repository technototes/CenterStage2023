package org.firstinspires.ftc.sixteen750.commands.hang;

import com.technototes.library.command.Command;
import org.firstinspires.ftc.sixteen750.subsystems.HangSubsystem;
import org.firstinspires.ftc.sixteen750.subsystems.IntakeSubsystem;

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
