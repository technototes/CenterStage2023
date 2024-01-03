package org.firstinspires.ftc.twenty403.subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.CRServo;
import com.technototes.library.logger.Loggable;
import com.technototes.library.subsystem.Subsystem;

@Config
public class DroneSubsystem implements Subsystem, Loggable {

    private CRServo launchServo;
    private boolean haveHardware;
    public static double launch_pos = 0.3;

    public DroneSubsystem(CRServo l) {
        launchServo = l;
        haveHardware = true;
    }

    public DroneSubsystem() {
        launchServo = null;
        haveHardware = false;
    }

    public void launch() {
        activateDroneServo(launch_pos);
    }
    public void unlaunch() {
        deactivateDroneServo(launch_pos);
    }

    //    @Override
    //    public void periodic(){
    //
    //    }

    private void activateDroneServo(double c) {
        if (launchServo != null) {
            launchServo.setPower(c);
        }
    }
    private void deactivateDroneServo(double c) {
        if (launchServo != null) {
            launchServo.setPower(0);
        }
    }
}
