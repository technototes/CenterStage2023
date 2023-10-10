package org.firstinspires.ftc.twenty403.subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.technototes.library.hardware.servo.Servo;
import com.technototes.library.logger.Loggable;
import com.technototes.library.subsystem.Subsystem;
@Config
public class ClawSubsystem implements Subsystem, Loggable {
    public static double OPEN_CLAW_POS = 0;
    public static double CLOSE_CLAW_POS = 1;


    private Servo clawServo;
    private Servo elbowServo;
    private Servo shoulderServo;
    private boolean haveHardware;


    public ClawSubsystem (Servo claw, Servo elbow, Servo shoulder){
    clawServo = claw;
    elbowServo = elbow;
    shoulderServo = shoulder;
    haveHardware = true;
    }
    public ClawSubsystem (){
    clawServo = null;
    elbowServo = null;
    shoulderServo = null;
    haveHardware = false;
    }
    public void open(){}
}
