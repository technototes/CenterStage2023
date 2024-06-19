package org.firstinspires.ftc.learnbot.subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.technototes.library.command.Command;
import com.technototes.library.command.MethodCommand;
import com.technototes.library.hardware.motor.EncodedMotor;
import com.technototes.library.hardware.motor.Motor;
import com.technototes.library.logger.Log;
import com.technototes.library.logger.Loggable;
import com.technototes.library.subsystem.Subsystem;
import org.firstinspires.ftc.learnbot.Hardware;

@Config
public class MotorTestSubsystem implements Subsystem, Loggable {

    public static double POWER_CHANGE = 0.02;

    // but only while/if the the distance is greater than 10cm
    private Motor<?> theMotor;

    private double curPower;

    @Log(name = "Power")
    public volatile double power = 0.0;

    @Log(name = "Stop mode [O]")
    public String stopMode = "coast";

    @Log(name = "Control Mode [X]")
    public String controlMode = "analog";

    public MotorTestSubsystem(Hardware hw) {
        theMotor = hw.theMotor;
        curPower = 0.0;
    }

    // Example of another way to create 'simple' commands.
    // A couple of advantages of doing it this way:
    // 1. You don't have to expose the methods as public.
    //    Notice that motorInc and motorDec are both *private* methods!
    // 2. What is controlling or observing is controlled in the subsystem,
    //    rather than by the author of the commands. This helps minimize
    //    how much understanding of the subsystem has to exist outside the
    //    implementation.
    public Command MotorInc() {
        return new MethodCommand(this::motorInc, this);
    }

    public Command MotorDec() {
        return new MethodCommand(this::motorDec, this);
    }

    private void motorInc() {
        if (controlMode.equals("digital")) setPower(getPower() + POWER_CHANGE);
    }

    private void motorDec() {
        if (controlMode.equals("digital")) setPower(getPower() - POWER_CHANGE);
    }

    public void toggleMotorStopMode() {
        if (stopMode.equals("coast")) {
            theMotor.brake();
            stopMode = "brake";
        } else {
            theMotor.coast();
            stopMode = "coast";
        }
    }

    public void toggleMotorControlMode() {
        setPower(0);
        if (controlMode.equals("digital")) {
            controlMode = "analog";
        } else {
            controlMode = "digital";
        }
    }

    public void setMotorPower(double d) {
        if (controlMode.equals("analog")) setPower(d);
    }

    private void setPower(double d) {
        if (theMotor != null) {
            theMotor.setPower(d);
            power = d;
        }
    }

    private double getPower() {
        return power;
    }
}
