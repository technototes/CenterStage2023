package org.firstinspires.ftc.twenty403;

import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.technototes.library.hardware.motor.EncodedMotor;
import com.technototes.library.hardware.motor.Motor;
import com.technototes.library.hardware.sensor.IMU;
import com.technototes.library.hardware.servo.Servo;
import com.technototes.library.logger.Loggable;
import com.technototes.vision.hardware.Webcam;
import java.util.List;
import java.util.Set;

import org.firstinspires.ftc.robotcore.external.navigation.VoltageUnit;

public class Hardware implements Loggable {

    public List<LynxModule> hubs;

    public IMU imu;
    public EncodedMotor<DcMotorEx> fl, fr, rl, rr;
    public Webcam camera;
    public Servo clawServo;
    public Servo launchServo;
    public EncodedMotor<DcMotorEx> swingMotor;

    public Motor<DcMotorEx> hangMotor1;

    public CRServo hangServo1;

    /* Put other hardware here! */

    public Hardware(HardwareMap hwmap) {
        hubs = hwmap.getAll(LynxModule.class);
        imu =
            new IMU(
                Setup.HardwareNames.IMU,
                RevHubOrientationOnRobot.LogoFacingDirection.UP,
                RevHubOrientationOnRobot.UsbFacingDirection.BACKWARD
            );
        if (Setup.Connected.DRIVEBASE) {
            fl = new EncodedMotor<>(Setup.HardwareNames.FLMOTOR);
            fr = new EncodedMotor<>(Setup.HardwareNames.FRMOTOR);
            rl = new EncodedMotor<>(Setup.HardwareNames.RLMOTOR);
            rr = new EncodedMotor<>(Setup.HardwareNames.RRMOTOR);
        }
        if (Setup.Connected.WEBCAM) {
            camera = new Webcam(Setup.HardwareNames.CAMERA);
        }
        if (Setup.Connected.CLAWSUBSYSTEM) {
            clawServo = new Servo(Setup.HardwareNames.CLAWSERVO);
            swingMotor = new EncodedMotor<>(Setup.HardwareNames.SHOULDERMOTOR);
        }
        if (Setup.Connected.HANGSUBSYSTEM) {
            hangServo1 = hwmap.get(CRServo.class, Setup.HardwareNames.HANG_CRSERVO);
            hangMotor1 = new Motor<DcMotorEx>(Setup.HardwareNames.HANG_MOTOR);
        }
        if (Setup.Connected.DRONESUBSYSTEM) {
            launchServo = new Servo(Setup.HardwareNames.DRONESERVO);
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
