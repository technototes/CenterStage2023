package org.firstinspires.ftc.sixteen750.subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.technototes.library.hardware.motor.Motor;
import com.technototes.library.hardware.servo.Servo;
import com.technototes.library.logger.Log;
import com.technototes.library.logger.Loggable;
import com.technototes.library.subsystem.Subsystem;
import org.firstinspires.ftc.sixteen750.Hardware;
import org.firstinspires.ftc.sixteen750.Robot;

@Config
public class HangSubsystem implements Subsystem, Loggable {

    //this code sets the arm position of the hang subsystem.

    public static double upPositionPower = .5;
    public static double downPositionPower = -.5;
    //    public static double neutralPositionNumber = 0;

    // one of the 2 variables below are going to be negative and the other is going to be positive
    public static double extendScrewSpeed = 1;

    public static double retractScrewSpeed = -1;

    public static Motor<DcMotorEx> hangMotor1;

    public static CRServo hangServo1;

    private boolean isHardware;

    // @Log(name="HangServo")
    public double servoPos;

    @Log(name = "Hang Motor")
    public double motorSpeed;

    // @Log(name="HangMotor")
    //    public double motorSpeed;

    public HangSubsystem(Hardware hw) {
        hangServo1 = hw.hangS;
        hangMotor1 = hw.hangM;
        isHardware = true;
    }

    public HangSubsystem() {
        hangServo1 = null;
        hangMotor1 = null;
        isHardware = false;
    }

    public void ElbowUp() {
        SetServoPower(upPositionPower);
    }

    public void ElbowDown() {
        SetServoPower(downPositionPower);
    }

    public void ElbowStop() {
        SetServoPower(0);
    }

    public void leadScrewExtended() {
        setMotorSpeed(extendScrewSpeed);
    }

    public void leadScrewRetract() {
        setMotorSpeed(retractScrewSpeed);
    }

    public void leadScrewStop() {
        setMotorSpeed(0);
    }

    //    public void ServoUp() {
    //        SetServoPower(upPositionNumber);
    //    }

    private void SetServoPower(double pos) {
        if (isHardware == true) {
            hangServo1.setPower(pos);
        }
        servoPos = pos;
    }

    private void setMotorSpeed(double speed) {
        if (isHardware == true) {
            hangMotor1.setSpeed(speed);
        }
        motorSpeed = speed;
    }
}
