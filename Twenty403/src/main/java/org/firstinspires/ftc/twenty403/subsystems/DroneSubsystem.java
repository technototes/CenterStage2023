package org.firstinspires.ftc.twenty403.subsystems;

import com.technototes.library.hardware.servo.Servo;
import com.technototes.library.logger.Loggable;
import com.technototes.library.subsystem.Subsystem;

public class DroneSubsystem implements Subsystem, Loggable {

    private Servo launchServo;
    private boolean haveHardware;
    public static double launch_pos;

    public DroneSubsystem(Servo l) {
        launchServo = l;
        haveHardware = true;
    }
    public DroneSubsystem(){
        launchServo = null;
        haveHardware = false;
    }
    public void launch() {
        setDroneServo(launch_pos);
    }

    //    @Override
    //    public void periodic(){
    //
    //    }

    private void setDroneServo(double c) {
        if (launchServo != null) {
            launchServo.setPosition(c);
        }
    
}
