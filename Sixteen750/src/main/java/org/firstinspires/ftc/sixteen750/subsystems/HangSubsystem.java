package org.firstinspires.ftc.sixteen750.subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.technototes.library.hardware.motor.Motor;
import com.technototes.library.hardware.servo.Servo;
import com.technototes.library.logger.Loggable;
import com.technototes.library.subsystem.Subsystem;
import org.firstinspires.ftc.sixteen750.Hardware;
import org.firstinspires.ftc.sixteen750.Robot;

@Config
public class HangSubsystem implements Subsystem, Loggable {

    //this code sets the arm position of the hang subsystem.

    public static double upPositionNumber = 0;
    public static double downPositionNumber = 0;
    public static double neutralPositionNumber = 0;

    // one of the 2 variables below are going to be negative and the other is going to be positive
    public static double extendScrew = 0;

    public static double retractScrew = 0;

    public static Motor<DcMotorEx> hangMotor1;

    public static Servo hangServo1;

    private boolean isHardware;

    // @Log(name="HangServo")
    public double servoPos;

    // @Log(name="HangMotor")
    public double motorSpeed;

    public HangSubsystem(Hardware hw) {
        hangServo1 = hw.hangServo1;
        hangMotor1 = hw.hangMotor1;
        isHardware = true;
    }

    public HangSubsystem() {
        hangServo1 = null;
        hangMotor1 = null;
        isHardware = false;
    }

    public void servoHangPosition() {
        setServoPosition(upPositionNumber);
    }

    public void servoPushPosition() {
        setServoPosition(downPositionNumber);
    }

    public void leadScrewExtended() {
        setMotorSpeed(extendScrew);
    }

    public void leadScrewRetract() {
        setMotorSpeed(retractScrew);
    }

    public void neutralPosition() {
        setServoPosition(neutralPositionNumber);
    }

    private void setServoPosition(double pos) {
        if (isHardware == true) {
            hangServo1.setPosition(pos);
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
