package org.firstinspires.ftc.twenty403.subsystems;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.technototes.library.hardware.motor.Motor;
import com.technototes.library.hardware.servo.Servo;
import com.technototes.library.logger.Loggable;
import com.technototes.library.subsystem.Subsystem;
import org.firstinspires.ftc.twenty403.Hardware;

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
        if (isHardware == true) {
            hangServo1.setPosition(upPositionNumber);
        }
    }

    public void servoPushPosition() {
        if (isHardware == true) {
            hangServo1.setPosition(downPositionNumber);
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

    public void neutralPosition() {
        if (isHardware == true) {
            hangServo1.setPosition(neutralPositionNumber);
        }
    }
}
