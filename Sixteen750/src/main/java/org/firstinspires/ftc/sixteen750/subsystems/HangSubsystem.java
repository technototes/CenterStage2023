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
public class HangSubsystem implements Subsystem, Loggable {
      //needs 2 servos and needs 1 motor. change the names of the variable later.

    public static Motor<DcMotorEx> hangMotor1;

    public static Servo hangServo1;

    public static Servo hangServo2;

    private boolean isHardware;

    public HangSubsystem(Hardware hw){

        hangServo1 = hw.hangServo1;
        hangServo2 = hw.hangServo2;
        hangMotor1 = hw.hangMotor1;
        isHardware = true;

    }

    public HangSubsystem(){

        hangServo1 = null;
        hangServo2 = null;
        hangMotor1 = null;
        isHardware = false;

    }





}
