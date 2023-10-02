package org.firstinspires.ftc.learnbot.commands;

import com.technototes.library.command.Command;
import com.technototes.library.logger.Log;
import com.technototes.library.logger.Loggable;
import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;
import org.firstinspires.ftc.learnbot.Setup;
import org.firstinspires.ftc.learnbot.Setup.OtherSettings;
import org.firstinspires.ftc.learnbot.subsystems.TestSubsystem;

public class MotorMovementCommand implements Command, Loggable {

    @Log(name = "")
    public String instruction = "RStick ^/v";

    @Log.Number(name = "Move Value")
    public double value;

    @Log.Number(name = "Move Position")
    public double ticks;

    TestSubsystem test;
    DoubleSupplier d;

    public MotorMovementCommand(TestSubsystem ts, DoubleSupplier axis) {
        test = ts;
        d = axis;
        addRequirements(ts);
    }

    @Override
    public void execute() {
        double stickPos = d.getAsDouble();
        if (Math.abs(stickPos) < OtherSettings.STICK_DEAD_ZONE) {
            stickPos = 0;
        } else {
            // Scale stickPos from deadzone to 1 and square it to make it a little easier to control
            double scaled =
                (Math.abs(stickPos) - OtherSettings.STICK_DEAD_ZONE) /
                (1 - OtherSettings.STICK_DEAD_ZONE);
            // This gets the sign from stickPos, and puts it on scaled ^ 3
            stickPos = Math.copySign(scaled * scaled * scaled, stickPos);
        }
        value = stickPos;
        test.setMotorPower(stickPos);
        ticks = test.getMotorPosition();
    }
}
