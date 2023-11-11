package org.firstinspires.ftc.twenty403.subsystems;

import androidx.annotation.NonNull;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.localization.TwoTrackingWheelLocalizer;
import com.technototes.library.hardware.sensor.IMU;
import com.technototes.library.hardware.sensor.encoder.MotorEncoder;
import com.technototes.library.logger.Log;
import com.technototes.library.logger.Loggable;
import com.technototes.library.subsystem.Subsystem;
import com.technototes.path.subsystem.DeadWheelConstants;
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
public class TwoDeadWheelLocalizer
    extends TwoTrackingWheelLocalizer
    implements Subsystem, Loggable {

    @Config
    public abstract static class OdoDeadWheelConstants implements DeadWheelConstants {

        public static double LateralDistance = 152.4; //mm

        public static double ForwardOffset = 63.5; // mm

        // No idea what this is getting at
        public static boolean EncoderOverflow = false;

        // This may be wrong
        public static double GearRatio = 1;

        public static double TicksPerRev = 8192; // Might be 2048

        public static double WheelRadius = 17.5 / 25.4; // millimeters -> inches

        public static double perpAngle = 180;
        public static double paraAngle = 90;
    }

    public static double GEAR_RATIO = 1; // output (wheel) speed / input (encoder) speed

    public static double PARALLEL_X = -3.5; // X is the up and down direction
    public static double PARALLEL_Y = 2; // Y is the strafe direction

    public static double PERPENDICULAR_X = 8;
    public static double PERPENDICULAR_Y = -3;

    @Log(name = "parOdo")
    public MotorEncoder /*leftEncoder,*/parallelEncoder;

    @Log(name = "perpOdo")
    public MotorEncoder perpendicularEncoder;

    protected double lateralDistance, forwardOffset, gearRatio, wheelRadius, ticksPerRev;
    protected IMU imu;
    //1862.5 per inch
    protected boolean encoderOverflow;

    public TwoDeadWheelLocalizer(/*MotorEncoder l*/MotorEncoder r, MotorEncoder f, IMU i) {
        super(
            Arrays.asList(
                //new Pose2d(0, constants.getDouble(LateralDistance.class) / 2, 0), // left
                new Pose2d(PARALLEL_X, PARALLEL_Y, Math.toRadians(OdoDeadWheelConstants.paraAngle)), // right
                new Pose2d(
                    PERPENDICULAR_X,
                    PERPENDICULAR_Y,
                    Math.toRadians(OdoDeadWheelConstants.perpAngle)
                ) // front
            )
        );
        //leftEncoder = l;
        parallelEncoder = r;
        perpendicularEncoder = f;

        imu = i;
        lateralDistance = OdoDeadWheelConstants.LateralDistance;
        forwardOffset = OdoDeadWheelConstants.ForwardOffset;
        encoderOverflow = OdoDeadWheelConstants.EncoderOverflow;
        gearRatio = OdoDeadWheelConstants.GearRatio;
        ticksPerRev = OdoDeadWheelConstants.TicksPerRev;
        wheelRadius = OdoDeadWheelConstants.WheelRadius;
    }

    public double encoderTicksToInches(double ticks) {
        return ((getWheelRadius() * 2 * Math.PI * getGearRatio() * ticks) / getTicksPerRev());
    }

    @NonNull
    @Override
    public List<Double> getWheelPositions() {
        return Arrays.asList(
            //encoderTicksToInches(leftEncoder.getCurrentPosition()),
            encoderTicksToInches(parallelEncoder.getCurrentPosition()),
            encoderTicksToInches(perpendicularEncoder.getCurrentPosition())
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
            encoderTicksToInches(parallelEncoder.getCorrectedVelocity()),
            encoderTicksToInches(perpendicularEncoder.getCorrectedVelocity())
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
