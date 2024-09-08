package org.firstinspires.ftc.protobot.commands;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.technototes.library.command.Command;
import com.technototes.library.control.Stick;
import com.technototes.library.logger.Loggable;
import com.technototes.library.util.MathUtils;
import java.util.function.DoubleSupplier;
import org.firstinspires.ftc.protobot.Setup;
import org.firstinspires.ftc.protobot.subsystems.DrivebaseSubsystem;

public class DriveCommand implements Command, Loggable {

    public DoubleSupplier driveStraighten;
    public DrivebaseSubsystem subsystem;
    public DoubleSupplier x, y, r;

    public DriveCommand(
        DrivebaseSubsystem sub,
        Stick stick1,
        Stick stick2,
        DoubleSupplier strtDrive
    ) {
        addRequirements(sub);
        subsystem = sub;
        x = stick1.getXSupplier();
        y = stick1.getYSupplier();
        r = stick2.getXSupplier();
        driveStraighten = strtDrive;
    }

    private double getRotation(double headingInRads) {
        // Check to see if we're trying to straighten the robot
        if (
            driveStraighten == null ||
            driveStraighten.getAsDouble() < DrivebaseSubsystem.DriveConstants.TRIGGER_THRESHOLD
        ) {
            // No straighten override: return the stick value
            // (with some adjustment...)
            return -Math.pow(r.getAsDouble(), 3) * subsystem.speed;
        } else {
            // headingInRads is [0-2pi]
            double heading = -Math.toDegrees(headingInRads);
            // Snap to the closest 90 or 270 degree angle (for going through the depot)
            double close = MathUtils.closestTo(heading, 0, 90, 180, 270, 360);
            double offBy = close - heading;
            // Normalize the error to -1 to 1
            double normalized = Math.max(Math.min(offBy / 45, 1.), -1.);
            // Dead zone of 5 degreesLiftHighJunctionCommand(liftSubsystem)
            if (Math.abs(normalized) < Setup.OtherSettings.STRAIGHTEN_DEAD_ZONE) {
                return 0.0;
            }
            // Scale it by the cube root, the scale that down by 30%
            // .9 (about 40 degrees off) provides .96 power => .288
            // .1 (about 5 degrees off) provides .46 power => .14
            return Math.cbrt(normalized) * 0.3;
        }
    }

    @Override
    public void execute() {
        if (!subsystem.isBusy()) {
            double curHeading = -subsystem.getExternalHeading();

            // The math & signs looks wonky, because this makes things field-relative
            // (Remember that "3 O'Clock" is zero degrees)
            double yvalue = -y.getAsDouble();
            double xvalue = -x.getAsDouble();
            if (driveStraighten != null) {
                if (driveStraighten.getAsDouble() > 0.7) {
                    if (Math.abs(yvalue) > Math.abs(xvalue)) xvalue = 0;
                    else yvalue = 0;
                }
            }
            Vector2d input = new Vector2d(
                yvalue * subsystem.speed,
                xvalue * subsystem.speed
            ).rotated(curHeading);

            subsystem.setWeightedDrivePower(
                new Pose2d(input.getX(), input.getY(), getRotation(curHeading))
            );
        }
        subsystem.update();
    }
}
