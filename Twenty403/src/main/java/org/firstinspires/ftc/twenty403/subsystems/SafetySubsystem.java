package org.firstinspires.ftc.twenty403.subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.technototes.library.command.CommandScheduler;
import com.technototes.library.hardware.motor.EncodedMotor;
import com.technototes.library.hardware.sensor.encoder.MotorEncoder;
import com.technototes.library.logger.Log;
import com.technototes.library.logger.Loggable;
import com.technototes.library.subsystem.Subsystem;

import org.firstinspires.ftc.twenty403.Hardware;

import java.util.ArrayList;
import java.util.List;

@Config
public class SafetySubsystem implements Subsystem, Loggable {

    public Hardware myHw;
    @Log (name = "prevOdoF")
    public int previousOdoFPosition = 0;
    public int previousOdoRPosition = 0;
    @Log (name = "prevFLPos")
    public double previousFLPosiiton = 0;
    public double previousFRPosiiton = 0;
    public double previousRLPosiiton = 0;
    public double previousRRPosiiton = 0;
    public static int OdoTickDiff = 10;
    public static double WheelTickDiff = 1.0;
    @Log (name="monitoringEnabled")
    public boolean monitoringEnabled = false;
    private boolean DistanceSensor1 = false;
    public boolean DistanceSensor2 = false;
    private int numFailed = 0;
    @Log
    public String stopAutoReason = "not stopping";
    public static int MaxFail = 1000000;

    public boolean isOdoFailing(int currentOdoPosition, int previousOdoPosition){
        if (Math.abs(previousOdoPosition - currentOdoPosition) < SafetySubsystem.OdoTickDiff) {
            return true;
        }
        //new math: previous - current = absolute value (result) and result should be > odotickdiff
        else {
            return false;
        }
    }

    public boolean isWheelFailing(double currentWheelPosition, double previousWheelPosition){
        if (Math.abs(previousWheelPosition - currentWheelPosition) < SafetySubsystem.WheelTickDiff) {
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public void periodic() {
        if (monitoringEnabled ==  false) {
            return;
        }
        int odoFCurrentPosition = myHw.odoF.getCurrentPosition();
        int odoRCurrentPosition = myHw.odoR.getCurrentPosition();

        boolean stopAutoFlag = false;
        String stopAutoReason = "";
        if (isOdoFailing(odoFCurrentPosition, previousOdoFPosition)) {
            stopAutoFlag = true;
            stopAutoReason += "OdoF not reading;";
        }
        if (isOdoFailing(odoRCurrentPosition, previousOdoRPosition)) {
            stopAutoFlag = true;
            stopAutoReason += "OdoR not reading;";
        }

        this.previousOdoFPosition = odoFCurrentPosition;
        previousOdoRPosition = odoRCurrentPosition;

        double wheelflCurrentPosition = myHw.fl.getSensorValue();
        double wheelfrCurrentPosition = myHw.fr.getSensorValue();
        double wheelrlCurrentPosition = myHw.rl.getSensorValue();
        double wheelrrCurrentPosition = myHw.rr.getSensorValue();
        if (isWheelFailing(wheelflCurrentPosition, previousFLPosiiton) || isWheelFailing(wheelfrCurrentPosition, previousFRPosiiton) ||
                isWheelFailing(wheelrlCurrentPosition, previousRLPosiiton) || isWheelFailing(wheelrrCurrentPosition, previousRRPosiiton)) {
            stopAuto("wheels not reading");
            return;
        }
        this.previousFLPosiiton = wheelfrCurrentPosition;
        previousFRPosiiton = wheelfrCurrentPosition;
        previousRLPosiiton = wheelrlCurrentPosition;
        previousRRPosiiton = wheelrrCurrentPosition;

        if (stopAutoFlag == true) {
            stopAuto(stopAutoReason);
        }
        numFailed = 0;





    }
    public SafetySubsystem(Hardware hw){
        myHw = hw;
        CommandScheduler.getInstance().register(this);
    }


    private void stopAuto(String reason) {
        if (monitoringEnabled == true) {
            if (numFailed >= MaxFail) {
                stopAutoReason = reason;
                CommandScheduler.getInstance().terminateOpMode();

            }
            else {
                numFailed += 1;
            }
        }
    }

    public void startMonitoring() {
        monitoringEnabled = true;
    }
    public void stopMonitoring() {
        monitoringEnabled = false;
    }
    public void distanceSensor1bad() {
        DistanceSensor1 = false;
    }
    public void distanceSensor2bad() {
        DistanceSensor2 = false;
    }
    /*
    public distanceSensorBad() {
        if (monitoringEnabled == true)
            if (distanceSensor1bad() || distanceSensor2bad());
            CommandScheduler.getInstance().terminateOpMode();

    } */
}

// start monitoring after robot starts moving (wait a little bit after start, then start monitoring)
//in auto there is this thingy where it says opmodestate.RUN and idk if there is a subsystem version of that
//im now looking for that in a subsystem UPDATE: idk where
// stop monitoring if we know that distance sensors wil get close to walls
// stop monitoring if auto is going to a stop to go to next sequential command
// start monitoring again after distance sensors are far away and if next sequential auto command is starting again
// need to look at IMU to determine wonkiness

