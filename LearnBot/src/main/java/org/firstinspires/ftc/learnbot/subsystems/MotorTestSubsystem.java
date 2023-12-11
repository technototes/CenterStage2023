package org.firstinspires.ftc.learnbot.subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.technototes.library.hardware.motor.EncodedMotor;
import com.technototes.library.hardware.motor.Motor;
import com.technototes.library.hardware.sensor.Rev2MDistanceSensor;
import com.technototes.library.hardware.servo.Servo;
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

    public void motorInc() {
        if (controlMode.equals("digital")) setPower(getPower() + POWER_CHANGE);
    }

    public void motorDec() {
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
            theMotor.setSpeed(d);
            power = d;
        }
    }

    private double getPower() {
        return power;
    }
}
