package org.firstinspires.ftc.sixteen750.subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.technototes.library.hardware.servo.Servo;
import com.technototes.library.logger.Log;
import com.technototes.library.logger.Loggable;
import com.technototes.library.subsystem.Subsystem;
import org.firstinspires.ftc.sixteen750.Hardware;

@Config
public class DroneSubsystem implements Subsystem, Loggable {

    private Servo DroneServo;
    private boolean isHardware;

    @Log(name = "LAUNCH")
    public static double LAUNCH = 0;

    @Log(name = "START")
    public static double START = 1;

    //     public static double INTAKE = 0;
    public DroneSubsystem(Hardware hw) {
        DroneServo = hw.DroneServo;
        isHardware = true;
    }

    public DroneSubsystem() {
        DroneServo = null;
        isHardware = false;
    }

    public void Launch() {
        DroneServo.setPosition(LAUNCH);
    }

    public void Start() {
        DroneServo.setPosition(START);
    }
    //    public void Reset() { DroneServo.setPosition(INTAKE);}
}
