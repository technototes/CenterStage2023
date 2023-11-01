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
import org.firstinspires.ftc.twenty403.Setup;

@Config
public class ClawSubsystem implements Subsystem, Loggable {

    // TODO: Create claw real values

    public static double OPEN_CLAW_POS = 0.1; //Tested as of 10/27
    public static double CLOSE_CLAW_POS = 0.4; //Tested as of 10/27

    public static double ARM_INTAKE = 602;
    public static double MANUAL_STEP = 5;
    public static double FIRST_LINE_SCORING = 401;

    public static double NEUTERAL_ARM_POSITION = 0;
    public static double SECOND_LINE_SCORING = 1;
    public static double THIRD_LINE_SCORING = 1;

    public static double MIN_MOTOR_SPEED = -0.5;
    public static double MAX_MOTOR_SPEED = 0.5;
    @Log(name = "elbowPos")
    public int elbowPos;

    @Log(name = "elbowPow")
    public double elbowPow;

    @Log(name = "elbowTarget")
    public double targetPos;

    private Servo clawServo;
    private EncodedMotor<DcMotorEx> swingMotor;
    private boolean haveHardware;
    public static PIDCoefficients PID = new PIDCoefficients(0.0027, 0.0, 0.00015);
    private PIDFController swingPidController;

    public ClawSubsystem(Servo claw, EncodedMotor<DcMotorEx> swing) {
        clawServo = claw;
        swingMotor = swing;
        haveHardware = true;
        swingMotor = swing;
        haveHardware = true;
        swingPidController = new PIDFController(PID, 0, 0, 0, (x, y) -> 0.0);
    }

    public ClawSubsystem() {
        clawServo = null;
        swingMotor = null;
        haveHardware = false;
        swingPidController = new PIDFController(PID, 0, 0, 0, (x, y) -> 0.0);
    }

    private int getLiftCurrentPosition() {
        if (Setup.Connected.CLAWSUBSYSTEM) {
            return (int) swingMotor.getSensorValue();
        } else {
            return 0;
        }
    }

    public void open() {
        setClawPos(OPEN_CLAW_POS);
    }

    public void close() {
        setClawPos(CLOSE_CLAW_POS);
    }


    //add setZero method
    public void intake() {
        setElbowPos(ARM_INTAKE);
    }

    public void arm_increment() {
        double curPos = getSwingCurrentPosition();
        curPos += MANUAL_STEP;
        setElbowPos(curPos);
    }
    public void arm_decrement() {
        double curPos = getSwingCurrentPosition();
        curPos += MANUAL_STEP;
        setElbowPos(curPos);
    }

    public void firstLineScoring() {
        setElbowPos(FIRST_LINE_SCORING);
    }

    public void neutralArmPosition() { setElbowPos(NEUTERAL_ARM_POSITION);}

    /*
    public void secondLineScoring() {
        setElbowPos(SECOND_LINE_SCORING);
    }

    public void thirdLineScoring() {
        setElbowPos(THIRD_LINE_SCORING);
    }
    */

    @Override
    public void periodic() {
        int swingPos = getSwingCurrentPosition();
        double targetSpeed = swingPidController.update(swingPos);
        setSwingMotorPower(targetSpeed);
        elbowPos = swingPos;
        elbowPow = targetSpeed;
        //        setLiftPosition_OVERRIDE(
        //                leftPidController.getTargetPosition(),
        //                rightPidController.getTargetPosition()
        //        );

    }

    private void setElbowPos(double e) {
        swingPidController.setTargetPosition(e);
        targetPos = e;
    }

    private void setClawPos(double c) {
        if (clawServo != null) {
            clawServo.setPosition(c);
        }
    }

    private int getSwingCurrentPosition() {
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
