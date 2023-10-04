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
    public Servo leftservo;

    public Servo rightservo;
    public Motor<DcMotorEx> liftMotor;
    private boolean isHardware;

    public PlacementSubsystem(Hardware hw) {
        leftservo = hw.leftservo;
        rightservo = hw.rightservo;
        liftMotor = hw.liftMotor;
        isHardware = true;
    }

    public PlacementSubsystem() {
        isHardware = false;
        liftMotor = null;
        rightservo = null;
        leftservo = null; 
    }
    

    
  

    
        
        public void armliftup(){
        // lift to take the intake system up or down

        }

        public void rightpixel() {
            //servo to pick up a pixle from the lift
        }

        public void leftpixel() {
            //servo to place the pixel on the board
        }
    public void armliftdown(){
        // brings the intake system down

    }
    
    public void armHeight1(){
        //takes the arm to the first level
    }

    public void armHeight2(){
        //takes the arm to the second level
    }

    public void armHeight3(){
        //takes the arm to the third level
    }

    public void armReset(){
        //brings the arm all the way down

    }

    private void leftServoScore(){
        // positions the left servo to score
        leftservo.setPosition();
    }

    private void rightServoScore(){
        // positions the right servo to score
        rightservo.setPosition();

    }

}
