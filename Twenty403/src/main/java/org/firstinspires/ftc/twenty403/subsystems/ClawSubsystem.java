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
public class ClawSubsystem implements Subsystem, Loggable {

    public static double OPEN_CLAW_POS = 0.1; //Tested as of 10/27
    public static double CLOSE_CLAW_POS = 0.4; //Tested as of 10/27

    public static int ARM_INTAKE = 602;
    public static int MANUAL_STEP = 15;
    public static int FIRST_LINE_SCORING = 401;
    public static int NEUTRAL_ARM_POSITION = 0;
    public static int SECOND_LINE_SCORING = 350;
    public static int THIRD_LINE_SCORING = 300;

    public static double MIN_MOTOR_SPEED = -0.5;
    public static double MAX_MOTOR_SPEED = 0.5;

    @Log(name = "elbowPos")
    public int elbowPos;

    @Log(name = "elbowPow")
    public double elbowPow;

    @Log(name = "elbowTarget")
    public int targetPos;

    private Servo clawServo;
    private EncodedMotor<DcMotorEx> swingMotor;
    private boolean haveHardware;
    public static PIDCoefficients PID = new PIDCoefficients(0.0027, 0.0, 0.00015);
    private PIDFController swingPidController;
    public int armResetPos;

    public ClawSubsystem(Servo claw, EncodedMotor<DcMotorEx> swing) {
        clawServo = claw;
        swingMotor = swing;
        haveHardware = true;
        swingPidController = new PIDFController(PID, 0, 0, 0, (x, y) -> 0.0);
        resetArmNeutral();
    }

    public ClawSubsystem() {
        clawServo = null;
        swingMotor = null;
        haveHardware = false;
        swingPidController = new PIDFController(PID, 0, 0, 0, (x, y) -> 0.0);
        resetArmNeutral();
    }

    public void open() {
        setClawPos(OPEN_CLAW_POS);
    }

    public void close() {
        setClawPos(CLOSE_CLAW_POS);
    }

    public void resetArmNeutral() {
        armResetPos = getSwingUnmodifiedPosition();
        // We don't want the destination to go nuts, so update the target with the new zero
        targetPos = armResetPos;
    }

    public void intake() {
        setElbowPos(ARM_INTAKE);
    }

    public void arm_increment() {
        setElbowPos(targetPos + MANUAL_STEP);
    }

    public void arm_decrement() {
        setElbowPos(targetPos - MANUAL_STEP);
    }

    public void firstLineScoring() {
        setElbowPos(FIRST_LINE_SCORING);
    }

    public void neutralArmPosition() {
        setElbowPos(NEUTRAL_ARM_POSITION);
    }

    public void secondLineScoring() {
        setElbowPos(SECOND_LINE_SCORING);
    }

    public void thirdLineScoring() {
        setElbowPos(THIRD_LINE_SCORING);
    }

    @Override
    public void periodic() {
        elbowPos = getSwingCurrentPosition();
        elbowPow = swingPidController.update(elbowPos);
        setSwingMotorPower(elbowPow);
    }

    private void setElbowPos(int e) {
        swingPidController.setTargetPosition(e);
        targetPos = e;
    }

    private void setClawPos(double c) {
        if (clawServo != null) {
            clawServo.setPosition(c);
        }
    }

    private int getSwingCurrentPosition() {
        return getSwingUnmodifiedPosition() - armResetPos;
    }

    private int getSwingUnmodifiedPosition() {
        if (haveHardware) {
            return (int) swingMotor.getSensorValue();
        } else {
            return 0;
        }
    }

    private void setSwingMotorPower(double speed) {
        if (haveHardware) {
            double clippedSpeed = Range.clip(speed, MIN_MOTOR_SPEED, MAX_MOTOR_SPEED);
            swingMotor.setSpeed(clippedSpeed);
        }
    }
}
