package com.example.meepmeeptesting;

import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Sixteen750Testing {
    /* // preexisting code from powerplay
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(750);
        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
            .setDimensions(18, 17)
            //Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
            .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 9.5)
            .followTrajectorySequence(drive ->
                drive
                    .trajectorySequenceBuilder(AutoConstantsBlue.Stage.START)
                    .addTrajectory(AutoConstantsBlue.Stage.START_TO_CENTER_SPIKE.get())
                    .build()
            );

        meepMeep
            .setBackground(MeepMeep.Background.FIELD_POWERPLAY_OFFICIAL)
            .setDarkMode(true)
            .setBackgroundAlpha(0.95f)
            .addEntity(myBot)
            .start();
    }
     */
    public static void main(String[] args) { //runs code for Wing Blue
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
                                .trajectorySequenceBuilder(AutoConstantsBlue.Wing.START)
                                //.addTrajectory(AutoConstantsRed.Stage.START_TO_LEFT_LOW.get())
                                .addTrajectory(AutoConstantsBlue.Wing.START_TO_CENTER_SPIKE.get())
                                .addTrajectory(AutoConstantsBlue.Wing.CENTER_SPIKE_TO_BACK.get())
                                .addTrajectory(AutoConstantsBlue.Wing.BACK_TO_PARK_CORNER.get())
                                //.addTrajectory(AutoConstantsRed.Stage.LEFT_SPIKE_TO_CENTER_SPIKE.get())
                                //.addTrajectory(AutoConstantsRed.Stage.CENTER_SPIKE_TO_RIGHT_SPIKE.get())
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
    /*
    public static void main(String[] args) { //runs code for Stage Blue
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
                                .trajectorySequenceBuilder(AutoConstantsBlue.Stage.START)
                                //.addTrajectory(AutoConstantsRed.Stage.START_TO_LEFT_LOW.get())
                                .addTrajectory(AutoConstantsBlue.Stage.START_TO_CENTER_SPIKE.get())
                                .addTrajectory(AutoConstantsBlue.Stage.CENTER_SPIKE_TO_START.get())
                                .addTrajectory(AutoConstantsBlue.Stage.START_TO_PARK_CENTER.get())
                                //.addTrajectory(AutoConstantsRed.Stage.LEFT_SPIKE_TO_CENTER_SPIKE.get())
                                //.addTrajectory(AutoConstantsRed.Stage.CENTER_SPIKE_TO_RIGHT_SPIKE.get())
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
    public static void othermain(String[] args) {
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
                                .trajectorySequenceBuilder(AutoConstantsRed.WingRed.START)
                                .addTrajectory(AutoConstants.WingRed.START_TO_MIDDLE_SPIKE.get())
                                //.addTrajectory(AutoConstantsRed.Away.START_TO_RIGHT_LOW.get())
                                .addTrajectory(AutoConstants.WingRed.FORWARD_PUSH_TO_START.get())
                                .addTrajectory(AutoConstants.WingRed.START2_TO_PARK_LEFT.get())
                                // .addTrajectory(AutoConstantsRed.Wing.PARK_LEFT_TO_START3.get())

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
     */
}
