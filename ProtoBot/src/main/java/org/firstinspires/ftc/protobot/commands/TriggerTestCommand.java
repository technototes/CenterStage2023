package org.firstinspires.ftc.protobot.commands;

import com.technototes.library.command.Command;
import com.technototes.library.control.CommandAxis;
import com.technototes.library.control.CommandButton;
import com.technototes.library.logger.Log;
import com.technototes.library.logger.Loggable;

public class TriggerTestCommand implements Command, Loggable {

    @Log(name = "Trig value")
    public double trig;

    @Log(name = "Thresh value")
    public boolean thresh;

    CommandAxis s;
    CommandButton b;

    public TriggerTestCommand(CommandAxis stick, CommandButton button) {
        s = stick;
        b = button;
    }

    @Override
    public void execute() {
        thresh = b.getAsBoolean();
        trig = s.getAsDouble();
    }
}
