package org.firstinspires.ftc.learnbot.subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.util.Range;
import com.technototes.library.hardware.motor.EncodedMotor;
import com.technototes.library.hardware.servo.Servo;
import com.technototes.library.logger.Log;
import com.technototes.library.logger.Loggable;
import com.technototes.library.subsystem.Subsystem;

@Config
public class IntakeSubsystem implements Subsystem, Loggable {

    public static double MIN_FLYWHEEL_MOTOR_SPEED = -0.5;
    public static double MAX_FLYWHEEL_MOTOR_SPEED = 0.5;

    public static double MIN_INTAKE_SPEED = -1;
    public static double MAX_INTAKE_SPEED = 1;
    public static double SPIT_SPEED = -.3;

    @Log(name = "shoulderPow")
    public double shoulderPow;

    private CRServo intakeServo;
    private EncodedMotor<DcMotorEx> flywheelMotor;
    private boolean haveHardware;

    public IntakeSubsystem(
            CRServo intake,
            EncodedMotor<DcMotorEx> shoulder
    ) {
        intakeServo = intake;
        flywheelMotor = shoulder;
        haveHardware = true;
    }

    public IntakeSubsystem() {
        intakeServo = null;
        flywheelMotor = null;
        haveHardware = false;
    }

    public void stopIntake() {
        setServoMotorPower(0);
    }

    public void slurpIntake() {
        setServoMotorPower(MAX_INTAKE_SPEED);
    }

    public void spitIntake() {
        setServoMotorPower(SPIT_SPEED);
    }

    @Override
    public void periodic() {
            setShoulderMotorPower(shoulderPow);
    }

    private void setServoMotorPower(double p) {
        if (haveHardware) {
            double clippedSpeed = Range.clip(p, MIN_INTAKE_SPEED, MAX_INTAKE_SPEED);
            intakeServo.setPower(clippedSpeed);
        }
    }

    private void setShoulderMotorPower(double speed) {
        if (haveHardware) {
            double clippedSpeed = Range.clip(
                    speed,
                    MIN_FLYWHEEL_MOTOR_SPEED,
                    MAX_FLYWHEEL_MOTOR_SPEED
            );
            flywheelMotor.setSpeed(clippedSpeed);
        }
    }

}

