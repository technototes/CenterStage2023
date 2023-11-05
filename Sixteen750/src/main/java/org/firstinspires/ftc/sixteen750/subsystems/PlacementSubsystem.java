package org.firstinspires.ftc.sixteen750.subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.control.PIDCoefficients;
import com.acmerobotics.roadrunner.control.PIDFController;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.util.Range;
import com.technototes.library.hardware.motor.EncodedMotor;
import com.technototes.library.hardware.motor.Motor;
import com.technototes.library.hardware.servo.Servo;
import com.technototes.library.logger.Loggable;
import com.technototes.library.subsystem.Subsystem;
import org.firstinspires.ftc.sixteen750.Hardware;
import org.firstinspires.ftc.sixteen750.Robot;
import org.firstinspires.ftc.sixteen750.Setup;

@Config
public class PlacementSubsystem implements Subsystem, Loggable {

    public static double INTAKE_SPEED = .3;
    public static double OUTPUT_SPEED = -.3;

    // numbers need to be calibrated for the lift
    public static double LOW_POS = 300;
    public static double MEDIUM_POS = 800;
    public static double HIGH_POS = 1000;
    public static double INTAKELIFT_POS = 0;
    public static double MIN_MOTOR_SPEED = -0.3;
    public static double MAX_MOTOR_SPEED = 1;

    public static double ScoreServo = 0.5;

    public static double ArmServo = 0.5;
    public Servo armServo;

    public static double ScoreServoInput = 0.7;

    public static double ArmServoInput = 1;

    public static double ScoreServoOutput = 0.3;

    public static double ArmServoOutput = 0.2;

    public static PIDCoefficients PID = new PIDCoefficients(0.0027, 0.0, 0.00015);

    public Servo scoreServo;
    public EncodedMotor<DcMotorEx> liftMotor;
    private boolean isHardware;
    private PIDFController leftPidController;

    public PlacementSubsystem(Hardware hw) {
        armServo = hw.armServo;
        scoreServo = hw.scoreServo;
        // TODO:
        // For bhavjot and Laksh:
        // We need to configure the liftMotor to work like a servo.
        // This entails switching to "RunMode.RUN_TO_POSITION" and then tuning PID(F) constants
        liftMotor = hw.liftMotor;
        isHardware = true;
        leftPidController = new PIDFController(PID, 0, 0, 0, (x, y) -> 0.1);
    }

    public PlacementSubsystem() {
        isHardware = false;
        liftMotor = null;
        armServo = null;
        scoreServo = null;
    }

    private int getLiftCurrentPosition() {
        if (Setup.Connected.PLACEMENT) {
            return (int) liftMotor.getSensorValue();
        } else {
            return 0;
        }
    }

    private int getLiftTargetPosition() {
        return (int) leftPidController.getTargetPosition();
    }

    private void setLiftTargetPostion(int p) {
        leftPidController.setTargetPosition(p);
    }

    public void armliftup() {
        // lift to take the intake system up or down

    }

    public void armliftdown() {
        // brings the intake system down

    }

    public void LiftHeightLow() {
        //takes the arm to the first level
        leftPidController.setTargetPosition(LOW_POS);
    }

    public void LiftHeightHigh() {
        //takes the arm to the third level
        leftPidController.setTargetPosition(HIGH_POS);
    }

    public void LiftHeightMedium() {
        //takes the arm to the third level
        leftPidController.setTargetPosition(MEDIUM_POS);
    }

    public void LiftHeightIntake() {
        //brings the arm all the way down
        leftPidController.setTargetPosition(INTAKELIFT_POS);
        //        armServo.setPosition(0);
        //        scoreServo.setPosition(0);
    }

    public void ArmServoOutput() {
        // the arm's position to score
        armServo.setPosition(ArmServoOutput);
    }

    public void ScoreServoOutput() {
        // the intake system's postion to score
        scoreServo.setPosition(ScoreServoOutput);
    }

    public void periodic() {
        double targetSpeed = leftPidController.update(getLiftCurrentPosition());
        double clippedSpeed = Range.clip(targetSpeed, MIN_MOTOR_SPEED, MAX_MOTOR_SPEED);
        setLiftMotorPower(clippedSpeed);
        //        setLiftPosition_OVERRIDE(
        //                leftPidController.getTargetPosition(),
        //                rightPidController.getTargetPosition()
        //        );

    }

    private void setLiftMotorPower(double speed) {
        if (isHardware) {
            liftMotor.setSpeed(speed);
        }
    }

    public void ArmServoInput() {
        // positions for the arm of the bot
        armServo.setPosition(ArmServoInput);
    }

    public void ScoreServoInput() {
        // positions for the arm of the bot
        armServo.setPosition(ScoreServoInput);
    }
}
