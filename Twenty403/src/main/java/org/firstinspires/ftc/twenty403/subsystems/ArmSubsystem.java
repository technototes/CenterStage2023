package org.firstinspires.ftc.twenty403.subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.control.PIDCoefficients;
import com.acmerobotics.roadrunner.control.PIDFController;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.util.Range;
import com.technototes.library.hardware.motor.EncodedMotor;
import com.technototes.library.hardware.servo.Servo;
import com.technototes.library.logger.Log;
import com.technototes.library.logger.Loggable;
import com.technototes.library.subsystem.Subsystem;

@Config
public class ArmSubsystem implements Subsystem, Loggable {

    public static double OPEN_CLAW_POS = 0.1; //Tested as of 10/27
    public static double CLOSE_CLAW_POS = 0.4; //Tested as of 10/27

    public static int SHOULDER_ARM_INTAKE = 602;
    public static int SHOULDER_MANUAL_STEP = 15;
    public static int SHOULDER_FIRST_LINE_SCORING = 401;
    public static int SHOULDER_NEUTRAL_ARM_POSITION = 0;
    public static int SHOULDER_SECOND_LINE_SCORING = 350;
    public static int SHOULDER_THIRD_LINE_SCORING = 300;

    public static double MIN_SHOULDER_MOTOR_SPEED = -0.5;
    public static double MAX_SHOULDER_MOTOR_SPEED = 0.5;

    public static int ELBOW_ARM_INTAKE = 602;
    public static int ELBOW_MANUAL_STEP = 15;
    public static int ELBOW_FIRST_LINE_SCORING = 401;
    public static int ELBOW_NEUTRAL_ARM_POSITION = 0;
    public static int ELBOW_SECOND_LINE_SCORING = 350;
    public static int ELBOW_THIRD_LINE_SCORING = 300;

    public static double MIN_ELBOW_MOTOR_SPEED = -0.5;
    public static double MAX_ELBOW_MOTOR_SPEED = 0.5;

    @Log(name = "shoulderPos")
    public int shoulderPos;

    @Log(name = "shoulderPow")
    public double shoulderPow;

    @Log(name = "shoulderTarget")
    public int shoulderTargetPos;

    @Log(name = "elbowPos")
    public int elbowPos;

    @Log(name = "elbowPow")
    public double elbowPow;

    @Log(name = "elbowTarget")
    public int elbowTargetPos;

    private Servo clawServo;
    private EncodedMotor<DcMotorEx> shoulderMotor, elbowMotor;
    private boolean haveHardware;
    public static PIDCoefficients shoulderPID = new PIDCoefficients(0.0027, 0.0, 0.00015);
    public static PIDCoefficients elbowPID = new PIDCoefficients(0.0027, 0.0, 0.00015);
    private PIDFController shoulderPidController, elbowPidController;
    public int shoulderResetPos, elbowResetPos;

    public ArmSubsystem(
        Servo claw,
        EncodedMotor<DcMotorEx> shoulder,
        EncodedMotor<DcMotorEx> elbow
    ) {
        clawServo = claw;
        shoulderMotor = shoulder;
        elbowMotor = elbow;
        haveHardware = true;
        shoulderPidController = new PIDFController(shoulderPID, 0, 0, 0, (x, y) -> 0.0);
        elbowPidController = new PIDFController(elbowPID, 0, 0, 0, (x, y) -> 0.0);
        resetArmNeutral();
    }

    public ArmSubsystem() {
        clawServo = null;
        shoulderMotor = null;
        elbowMotor = null;
        haveHardware = false;
        shoulderPidController = new PIDFController(shoulderPID, 0, 0, 0, (x, y) -> 0.0);
        elbowPidController = new PIDFController(elbowPID, 0, 0, 0, (x, y) -> 0.0);
        resetArmNeutral();
    }

    public void open() {
        setClawPos(OPEN_CLAW_POS);
    }

    public void close() {
        setClawPos(CLOSE_CLAW_POS);
    }

    public void resetArmNeutral() {
        shoulderResetPos = getShoulderUnmodifiedPosition();
        // We don't want the destination to go nuts, so update the target with the new zero
        shoulderTargetPos = shoulderResetPos;
    }

    public void intake() {
        setShoulderPos(SHOULDER_ARM_INTAKE);
        setElbowPos(ELBOW_ARM_INTAKE);
    }

    public void shoulder_increment() {
        setShoulderPos(shoulderTargetPos + SHOULDER_MANUAL_STEP);
    }

    public void shoulder_decrement() {
        setShoulderPos(shoulderTargetPos - SHOULDER_MANUAL_STEP);
    }

    public void elbow_increment() {
        setElbowPos(elbowTargetPos + ELBOW_MANUAL_STEP);
    }

    public void elbow_decrement() {
        setElbowPos(elbowTargetPos - ELBOW_MANUAL_STEP);
    }

    public void firstLineScoring() {
        setShoulderPos(SHOULDER_FIRST_LINE_SCORING);
        setElbowPos(ELBOW_FIRST_LINE_SCORING);
    }

    public void neutralArmPosition() {
        setShoulderPos(SHOULDER_NEUTRAL_ARM_POSITION);
        setElbowPos(ELBOW_NEUTRAL_ARM_POSITION);
    }

    public void secondLineScoring() {
        setShoulderPos(SHOULDER_SECOND_LINE_SCORING);
        setElbowPos(ELBOW_SECOND_LINE_SCORING);
    }

    public void thirdLineScoring() {
        setShoulderPos(SHOULDER_THIRD_LINE_SCORING);
        setElbowPos(ELBOW_THIRD_LINE_SCORING);
    }

    @Override
    public void periodic() {
        shoulderPos = getShoulderCurrentPos();
        shoulderPow = shoulderPidController.update(shoulderPos);
        setShoulderMotorPower(shoulderPow);
        elbowPos = getElbowCurrentPos();
        elbowPow = elbowPidController.update(elbowPos);
        setElbowMotorPower(elbowPow);
    }

    private void setShoulderPos(int e) {
        shoulderPidController.setTargetPosition(e);
        shoulderTargetPos = e;
    }

    private void setElbowPos(int e) {
        elbowPidController.setTargetPosition(e);
        elbowTargetPos = e;
    }

    private void setClawPos(double c) {
        if (clawServo != null) {
            clawServo.setPosition(c);
        }
    }

    private int getShoulderCurrentPos() {
        return getShoulderUnmodifiedPosition() - shoulderResetPos;
    }

    private int getShoulderUnmodifiedPosition() {
        if (haveHardware) {
            return (int) shoulderMotor.getSensorValue();
        } else {
            return 0;
        }
    }

    private void setShoulderMotorPower(double speed) {
        if (haveHardware) {
            double clippedSpeed = Range.clip(
                speed,
                MIN_SHOULDER_MOTOR_SPEED,
                MAX_SHOULDER_MOTOR_SPEED
            );
            shoulderMotor.setSpeed(clippedSpeed);
        }
    }

    private int getElbowCurrentPos() {
        return getElbowUnmodifiedPosition() - elbowResetPos;
    }

    private int getElbowUnmodifiedPosition() {
        if (haveHardware) {
            return (int) elbowMotor.getSensorValue();
        } else {
            return 0;
        }
    }

    private void setElbowMotorPower(double speed) {
        if (haveHardware) {
            double clippedSpeed = Range.clip(speed, MIN_ELBOW_MOTOR_SPEED, MAX_ELBOW_MOTOR_SPEED);
            elbowMotor.setSpeed(clippedSpeed);
        }
    }
}
