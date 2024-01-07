package org.firstinspires.ftc.twenty403.commands.auto;

import com.technototes.library.command.Command;

import org.firstinspires.ftc.twenty403.subsystems.SafetySubsystem;

public class SafetyTestOdoRCommand implements Command {
        private SafetySubsystem subsystem;

        @Override
        public void execute() {
            subsystem.simulateFail(SafetySubsystem.FailedPart.ODOR);
        }

        public SafetyTestOdoRCommand(SafetySubsystem d) {
            subsystem = d;
        }


}
