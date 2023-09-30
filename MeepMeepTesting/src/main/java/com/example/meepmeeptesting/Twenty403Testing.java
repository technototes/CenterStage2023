package com.example.meepmeeptesting;

import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;
import java.io.*;
import javax.imageio.ImageIO;

public class Twenty403Testing {

    public static void main(String[] args) {
        // Make this as large as possible while still fitting on our laptop screens:
        MeepMeep meepMeep = new MeepMeep(600);
        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
            .setDimensions(14, 14)
            // Constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, trackWidth
            // maxVel: The fastest dist/sec we'll travel (velocity)
            // maxAcc: The fastest rate (dist/sec/sec) we'll change our velocity (acceleration)
            // maxAngVel: the fastest degrees/sec we'll rotate (angular velocity)
            // maxAngAcc: the fastest rate (deg/sec/sec) we'll change our rotation (angular acceleration)
            // trackWidth: The width of our wheelbase (not clear what this really affects...)
            .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 9.5)
            .followTrajectorySequence(drive ->
                drive
                    .trajectorySequenceBuilder(AutoConstantsRed.Wing.START)
                    .addTrajectory(AutoConstantsRed.Wing.START_TO_LEFT_LOW.get())
                    //.addTrajectory(AutoConstantsRed.Away.START_TO_RIGHT_LOW.get())
                    .addTrajectory(AutoConstantsRed.Wing.LEFT_LOW_TO_BETWEEN_LEFT.get())
                    .addTrajectory(AutoConstantsRed.Wing.BETWEEN_TO_PARK_LEFT.get())
                        .addTrajectory(AutoConstantsRed.Wing.LEFT_LOW_TO_PARK_MIDDLE.get())

                        .build()
            );
        try {
            // Try to load the field image from the repo:
            meepMeep.setBackground(ImageIO.read(new File("Field.jpg")));
        } catch (IOException io) {
            // If we can't find the field image, fall back to the gray grid
            meepMeep.setBackground(MeepMeep.Background.GRID_GRAY);
        }
        meepMeep.setBackgroundAlpha(0.75f).addEntity(myBot).start();
    }
}
