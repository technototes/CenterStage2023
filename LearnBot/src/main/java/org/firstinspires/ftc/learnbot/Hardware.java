package org.firstinspires.ftc.learnbot;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.hardware.rev.Rev2mDistanceSensor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.technototes.library.hardware.motor.EncodedMotor;
import com.technototes.library.hardware.sensor.ColorDistanceSensor;
import com.technototes.library.hardware.sensor.IMU;
import com.technototes.library.hardware.sensor.Rev2MDistanceSensor;
import com.technototes.library.hardware.servo.Servo;
import java.util.List;
import org.firstinspires.ftc.robotcore.external.navigation.VoltageUnit;

public class Hardware {

    @Config
    public static class Connected {

        public static boolean MOTOR = true;
        public static boolean SERVO = false;
        public static boolean DISTANCE_SENSOR = true;
        public static boolean COLOR_SENSOR = false;
    }

    @Config
    public static class HardwareNames {

        public static String MOTOR = "m";
        public static String SERVO = "s";
        public static String IMU = "imu";
        public static String DISTANCE = "d";
        public static String COLOR = "c";
    }

    public List<LynxModule> hubs;

    public EncodedMotor<DcMotorEx> theMotor;
    public IMU imu;
    public Servo servo;
    public Rev2MDistanceSensor distanceSensor;
    public ColorDistanceSensor colorSensor;

    public Hardware(HardwareMap hwmap) {
        hubs = hwmap.getAll(LynxModule.class);
        if (Connected.MOTOR) {
            this.theMotor = new EncodedMotor<>(HardwareNames.MOTOR);
        }
        if (Connected.SERVO) {
            this.servo = new Servo(HardwareNames.SERVO);
        }
        if (Connected.DISTANCE_SENSOR) {
            this.distanceSensor = new Rev2MDistanceSensor(HardwareNames.DISTANCE);
        }
        if (Connected.COLOR_SENSOR) {
            this.colorSensor = new ColorDistanceSensor(HardwareNames.COLOR);
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
