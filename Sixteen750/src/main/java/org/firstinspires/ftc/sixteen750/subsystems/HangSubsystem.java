package org.firstinspires.ftc.sixteen750.subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.technototes.library.hardware.motor.EncodedMotor;
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

    public static double hangPosition = 0.9;
    public static double downPosition = -0.5;
    public static double neutralPositionNumber = 1;

    // one of the 2 variables below are going to be negative and the other is going to be positive
    public static double extendScrewSpeed = 1;

    public static double retractScrewSpeed = -1;

//    public static double HANGMIN = -1000000;

    public static double HANGMAX = 11000;

    public static EncodedMotor<DcMotorEx> hangMotor1;

    public static Servo hangServo1;

    private boolean isHardware;



    @Log(name="HangServo")
    public double servoPos;

    @Log(name = "Hang Motor")
    public double motorSpeed;

    @Log(name = "Hang Motor pos")
    public double motorpos;



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
        SetServoPower(hangPosition);
    }

    public void ElbowDown() {
        SetServoPower(downPosition);
    }

    public void ElbowNeutral() {
        SetServoPower(neutralPositionNumber);
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
            hangServo1.setPosition(pos);
        }
        servoPos = pos;
    }

    private void setMotorSpeed(double speed) {
        if (isHardware == true) {
//            double position = hangMotor1.getSensorValue();
//            if(position < HANGMIN && speed < 0) {
//                speed = 0;
//            } else if (position > HANGMAX && speed > 0) {
//                speed = 0;
//            }
            hangMotor1.setSpeed(speed);

        }
        motorSpeed = speed;
    }
    @Override public void periodic () {
        motorpos = hangMotor1.getSensorValue();
    }

}
