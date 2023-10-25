package com.technototes.path.subsystem;

import static com.technototes.path.subsystem.DeadWheelConstants.*;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.localization.ThreeTrackingWheelLocalizer;
import com.acmerobotics.roadrunner.localization.TwoTrackingWheelLocalizer;
import com.technototes.library.hardware.sensor.IMU;
import com.technototes.library.hardware.sensor.encoder.MotorEncoder;
import com.technototes.library.subsystem.Subsystem;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Arrays;
import java.util.List;

/*
 * Sample tracking wheel localizer implementation assuming the standard configuration:
 *
 *    /--------------\
 *    |     ____     |
 *    |     ----     |
 *    | ||        || |
 *    | ||        || |
 *    |              |
 *    |              |
 *    \--------------/
 *
 */
public class TwoDeadWheelLocalizer extends TwoTrackingWheelLocalizer implements Subsystem {
@Config
    public abstract static class OdoDeadWheelConstants implements DeadWheelConstants{
        public static double LateralDistance = 152.4;//mm

        public static double ForwardOffset = 63.5;// mm

        public static double EncoderOverflow;

        public static double GearRatio = 1;

        public static double TicksPerRev = 8192; // Might be 2048

        public static double WheelRadius = 17.5; // millimeters?
    }

    protected MotorEncoder /*leftEncoder,*/ rightEncoder, frontEncoder;
    protected double lateralDistance, forwardOffset, gearRatio, wheelRadius, ticksPerRev;
    protected IMU imu;

    protected boolean encoderOverflow;

    public TwoDeadWheelLocalizer(/*MotorEncoder l*/ MotorEncoder r, MotorEncoder f, IMU i, DeadWheelConstants constants) {
        super(
                Arrays.asList(
                        //new Pose2d(0, constants.getDouble(LateralDistance.class) / 2, 0), // left
                        new Pose2d(0, -constants.getDouble(LateralDistance.class) / 2, 0), // right
                        new Pose2d(constants.getDouble(ForwardOffset.class), 0, Math.toRadians(90)) // front
                )
        );
        //leftEncoder = l;
        rightEncoder = r;
        frontEncoder = f;

        imu = i;
        lateralDistance = constants.getDouble(LateralDistance.class);
        forwardOffset = constants.getDouble(ForwardOffset.class);
        encoderOverflow = constants.getBoolean(EncoderOverflow.class);
        gearRatio = constants.getDouble(GearRatio.class);
        ticksPerRev = constants.getDouble(TicksPerRev.class);
        wheelRadius = constants.getDouble(WheelRadius.class);
    }

    public double encoderTicksToInches(double ticks) {
        return ((getWheelRadius() * 2 * Math.PI * getGearRatio() * ticks) / getTicksPerRev());
    }

    @NonNull
    @Override
    public List<Double> getWheelPositions() {
        return Arrays.asList(
                //encoderTicksToInches(leftEncoder.getCurrentPosition()),
                encoderTicksToInches(rightEncoder.getCurrentPosition()),
                encoderTicksToInches(frontEncoder.getCurrentPosition())
        );
    }

    @NonNull
    @Override
    public List<Double> getWheelVelocities() {
        // TODO: If your encoder velocity can exceed 32767 counts / second (such as the REV Through Bore and other
        //  competing magnetic encoders), change Encoder.getRawVelocity() to Encoder.getCorrectedVelocity() to enable a
        //  compensation method

        return Arrays.asList(
                //encoderTicksToInches(leftEncoder.getCorrectedVelocity()),
                encoderTicksToInches(rightEncoder.getCorrectedVelocity()),
                encoderTicksToInches(frontEncoder.getCorrectedVelocity())
        );
    }

    public double getTicksPerRev() {
        return ticksPerRev;
    }

    public double getWheelRadius() {
        return wheelRadius;
    }

    public double getGearRatio() {
        return gearRatio;
    }

    @Override
    public double getHeading() {
        return imu.gyroHeadingInRadians();
    }
}
