package org.firstinspires.ftc.twenty403.subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.technototes.library.hardware.servo.Servo;
import com.technototes.library.logger.Loggable;
import com.technototes.library.subsystem.Subsystem;
@Config
public class ClawSubsystem implements Subsystem, Loggable {
    public static double OPEN_CLAW_POS = 0;
    public static double CLOSE_CLAW_POS = 1;
    public static double ARM_INTAKE = 1;
    public static double FIRST_LINE_SCORING = 1;
    public static double SECOND_LINE_SCORING = 1;
    public static double THIRD_LINE_SCORING = 1;



    private Servo clawServo;
    private Servo elbowServo;
    private DcMotorEx swingMotor;
    private boolean haveHardware;


    public ClawSubsystem (Servo claw, Servo elbow, DcMotorEx swing){

    clawServo = claw;
    elbowServo = elbow;
    swingMotor = swing;
    haveHardware = true;
    }
    public ClawSubsystem (){
    clawServo = null;
    elbowServo = null;
    swingMotor = null;
    haveHardware = false;
    }
    public void open(){
        setClawServo(OPEN_CLAW_POS);
    }
    public void close(){
        setClawServo(CLOSE_CLAW_POS);
    }
    public void intake(){setElbowServo(ARM_INTAKE);}
    public void firstLineScoring(){setElbowServo(FIRST_LINE_SCORING);}
    public void secondLineScoring(){setElbowServo(SECOND_LINE_SCORING);}
    public void thirdLineScoring(){setElbowServo(THIRD_LINE_SCORING);}


//    @Override
//    public void periodic(){
//
//    }
    private void setElbowServo(double e){
        if (elbowServo != null){
            elbowServo.setPosition(e);
        }
    }
    private void setClawServo(double c) {
        if (clawServo != null ) {
            clawServo.setPosition(c);
        }
    }

}
