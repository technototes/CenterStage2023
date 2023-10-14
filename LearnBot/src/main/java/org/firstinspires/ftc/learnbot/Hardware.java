package org.firstinspires.ftc.learnbot;

import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.technototes.library.hardware.motor.EncodedMotor;
import com.technototes.library.hardware.sensor.ColorDistanceSensor;
import com.technototes.library.hardware.sensor.IMU;
import com.technototes.library.hardware.sensor.Rev2MDistanceSensor;
import com.technototes.library.hardware.servo.Servo;
import com.technototes.library.logger.Loggable;
import java.util.List;
import org.firstinspires.ftc.robotcore.external.navigation.VoltageUnit;

public class Hardware implements Loggable {

    public List<LynxModule> hubs;

    public EncodedMotor<DcMotorEx> theMotor;
    public EncodedMotor<DcMotorEx> flMotor;
    public EncodedMotor<DcMotorEx> frMotor;
    public EncodedMotor<DcMotorEx> rlMotor;
    public EncodedMotor<DcMotorEx> rrMotor;
    public IMU imu;
    public Servo servo;
    public Rev2MDistanceSensor distanceSensor;
    public ColorDistanceSensor colorSensor;

    public DcMotorEx liftMotor;
    public Hardware(HardwareMap hwmap) {
        hubs = hwmap.getAll(LynxModule.class);
        if (Setup.Connected.DRIVEBASE) {
            this.frMotor = new EncodedMotor<>(Setup.HardwareNames.FRMOTOR);
            this.flMotor = new EncodedMotor<>(Setup.HardwareNames.FLMOTOR);
            this.rrMotor = new EncodedMotor<>(Setup.HardwareNames.RRMOTOR);
            this.rlMotor = new EncodedMotor<>(Setup.HardwareNames.RLMOTOR);
        }
        if (Setup.Connected.TESTSUBSYSTEM) {
            if (Setup.Connected.MOTOR) {
                this.theMotor = new EncodedMotor<>(Setup.HardwareNames.MOTOR);
            }
            if (Setup.Connected.SERVO) {
                this.servo = new Servo(Setup.HardwareNames.SERVO);
            }
            if (Setup.Connected.DISTANCE_SENSOR) {
                this.distanceSensor = new Rev2MDistanceSensor(Setup.HardwareNames.DISTANCE);
            }
            if (Setup.Connected.COLOR_SENSOR) {
                this.colorSensor = new ColorDistanceSensor(Setup.HardwareNames.COLOR);
            }
        }
        this.imu =
            new IMU(
                Setup.HardwareNames.IMU,
                RevHubOrientationOnRobot.LogoFacingDirection.UP,
                RevHubOrientationOnRobot.UsbFacingDirection.BACKWARD
            );
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
