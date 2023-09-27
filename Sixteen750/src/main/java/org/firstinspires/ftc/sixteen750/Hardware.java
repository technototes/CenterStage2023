package org.firstinspires.ftc.sixteen750;

import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.technototes.library.hardware.motor.Motor;
import com.technototes.library.hardware.sensor.IMU;
import com.technototes.library.logger.Loggable;
import java.util.List;
import org.firstinspires.ftc.robotcore.external.navigation.VoltageUnit;

public class Hardware implements Loggable {

    public List<LynxModule> hubs;

    public IMU imu;
    public Motor<DcMotorEx> leftIntake;
    public Motor<DcMotorEx> rightIntake;

    /* TODO: Put other other here! */

    public Hardware(HardwareMap hwmap) {
        hubs = hwmap.getAll(LynxModule.class);
        imu =
            new IMU(
                Setup.HardwareNames.IMU,
                RevHubOrientationOnRobot.LogoFacingDirection.BACKWARD,
                RevHubOrientationOnRobot.UsbFacingDirection.LEFT
            );
        leftIntake = new Motor<DcMotorEx>(Setup.HardwareNames.INTAKELEFT);
        rightIntake = new Motor<DcMotorEx>(Setup.HardwareNames.INTAKERIGHT);

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
