package org.firstinspires.ftc.twenty403.subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.technototes.library.hardware.motor.EncodedMotor;
import com.technototes.library.hardware.sensor.encoder.MotorEncoder;
import com.technototes.library.logger.Loggable;
import com.technototes.library.subsystem.Subsystem;

import org.firstinspires.ftc.twenty403.Hardware;

import java.util.ArrayList;
import java.util.List;

@Config
public class SafetySubsystem implements Subsystem, Loggable {

    public Hardware myHw;
    public int previousOdoFPosition = 0;
    public int previousOdoRPosition = 0;
    public int previousFLPosiiton = 0;
    public int previousFRPosiiton = 0;
    public int previousRLPosiiton = 0;
    public int previousRRPosiiton = 0;
    public static int OdoTickDiff = 10;
    public static double WheelTickDiff = 1.0;
    public List<EncodedMotor> wheelHw = new ArrayList<EncodedMotor>();
    public List<Double> previousWheelPos = new ArrayList<Double>();

    public boolean isOdoFailing(MotorEncoder odo, int previousOdoPosition){
        int currentOdoPosition = odo.getCurrentPosition();
        if ((previousOdoPosition + SafetySubsystem.OdoTickDiff) > currentOdoPosition) {
            return true;
        }
        else {
            return false;
        }
    }

    public boolean isWheelFailing(EncodedMotor wheel, int previousWheelPosition){
        double currentWheelPosition = wheel.getSensorValue();
        if ((previousWheelPosition + SafetySubsystem.WheelTickDiff) > currentWheelPosition) {
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public void periodic() {
        if (isOdoFailing(myHw.odoF, previousOdoFPosition) || isOdoFailing(myHw.odoR, previousOdoRPosition)){
         //stop auto

        }
        if (isWheelFailing(myHw.fl, previousFLPosiiton) || isWheelFailing(myHw.fr, previousFRPosiiton) || isWheelFailing(myHw.rl, previousRLPosiiton) || isWheelFailing(myHw.rr, previousRRPosiiton)) {
        // stop auto

        }
        for (int i = 0; i < 4; i++) {

        }



    }
    public SafetySubsystem(Hardware hw){
        myHw = hw;
        wheelHw.add(myHw.fl);
        wheelHw.add(myHw.fr);
        wheelHw.add(myHw.rl);
        wheelHw.add(myHw.rr);
        for (int i = 0; i < 4; i++) {
            previousWheelPos.add(0.0);
        }
    }


}
