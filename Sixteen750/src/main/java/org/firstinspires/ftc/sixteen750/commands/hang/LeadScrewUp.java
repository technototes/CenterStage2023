package org.firstinspires.ftc.sixteen750.commands.hang;

import com.technototes.library.command.Command;
import org.firstinspires.ftc.sixteen750.subsystems.HangSubsystem;
import org.firstinspires.ftc.sixteen750.subsystems.IntakeSubsystem;

public class LeadScrewUp implements Command {

    private HangSubsystem leadscrewUp;

    public LeadScrewUp(HangSubsystem s) {
        leadscrewUp = s;
        addControlledSubsystems(s);
    }

    @Override
    public void execute() {
        leadscrewUp.leadScrewExtended();
    }
}
