package org.firstinspires.ftc.learnbot.subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.technototes.library.hardware.motor.EncodedMotor;
import com.technototes.library.hardware.sensor.Rev2MDistanceSensor;
import com.technototes.library.subsystem.Subsystem;
import org.firstinspires.ftc.learnbot.Hardware;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@Config
public class SpinnySubsystem implements Subsystem {

    public static int DISTANCE = 10; // centimeters!
    // Let's say thing this spins a motor between 0 and 3600 'ticks'
    // but only while/if the the distance is greater than 10cm
    private EncodedMotor<DcMotorEx> theMotor;
    private Rev2MDistanceSensor theSensor;
    private boolean running;
    private double curPower;

    public SpinnySubsystem(Hardware hw) {
        theMotor = hw.theMotor;
        theSensor = hw.distanceSensor;
        curPower = 0.0;
    }

    public void enableSpinning() {
        running = true;
        if (Math.abs(curPower) < 0.01) {
            curPower = .1;
        }
    }

    public void disableSpinning() {
        running = false;
    }

    @Override
    public void periodic() {
        if (running && shouldSpin()) {
            if (getTicks() > 3600) {
                windDown();
            } else if (getTicks() < 0) {
                windUp();
            } else {
                justGo();
            }
        } else {
            stopMotor();
        }
    }

    private boolean shouldSpin() {
        // We should only spin if we're more than 10 CM away, right?
        return (distance() < DISTANCE);
    }

    private void windDown() {
        if (curPower > -.5) {
            curPower = curPower - .05;
        }
        setPower(curPower);
    }

    private void windUp() {
        if (curPower < .5) {
            curPower = curPower + .05;
        }
        setPower(curPower);
    }

    private void justGo() {
        setPower(curPower);
    }

    private void stopMotor() {
        setPower(0);
    }

    /**
     * This stuff is all for hiding the actual hardware behind fake values, so we can run
     * code even if the hardware isn't connected
     */

    private void setPower(double d) {
        if (theMotor != null) {
            theMotor.setSpeed(d);
        }
    }

    private double getTicks() {
        if (theMotor == null) {
            // This is a fake value: Do whatever you want to 'test' your code
            return 1500;
        }
        // This reads the 'encoder' from the motor
        return theMotor.get();
    }

    private double distance() {
        if (theSensor == null) {
            // Fake value if we don't have hardware
            return 12;
        }
        // Read the value from the sensor
        return theSensor.getDistance(DistanceUnit.CM);
    }
}
