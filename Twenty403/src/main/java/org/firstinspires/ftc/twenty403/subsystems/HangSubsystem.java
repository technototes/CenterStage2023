package org.firstinspires.ftc.twenty403.subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.technototes.library.hardware.motor.Motor;
import com.technototes.library.hardware.servo.Servo;
import com.technototes.library.logger.Loggable;
import com.technototes.library.subsystem.Subsystem;
import org.firstinspires.ftc.twenty403.Hardware;

@Config
public class HangSubsystem implements Subsystem, Loggable {

    //this code sets the arm position of the hang subsystem.

    public static double UP_SERVO_POWER = 0.5;
    public static double DOWN_SERVO_POWER = -0.5;

    // one of the 2 variables below are going to be negative and the other is going to be positive
    public static double extendScrew = 0.4;

    public static double retractScrew = -0.3;

    public static Motor<DcMotorEx> hangMotor1;

    public static CRServo hangServo1;

    private boolean isHardware;

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

    public void hingeUp() {
        if (isHardware == true) {
            hangServo1.setPower(UP_SERVO_POWER);
        }
    }

    public void hingeDown() {
        if (isHardware == true) {
            hangServo1.setPower(DOWN_SERVO_POWER);
        }
    }
    public void hingeStop() {
        if (isHardware == true) {
            hangServo1.setPower(0);
        }
    }

    public void leadScrewExtended() {
        if (isHardware == true) {
            hangMotor1.setSpeed(extendScrew);
        }
    }

    public void leadScrewRetract() {
        if (isHardware == true) {
            hangMotor1.setSpeed(retractScrew);
        }
    }
    public void leadScrewStop() {
        if (isHardware == true) {
            hangMotor1.setSpeed(0);
        }
    }
//    public void neutralPosition() {
//        if (isHardware == true) {
//            hangServo1.setPosition(neutralPositionNumber);
//        }
    }

