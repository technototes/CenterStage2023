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

    public static double CLOSE_CLAW_POS = 0; //needs retesting yay
    public static double OPEN_CLAW_POS = 0.45; //needs retesting yay

    public static int SHOULDER_ARM_INTAKE = 670; //collect
    public static int SHOULDER_MANUAL_STEP = 15; //inc/dec
    public static int SHOULDER_FIRST_LINE_SCORING = 600;
    public static int SHOULDER_NEUTRAL_ARM_POSITION = -40; //reset
    public static int SHOULDER_SECOND_LINE_SCORING = 500;
    public static int SHOULDER_THIRD_LINE_SCORING = 552;
    public static int SHOULDER_VERTICAL = 369; // For feed-fwd, and maybe hang

    public static double MIN_SHOULDER_MOTOR_SPEED = -1;
    public static double MAX_SHOULDER_MOTOR_SPEED = 1;

    public static int ELBOW_ARM_INTAKE = -712; //collect
    public static int ELBOW_MANUAL_STEP = 15; //increment/decrement
    public static int ELBOW_FOLD_POS = -300;
    public static int ELBOW_UNFOLD_POS = -100;
    public static int ELBOW_FIRST_LINE_SCORING = -800;
    public static int ELBOW_NEUTRAL_ARM_POSITION = 0; //reset
    public static int ELBOW_SECOND_LINE_SCORING = -700;
    public static int ELBOW_THIRD_LINE_SCORING = -529;

    public static double MIN_ELBOW_MOTOR_SPEED = -1;
    public static double MAX_ELBOW_MOTOR_SPEED = 1;
    public static int USE_SHOULDER_BRAKE = 0;

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
    public static double FEEDFORWARD_COEFFICIENT = 0.3;
    public static PIDCoefficients shoulderPID = new PIDCoefficients(0.00175, 0.0, 0.00009);
    public static PIDCoefficients elbowPID = new PIDCoefficients(0.001, 0.0, 0.000075);
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
        if (USE_SHOULDER_BRAKE == 0) {
            shoulder.brake();
        }
        shoulderPidController =
            new PIDFController(
                shoulderPID,
                0,
                0,
                0,
                /*

            The function arguments for the Feed Forward function are Position (ticks) and
            Velocity (units?). So, for the shoulder, we want to check to see if which side of
            center we're on, and if the velocity is pushing us down, FF should probably be
            low (negative?) while if velocity is pushing us *up*, FF should be high (right?)
            Someone who's done physics and/or calculus recently should write some real equations

            Braelyn got the math right

            For angle T through this range where we start at zero:
                       /
                      / T
            180 _____/_____ 0
            The downward torque due to gravity is cos(T) * Gravity (9.8m/s^2)

            If we're moving from 0 to 180 degrees, then:
                While T is less than 90, the "downward torque" is working *against* the motor
                When T is greater than 90, the "downward torque" is working *with* the motor

             */
                (ticks, velocity) ->
                    FEEDFORWARD_COEFFICIENT * Math.cos((Math.PI * ticks) / (2 * SHOULDER_VERTICAL))
            );
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
        elbowResetPos = getElbowUnmodifiedPosition();
        elbowTargetPos = elbowResetPos;
    }

    public void elbowIntake() {
        setElbowPos(ELBOW_ARM_INTAKE);
    }

    public void shoulderIntake() {
        setShoulderPos(SHOULDER_ARM_INTAKE);
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

    public void elbowFold() {
        setElbowPos(ELBOW_FOLD_POS);
    }

    public void elbowUnfold() {
        setElbowPos(ELBOW_UNFOLD_POS);
    }

    public void maybeMoveElbow() {
        if (getShoulderCurrentPos() > 369) {
            elbowFold();
        }
    }

    public void maybeUnfoldElbow() {
        elbowUnfold();
    }

    public void shoulderVertical() {
        setShoulderPos(SHOULDER_VERTICAL);
    }

    public void elbowFirstLineScoring() {
        setElbowPos(ELBOW_FIRST_LINE_SCORING);
    }

    public void shoulderFirstLineScoring() {
        setShoulderPos(SHOULDER_FIRST_LINE_SCORING);
    }

    public void elbowNeutralArmPosition() {
        setElbowPos(ELBOW_NEUTRAL_ARM_POSITION);
    }

    public void shoulderNeutralArmPosition() {
        setShoulderPos(SHOULDER_NEUTRAL_ARM_POSITION);
    }

    public void elbowSecondLineScoring() {
        setElbowPos(ELBOW_SECOND_LINE_SCORING);
    }

    public void secondLineScoring() {
        setShoulderPos(SHOULDER_SECOND_LINE_SCORING);
    }

    public void elbowThirdLineScoring() {
        setElbowPos(ELBOW_THIRD_LINE_SCORING);
    }

    public void shoulderThirdLineScoring() {
        setShoulderPos(SHOULDER_THIRD_LINE_SCORING);
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
