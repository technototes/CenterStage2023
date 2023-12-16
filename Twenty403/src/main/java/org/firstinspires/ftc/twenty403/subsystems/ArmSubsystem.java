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

    public static double CLOSE_CLAW_POS = 0; //do we need this? (change vals)
    public static double OPEN_CLAW_POS = 0.45; //do we need this? (change vals)

    public static int SHOULDER_ARM_INTAKE = 670; //collect (change vals)
    public static int SHOULDER_RESET_POSITION = -40; //reset (change vals)
    public static int SHOULDER_FIRST_LINE_SCORING = 600; //change vals
    public static int SHOULDER_SECOND_LINE_SCORING = 500; //change vals
    public static int SHOULDER_THIRD_LINE_SCORING = 552; //change vals
    public static int SHOULDER_VERTICAL = 369; // For feed-fwd, and maybe hang
    public static int SHOULDER_MANUAL_STEP = 20; //increment/decrement

    public static double MIN_SHOULDER_MOTOR_SPEED = -1;
    public static double MAX_SHOULDER_MOTOR_SPEED = 1;

    public static int WRIST_ARM_INTAKE = -712; //collect (change vals)
    public static int WRIST_RESET_POSITION = 0; //reset (change vals)
    public static int WRIST_FIRST_LINE_SCORING = -800; // change vals
    public static int WRIST_SECOND_LINE_SCORING = -700; // change vals
    public static int WRIST_THIRD_LINE_SCORING = -529; // change vals
    public static int WRIST_MANUAL_STEP = 20; //increment/decrement

    public static int USE_SHOULDER_BRAKE = 0;

    @Log(name = "shoulderPos")
    public int shoulderPos;

    @Log(name = "shoulderPow")
    public double shoulderPow;

    @Log(name = "shoulderTarget")
    public int shoulderTargetPos;

    @Log(name = "wristPos")
    public int wristPos;

    @Log(name = "wristTarget")
    public int wristTargetPos;

    private Servo clawServo;
    private Servo wristServo;
    private EncodedMotor<DcMotorEx> shoulderMotor;
    private boolean haveHardware;
    public static double FEEDFORWARD_COEFFICIENT = 0.3;
    public static PIDCoefficients shoulderPID = new PIDCoefficients(0.00175, 0.0, 0.00009);
    private PIDFController shoulderPidController;
    public int shoulderResetPos, wristResetPos;

    public ArmSubsystem(Servo claw, Servo wrist, EncodedMotor<DcMotorEx> shoulder) {
        clawServo = claw;
        wristServo = wrist;
        shoulderMotor = shoulder;
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
    }

    public ArmSubsystem() {
        clawServo = null;
        wristServo = null;
        shoulderMotor = null;
        haveHardware = false;
        shoulderPidController = new PIDFController(shoulderPID, 0, 0, 0, (x, y) -> 0.0);
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
        wristResetPos = getWristUnmodifiedPosition();
        wristTargetPos = wristResetPos;
    }

    public void wristIntake() {
        setWristPos(WRIST_ARM_INTAKE);
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

    public void wrist_increment() {
        setWristPos(wristTargetPos + WRIST_MANUAL_STEP);
    }

    public void wrist_decrement() {
        setWristPos(wristTargetPos - WRIST_MANUAL_STEP);
    }

    /*public void elbowFold() {
        setWristPos(WRIST_FOLD_POS);
    }

    public void elbowUnfold() {
        setWristPos(WRIST_UNFOLD_POS);
    }

    public void maybeMoveElbow() {
        if (getShoulderCurrentPos() > 369) {
            elbowFold();
        }
    }

    public void maybeUnfoldElbow() {
        elbowUnfold();
    }*/

    public void shoulderVertical() {
        setShoulderPos(SHOULDER_VERTICAL);
    }

    public void wristFirstLineScoring() {
        setWristPos(WRIST_FIRST_LINE_SCORING);
    }

    public void shoulderFirstLineScoring() {
        setShoulderPos(SHOULDER_FIRST_LINE_SCORING);
    }

    public void wristNeutralArmPosition() {
        setWristPos(WRIST_RESET_POSITION);
    }

    public void shoulderNeutralArmPosition() {
        setShoulderPos(SHOULDER_RESET_POSITION);
    }

    public void wristSecondLineScoring() {
        setWristPos(WRIST_SECOND_LINE_SCORING);
    }

    public void shoulderSecondLineScoring() {
        setShoulderPos(SHOULDER_SECOND_LINE_SCORING);
    }

    public void wristThirdLineScoring() {
        setWristPos(WRIST_THIRD_LINE_SCORING);
    }

    public void shoulderThirdLineScoring() {
        setShoulderPos(SHOULDER_THIRD_LINE_SCORING);
    }

    @Override
    public void periodic() {
        shoulderPos = getShoulderCurrentPos();
        shoulderPow = shoulderPidController.update(shoulderPos);
        setShoulderMotorPower(shoulderPow);
        wristPos = getWristCurrentPos();
    }

    private void setShoulderPos(int e) {
        shoulderPidController.setTargetPosition(e);
        shoulderTargetPos = e;
    }

    private void setWristPos(int w) {
        if (wristServo != null) {
            wristServo.setPosition(w);
        }
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

    private int getWristCurrentPos() {
        return getWristUnmodifiedPosition() - wristResetPos;
    }

    private int getWristUnmodifiedPosition() {
        if (haveHardware) {
            return (int) wristServo.getSensorValue();
        } else {
            return 0;
        }
    }
}
