package org.firstinspires.ftc.learnbot.subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.technototes.library.hardware.motor.Motor;
import com.technototes.library.logger.Loggable;
import com.technototes.library.subsystem.Subsystem;

@Config
public class FlywheelSubsystem implements Subsystem, Loggable {

    public static double INTAKE_SPEED = -0.8; //tested 1/9/24
    public static double OUTPUT_SPEED = .3;
    public Motor<DcMotorEx> leftMotor;
    //public Motor<DcMotorEx> rightMotor;
    private boolean isHardware;

    public FlywheelSubsystem(Motor<DcMotorEx> left) {
        leftMotor = left;
        //rightMotor = right;
        // This function is dumb. Note to mentor or programmer who wants to:
        // Deprecate this one, and make a "setReverse()" function.
        //rightMotor.setInverted(false);
        isHardware = true;
    }

    public FlywheelSubsystem() {
        isHardware = false;
        leftMotor = null;
        //rightMotor = null;
    }

    public void intake() {
        spin(INTAKE_SPEED);
    }

    public void stop() {
        spin(0);
    }

    public void output() {
        spin(OUTPUT_SPEED);
    }

    private void spin(double n) {
        if (isHardware) {
            leftMotor.setSpeed(n);
            //rightMotor.setSpeed(n);
        }
    }
}

