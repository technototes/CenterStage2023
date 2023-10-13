package org.firstinspires.ftc.sixteen750;

import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.technototes.library.hardware.motor.EncodedMotor;
import com.technototes.library.hardware.motor.Motor;
import com.technototes.library.hardware.sensor.IMU;
import com.technototes.library.hardware.servo.Servo;
import com.technototes.library.logger.Loggable;
import com.technototes.vision.hardware.Webcam;
import java.util.List;
import org.firstinspires.ftc.robotcore.external.navigation.VoltageUnit;

public class Hardware implements Loggable {

    public List<LynxModule> hubs;

    public IMU imu;
    public EncodedMotor<DcMotorEx> fl, fr, rl, rr;
    public Webcam camera;

    /* Put other hardware here! */

    // public Motor<DcMotorEx> leftIntake;
    // public Motor<DcMotorEx> rightIntake;

    public Motor<DcMotorEx> liftMotor;

    public Servo Armservo;

    public Servo ScoreServo;

    public Motor<DcMotorEx> hangMotor1;

    public Servo hangServo1;

    public Servo hangServo2;

    public Hardware(HardwareMap hwmap) {
        hubs = hwmap.getAll(LynxModule.class);
        imu =
            new IMU(
                Setup.HardwareNames.IMU,
                RevHubOrientationOnRobot.LogoFacingDirection.UP,
                RevHubOrientationOnRobot.UsbFacingDirection.FORWARD
            );
        //leftIntake = new Motor<DcMotorEx>(Setup.HardwareNames.INTAKELEFT);
        //rightIntake = new Motor<DcMotorEx>(Setup.HardwareNames.INTAKERIGHT);
        fl = new EncodedMotor<DcMotorEx>(Setup.HardwareNames.FLMOTOR);
        fr = new EncodedMotor<DcMotorEx>(Setup.HardwareNames.FRMOTOR);
        rl = new EncodedMotor<DcMotorEx>(Setup.HardwareNames.RLMOTOR);
        rr = new EncodedMotor<DcMotorEx>(Setup.HardwareNames.RRMOTOR);
        if (Setup.Connected.WEBCAM) {
            camera = new Webcam(Setup.HardwareNames.CAMERA);
        }
    }

    // We can read the voltage from the different hubs for fun...
    public double voltage() {
        double volt = 0;
        double count = 0;
        for (LynxModule lm : hubs) {
            count += 1;
            volt += lm.getInputVoltage(VoltageUnit.VOLTS);
        }
        return volt / count;
    }
}
