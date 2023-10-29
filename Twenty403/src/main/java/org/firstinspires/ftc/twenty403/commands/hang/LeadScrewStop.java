package org.firstinspires.ftc.twenty403.commands.hang;

import com.technototes.library.command.Command;
import org.firstinspires.ftc.twenty403.subsystems.HangSubsystem;

public class LeadScrewStop implements Command {

    private HangSubsystem leadscrew;

    public LeadScrewStop(HangSubsystem j) {
        leadscrew = j;
        addRequirements(j);
    }

    @Override
    public void execute() {
        leadscrew.leadScrewStop();
    }
}
