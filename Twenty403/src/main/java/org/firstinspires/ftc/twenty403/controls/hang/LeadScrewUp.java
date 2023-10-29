package org.firstinspires.ftc.twenty403.controls.hang;

import com.technototes.library.command.Command;

import org.firstinspires.ftc.twenty403.subsystems.HangSubsystem;

public class LeadScrewUp implements Command {

    private HangSubsystem leadscrewUp;

    public LeadScrewUp(HangSubsystem s) {
        leadscrewUp = s;
        addRequirements(s);
    }

    @Override
    public void execute() {
        leadscrewUp.leadScrewExtended();
    }
}
