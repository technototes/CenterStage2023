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
public class PlacementSubsystem implements Subsystem, Loggable {

    public static double INTAKE_SPEED = .3;
    public static double OUTPUT_SPEED = -.3;

    public static double ScoreServo = 0.5;

    public static double ArmServo = 0.5;
    public Servo armServo;

    public static double ScoreServoInput = 0.5;

    public static double ArmServoInput = 0.5;

    public static double ScoreServoOutput = 0.5;

    public static double ArmServoOutput = 0.5;

    public Servo scoreServo;
    public Motor<DcMotorEx> liftMotor;
    private boolean isHardware;

    public PlacementSubsystem(Hardware hw) {
        armServo = hw.Armservo;
        scoreServo = hw.ScoreServo;
        liftMotor = hw.liftMotor;
        isHardware = true;
    }

    public PlacementSubsystem() {
        isHardware = false;
        liftMotor = null;
        armServo = null;
        scoreServo = null;
    }

    public void armliftup() {
        // lift to take the intake system up or down

    }

    public void armliftdown() {
        // brings the intake system down

    }

    public void LiftHeight1() {
        //takes the arm to the first level
        liftMotor.setSpeed(0);
    }

    public void liftHeight2() {
        //takes the arm to the second level
        liftMotor.setSpeed(0);
    }

    public void LiftHeight3() {
        //takes the arm to the third level
        liftMotor.setSpeed(0);
    }

    public void placementReset() {
        //brings the arm all the way down
        liftMotor.setSpeed(0);
        armServo.setPosition(0);
        scoreServo.setPosition(0);
    }

    private void ArmServoOutput() {
        // the arm's position to score
        armServo.setPosition(ArmServoOutput);
    }

    private void ScoreServoOutput() {
        // the intake system's postion to score
        scoreServo.setPosition(ScoreServoOutput);
    }

    public void periodic() {
        //        double ltargetSpeed = leftPidController.update(getLeftPos());
        //        double lclippedSpeed = Range.clip(ltargetSpeed, MIN_MOTOR_SPEED, MAX_MOTOR_SPEED);
        //        double rtargetSpeed = rightPidController.update(getRightPos());
        //        double rclippedSpeed = Range.clip(rtargetSpeed, MIN_MOTOR_SPEED, MAX_MOTOR_SPEED);
        //
        //        //        double leftError = Math.abs(leftPidController.getTargetPosition() - getLeftPos());
        //        //        double rightError = Math.abs(rightPidController.getTargetPosition() - getRightPos());
        //        //        if (leftError > DEAD_ZONE || rightError > DEAD_ZONE) {
        //        //        }
        //        setMotorPower(lclippedSpeed, rclippedSpeed);
        //        setLiftPosition_OVERRIDE(
        //                leftPidController.getTargetPosition(),
        //                rightPidController.getTargetPosition()
        //        );

    }

    private void ArmServoInput() {
        // positions for the arm of the bot
        armServo.setPosition(ArmServoInput);
    }

    private void ScoreServoInput() {
        // positions for the arm of the bot
        armServo.setPosition(ScoreServoInput);
    }
}
