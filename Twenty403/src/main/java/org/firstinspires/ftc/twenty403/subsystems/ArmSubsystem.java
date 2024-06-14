package org.firstinspires.ftc.twenty403.subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.control.PIDCoefficients;
import com.acmerobotics.roadrunner.control.PIDFController;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.util.Range;
import com.technototes.library.hardware.motor.EncodedMotor;
import com.technototes.library.hardware.motor.Motor;
import com.technototes.library.hardware.servo.Servo;
import com.technototes.library.logger.Log;
import com.technototes.library.logger.Loggable;
import com.technototes.library.subsystem.Subsystem;

@Config
public class ArmSubsystem implements Subsystem, Loggable {
    public static int SHOULDER_ARM_INTAKE = 30; //collect (change vals)
    public static int SHOULDER_RESET_POSITION = 8; //reset (change vals)
    public static int SHOULDER_FIRST_LINE_SCORING = 1350; //change vals
    public static int SHOULDER_SECOND_LINE_SCORING = 1200; //change vals
    public static int SHOULDER_THIRD_LINE_SCORING = 1140; //change vals
    public static int SHOULDER_VERTICAL = 881; // For feed-fwd, and maybe hang
    public static int SHOULDER_MANUAL_STEP = 20; //increment/decrement
    public static int SHOULDER_LARGE_STEP = 850; //increment/decrement
    public static double MIN_SHOULDER_MOTOR_SPEED = -0.5;
    public static double MAX_SHOULDER_MOTOR_SPEED = 0.5;
    public static double MIN_HANG_SPEED = -1;
    public static double MAX_HANG_SPEED = 1;

    public static double MIN_INTAKE_SPEED = -1;
    public static double MAX_INTAKE_SPEED = 1;
    public static double SPIT_SPEED = -.3;

    public static double WRIST_ARM_INTAKE = 0.7; //collect (change vals)
    public static double WRIST_RESET_POSITION = 0.2; //reset (change vals)
    public static double WRIST_FIRST_LINE_SCORING = 0.2; // change vals
    public static double WRIST_SECOND_LINE_SCORING = 0.2; // change vals
    public static double WRIST_THIRD_LINE_SCORING = 0.2; // chan5ge vals
    public static double WRIST_MANUAL_STEP = 0.05; //increment/decrement (change vals)

    public static boolean USE_SHOULDER_BRAKE = true;

    @Log(name = "shoulderPos")
    public int shoulderPos;

    @Log(name = "shoulderPow")
    public double shoulderPow;

    @Log(name = "shoulderTarget")
    public int shoulderTargetPos;

    @Log(name = "wristPos")
    public double wristPos;

    @Log(name = "wristTarget")
    public double wristTargetPos;

    private CRServo intakeServo;
    private Servo wristServo;
    private EncodedMotor<DcMotorEx> shoulderMotor;
    private Motor<DcMotorEx> shoulder2Motor;
    private boolean haveHardware;
    private boolean hangMode = false;
    public static double FEEDFORWARD_COEFFICIENT = 0.13;
    public static PIDCoefficients shoulderPID = new PIDCoefficients(0.004, 0.00002, 0.00027);
    private PIDFController shoulderPidController;
    public int shoulderResetPos;
    public double wristResetPos;
    public ArmSubsystem(CRServo intake, Servo wrist, EncodedMotor<DcMotorEx> shoulder, Motor<DcMotorEx> shoulder2) {
        intakeServo = intake;
        wristServo = wrist;
        shoulderMotor = shoulder;
        shoulder2Motor = shoulder2;
        haveHardware = true;
        if (USE_SHOULDER_BRAKE) {
            shoulder.brake();
            shoulder2.brake();
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
        resetArmNeutral();
    }

    public ArmSubsystem() {
        intakeServo = null;
        wristServo = null;
        shoulderMotor = null;
        shoulder2Motor = null;
        haveHardware = false;
        shoulderPidController = new PIDFController(shoulderPID, 0, 0, 0, (x, y) -> 0.0);
        resetArmNeutral();
    }

    public void resetArmNeutral() {
        shoulderResetPos = getShoulderUnmodifiedPosition();
        // We don't want the destination to go nuts, so update the target with the new zero
        shoulderTargetPos = shoulderResetPos;
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
    public void shoulder_largeDecrement() {
        setShoulderPos(shoulderTargetPos - SHOULDER_LARGE_STEP);
    }
    public void shoulder_largeIncrement() {
        setShoulderPos(shoulderTargetPos + SHOULDER_LARGE_STEP);
    }
    public void wrist_decrement() {
        setWristPos(wristTargetPos - WRIST_MANUAL_STEP);
    }

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
    public void stopIntake() {setServoMotorPower(0);}
    public void slurpIntake() {setServoMotorPower(MAX_INTAKE_SPEED);}
    public void spitIntake() {setServoMotorPower(SPIT_SPEED);}

    @Override
    public void periodic() {
        if (!hangMode){
            shoulderPos = getShoulderCurrentPos();
            shoulderPow = shoulderPidController.update(shoulderPos);
            setShoulderMotorPower(shoulderPow);
        }
    }

    private void setShoulderPos(int e) {
        shoulderPidController.setTargetPosition(e);
        shoulderTargetPos = e;
    }

    private void setWristPos(double w) {
        if (wristServo != null) {
            wristServo.setPosition(w);
            wristTargetPos = w;
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
    private void setServoMotorPower(double p) {
        if (haveHardware) {
            double clippedSpeed = Range.clip(
                    p,
                    MIN_INTAKE_SPEED,
                    MAX_INTAKE_SPEED
            );
            intakeServo.setPower(clippedSpeed);
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
            shoulder2Motor.setSpeed(clippedSpeed);
        }
    }

    public void enterHangMode(){
        hangMode = true;
        setShoulderMotorPower(0);
    }
    public void leaveHangMode(){
        hangMode = false;
        setShoulderMotorPower(0);
        setShoulderPos(getShoulderCurrentPos());//so arm doesn't swing/move
    }
    private void setHangMotorPower(double speed){
        if (haveHardware) {
            shoulderMotor.setSpeed(speed);
            shoulder2Motor.setSpeed(-speed);
        }
    }
    public void hangUp(){
        setHangMotorPower(MAX_HANG_SPEED);
    }
    public void hangDown(){
        setHangMotorPower(MIN_HANG_SPEED);
    }
    public void hangStop(){
        setHangMotorPower(0);
    }
}
