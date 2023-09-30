package org.firstinspires.ftc.learnbot.subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.technototes.library.hardware.motor.EncodedMotor;
import com.technototes.library.hardware.sensor.Rev2MDistanceSensor;
import com.technototes.library.hardware.servo.Servo;
import com.technototes.library.logger.Log;
import com.technototes.library.logger.Loggable;
import com.technototes.library.subsystem.Subsystem;
import org.firstinspires.ftc.learnbot.Hardware;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@Config
public class TestSubsystem implements Subsystem, Loggable {

    public static int DISTANCE = 10; // centimeters!

    public static double STARTING_POWER = 0.2;

    public static double HIGH_TICKS = 3600;
    public static double LOW_TICKS = 0;
    public static double DEAD_POWER = 0.01;
    public static double POWER_STEP = 0.05;
    public static double LOW_POWER = -0.7;
    public static double HIGH_POWER = 0.7;
    // Let's say thing this spins a motor between 0 and 3600 'ticks'
    // but only while/if the the distance is greater than 10cm
    private EncodedMotor<DcMotorEx> theMotor;

    private Servo servo;

    private Rev2MDistanceSensor theSensor;
    private boolean running;
    private double curPower;
    private double zeroTicks;

    @Log(name = "Power")
    public volatile double power = 0.0;

    @Log(name = "Ticks")
    public volatile double ticks = 0.0;

    public TestSubsystem(Hardware hw) {
        theMotor = hw.theMotor;
        curPower = 0.0;
        zeroTicks = 0.0;
        servo = hw.servo;
        resetTicks();
    }

    public void servoLeft() {
        running = true;
        setPosition(0.5);
    }

    public void servoRight() {
        running = true;
        setPosition(0.7);
    }

    public void forwardSpinning() {
        running = true;
        setPower(1.0);
    }

    public void backwardSpinning() {
        running = true;
        setPower(-1.0);
    }

    public void stopSpinning() {
        running = true;
        setPower(0);
    }

    public void resetPosition() {
        resetTicks();
    }

    @Override
    public void periodic() {
        getTicks();
    }

    /**
     * This stuff is all for hiding the actual hardware behind fake values, so we can run
     * code even if the hardware isn't connected
     */

    private void setPower(double d) {
        if (theMotor != null) {
            theMotor.setSpeed(d);
            power = d;
        }
    }

    private void setPosition(double d) {
        if (servo != null) {
            servo.setPosition(d);
        }
    }

    private void resetTicks() {
        if (theMotor == null) {
            zeroTicks = 0.0;
        } else {
            zeroTicks = theMotor.get();
        }
    }

    private double getTicks() {
        if (theMotor == null) {
            // This is a fake value: Do whatever you want to 'test' your code
            return 1500;
        }
        ticks = theMotor.get();
        // This reads the 'encoder' from the motor, but it's offset from our reset value
        return ticks - zeroTicks;
    }
}
