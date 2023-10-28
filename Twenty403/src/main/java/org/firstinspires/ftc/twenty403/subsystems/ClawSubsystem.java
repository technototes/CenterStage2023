package org.firstinspires.ftc.twenty403.subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.control.PIDCoefficients;
import com.acmerobotics.roadrunner.control.PIDFController;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.technototes.library.hardware.motor.EncodedMotor;
import com.technototes.library.hardware.servo.Servo;
import com.technototes.library.logger.Loggable;
import com.technototes.library.subsystem.Subsystem;

import org.firstinspires.ftc.twenty403.Setup;

@Config
public class ClawSubsystem implements Subsystem, Loggable {

    // TODO: Create claw real values

    public static double OPEN_CLAW_POS = 0.1; //Tested as of 10/27
    public static double CLOSE_CLAW_POS = 0.4; //Tested as of 10/27
    public static double ARM_INTAKE = 0;
    public static double FIRST_LINE_SCORING = 1;
    public static double SECOND_LINE_SCORING = 1;
    public static double THIRD_LINE_SCORING = 1;
    public static PIDCoefficients PID = new PIDCoefficients(0.0027, 0.0, 0.00015);
    private PIDFController PidController;


    private Servo clawServo;
    private Servo elbowServo;
    private EncodedMotor<DcMotorEx> swingMotor;
    private boolean haveHardware;

    public ClawSubsystem(Servo claw, EncodedMotor<DcMotorEx> swing) {
        clawServo = claw;
        elbowServo = null;
        swingMotor = swing;
        haveHardware = true;
        PidController = new PIDFController(PID, 0, 0, 0, (x, y) -> 0.1);

    }

    public ClawSubsystem() {
        clawServo = null;
        elbowServo = null;
        swingMotor = null;
        haveHardware = false;
    }
    private int getLiftCurrentPosition() {
        if (Setup.Connected.CLAWSUBSYSTEM) {
            return (int) swingMotor.getSensorValue();
        } else {
            return 0;
        }
    }

    public void open() {
        setClawServo(OPEN_CLAW_POS);
    }

    public void close() {
        setClawServo(CLOSE_CLAW_POS);
    }

    public void intake() {
        setElbowServo(ARM_INTAKE);
    }

    public void firstLineScoring() {
        setElbowServo(FIRST_LINE_SCORING);
    }

    public void secondLineScoring() {
        setElbowServo(SECOND_LINE_SCORING);
    }

    public void thirdLineScoring() {
        setElbowServo(THIRD_LINE_SCORING);
    }


    //    @Override
    //    public void periodic(){
    //
    //    }

    private void setElbowServo(double e) {
        if (elbowServo != null) {
            elbowServo.setPosition(e);
        }
    }

    private void setClawServo(double c) {
        if (clawServo != null) {
            clawServo.setPosition(c);
        }
    }
}
