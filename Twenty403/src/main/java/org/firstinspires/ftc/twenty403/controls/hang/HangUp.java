package org.firstinspires.ftc.twenty403.controls.hang;

import com.technototes.library.command.Command;

import org.firstinspires.ftc.twenty403.subsystems.HangSubsystem;

public class HangUp implements Command {

    private HangSubsystem hang;

    public HangUp(HangSubsystem h) {
        hang = h;
        addRequirements(h);
    }

    @Override
    public void execute() {
        hang.hingeUp();
    }
}
