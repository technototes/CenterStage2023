package org.firstinspires.ftc.sixteen750;

import static java.lang.Math.toRadians;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.technototes.path.geometry.ConfigurablePoseD;
import com.technototes.path.trajectorysequence.TrajectorySequence;
import com.technototes.path.trajectorysequence.TrajectorySequenceBuilder;

import java.util.function.Function;

public class AutoConstants {
    @Config
    public static class WingRed {
        // WING RED
        public static ConfigurablePoseD START = new ConfigurablePoseD(35, 60, -90);
        public static ConfigurablePoseD LEFT_SPIKE = new ConfigurablePoseD(45, 30, -60);
        public static ConfigurablePoseD MIDDLE_SPIKE = new ConfigurablePoseD(35, 32, -90);
        public static ConfigurablePoseD RIGHT_SPIKE =  new ConfigurablePoseD(23, 30, -120);

        public static ConfigurablePoseD MID_CLEAR =  new ConfigurablePoseD(36, 32, -180);
        public static ConfigurablePoseD RIGHT_SPIKE_2 =  new ConfigurablePoseD(25, 32, -180);


        // This is "clear of the pixels, ready to somewhere else
        public static ConfigurablePoseD CLEAR = new ConfigurablePoseD(35,60.1,-180);
        public static ConfigurablePoseD PARK_RIGHT = new ConfigurablePoseD(-60,60,-180);

        // These are 'trajectory pieces' which should be named like this:
        // {STARTING_POSITION}_TO_{ENDING_POSITION}
        public static final Function<Function<Pose2d, TrajectorySequenceBuilder>, TrajectorySequence>
                START_TO_LEFT_SPIKE = b ->
                //
                b.apply(START.toPose()).lineToLinearHeading(LEFT_SPIKE.toPose()).build();

        public static final Function<Function<Pose2d, TrajectorySequenceBuilder>, TrajectorySequence>
                START_TO_MIDDLE_SPIKE = b ->
                b.apply(START.toPose()).lineToLinearHeading(MIDDLE_SPIKE.toPose()).build();
        public static final Function<Function<Pose2d, TrajectorySequenceBuilder>, TrajectorySequence>
                START_TO_RIGHT_SPIKE = b ->
                    // SPLINE
                    b.apply(START.toPose()).splineTo(RIGHT_SPIKE.toVec(), RIGHT_SPIKE.getHeading()).build();
        public static final Function<Function<Pose2d, TrajectorySequenceBuilder>, TrajectorySequence>

                START_TO_MID_CLEAR = b ->
                b.apply(START.toPose()).lineToLinearHeading(MID_CLEAR.toPose()).build();
        public static final Function<Function<Pose2d, TrajectorySequenceBuilder>, TrajectorySequence>

                MID_CLEAR_TO_RIGHT_SPIKE2 = b ->
                b.apply(MID_CLEAR.toPose()).lineToLinearHeading(RIGHT_SPIKE_2.toPose()).build();
        public static final Function<Function<Pose2d, TrajectorySequenceBuilder>, TrajectorySequence>

                RIGHT_SPIKE_2_TO_MID_CLEAR = b ->
                //
                b.apply(RIGHT_SPIKE_2.toPose()).lineToLinearHeading(MID_CLEAR.toPose()).build();
        public static final Function<Function<Pose2d, TrajectorySequenceBuilder>, TrajectorySequence>

                MID_CLEAR_TO_CLEAR = b ->
                //
                b.apply(MID_CLEAR.toPose()).lineToLinearHeading(CLEAR.toPose()).build();
        public static final Function<Function<Pose2d, TrajectorySequenceBuilder>, TrajectorySequence>
                LEFT_SPIKE_TO_CLEAR = b ->
                    //
                    b.apply(LEFT_SPIKE.toPose()).lineToLinearHeading(CLEAR.toPose()).build();

        public static final Function<Function<Pose2d, TrajectorySequenceBuilder>, TrajectorySequence>
                MIDDLE_SPIKE_TO_CLEAR = b ->
                b.apply(MIDDLE_SPIKE.toPose()).lineToLinearHeading(CLEAR.toPose()).build();
        public static final Function<Function<Pose2d, TrajectorySequenceBuilder>, TrajectorySequence>
                RIGHT_SPIKE_TO_CLEAR = b ->
                    //SPLINE done
                    b.apply(RIGHT_SPIKE.toPose()).splineTo(CLEAR.toVec(), CLEAR.getHeading()).build();
        public static final Function<Function<Pose2d, TrajectorySequenceBuilder>, TrajectorySequence>
                CLEAR_TO_PARK_RIGHT = b ->
                b.apply(CLEAR.toPose()).lineToLinearHeading(PARK_RIGHT.toPose()).build();
    }
    @Config
    public static class WingBlue {
        public static ConfigurablePoseD START = new ConfigurablePoseD(35, -60, 90);
        public static ConfigurablePoseD LEFT_SPIKE = new ConfigurablePoseD(23, -30, 120);
        public static ConfigurablePoseD MIDDLE_SPIKE = new ConfigurablePoseD(35, -32, 90);
        public static ConfigurablePoseD RIGHT_SPIKE = new ConfigurablePoseD(46, -30, 60);
        // This is "clear of the pixels, ready to somewhere else
        public static ConfigurablePoseD CLEAR = new ConfigurablePoseD(35,-60.1,180);
        public static ConfigurablePoseD PARK_LEFT = new ConfigurablePoseD(-60,-60,180);
        // These are 'trajectory pieces' which should be named like this:
        // {STARTING_POSITION}_TO_{ENDING_POSITION}
        public static final Function<Function<Pose2d, TrajectorySequenceBuilder>, TrajectorySequence>
                START_TO_LEFT_SPIKE = b ->
                //spline done (spline to clear the metal)
                b.apply(START.toPose())
                        .splineTo(LEFT_SPIKE.toVec(), LEFT_SPIKE.getHeading()).build(),
            START_TO_MIDDLE_SPIKE = b ->
                b.apply(START.toPose()).lineToLinearHeading(MIDDLE_SPIKE.toPose()).build(),
            START_TO_RIGHT_SPIKE = b ->
                b.apply(START.toPose()).lineToLinearHeading(RIGHT_SPIKE.toPose()).build(),
            LEFT_SPIKE_TO_CLEAR = b ->
                    //spline done
                    b.apply(LEFT_SPIKE.toPose()).splineTo(CLEAR.toVec(), CLEAR.getHeading()).build(),
            MIDDLE_SPIKE_TO_CLEAR = b ->
                b.apply(MIDDLE_SPIKE.toPose()).lineToLinearHeading(CLEAR.toPose()).build(),
            RIGHT_SPIKE_TO_CLEAR = b ->
                b.apply(RIGHT_SPIKE.toPose()).lineToLinearHeading(CLEAR.toPose()).build(),
            CLEAR_TO_PARK_LEFT = b ->
                b.apply(CLEAR.toPose()).lineToLinearHeading(PARK_LEFT.toPose()).build();

    }


    @Config
    public static class StageRed {
        public static ConfigurablePoseD START = new ConfigurablePoseD(-12, 60, -90);
        public static ConfigurablePoseD LEFT_SPIKE = new ConfigurablePoseD(0, 30, -60);
        public static ConfigurablePoseD MIDDLE_SPIKE = new ConfigurablePoseD(-12, 25, -90);
        public static ConfigurablePoseD RIGHT_SPIKE = new ConfigurablePoseD(-23, 30, -120);
        // This is "clear of the pixels, ready to somewhere else
        public static ConfigurablePoseD CLEAR = new ConfigurablePoseD(-12,60.1,-180);
        public static ConfigurablePoseD PARK_RIGHT = new ConfigurablePoseD(-60,14,-180);

        // These are 'trajectory pieces' which should be named like this:
        // {STARTING_POSITION}_TO_{ENDING_POSITION}
        public static final Function<Function<Pose2d, TrajectorySequenceBuilder>, TrajectorySequence>
            START_TO_LEFT_SPIKE = b ->
                //spline
                b.apply(START.toPose())
                        .splineTo(LEFT_SPIKE.toVec(), LEFT_SPIKE.getHeading()).build(),
                START_TO_MIDDLE_SPIKE = b ->
                b.apply(START.toPose()).lineToLinearHeading(MIDDLE_SPIKE.toPose()).build(),
            START_TO_RIGHT_SPIKE = b ->
                b.apply(START.toPose()).lineToLinearHeading(RIGHT_SPIKE.toPose()).build(),
            LEFT_SPIKE_TO_CLEAR = b ->
                    //spline
                    b.apply(LEFT_SPIKE.toPose()).splineTo(CLEAR.toVec(), CLEAR.getHeading()).build(),
                MIDDLE_SPIKE_TO_CLEAR = b ->
                b.apply(MIDDLE_SPIKE.toPose()).lineToLinearHeading(CLEAR.toPose()).build(),
            RIGHT_SPIKE_TO_CLEAR = b ->
                b.apply(RIGHT_SPIKE.toPose()).lineToLinearHeading(CLEAR.toPose()).build(),
                CLEAR_TO_PARK_RIGHT = b ->
                        b.apply(CLEAR.toPose()).lineToLinearHeading(PARK_RIGHT.toPose()).build();
    }
    @Config
    public static class StageBlue {
        public static ConfigurablePoseD START = new ConfigurablePoseD(-12, -60, 90);
        public static ConfigurablePoseD LEFT_SPIKE = new ConfigurablePoseD(-23, -30, 120);
        public static ConfigurablePoseD MIDDLE_SPIKE = new ConfigurablePoseD(-12, -25, 90);
        public static ConfigurablePoseD RIGHT_SPIKE = new ConfigurablePoseD(0, -30, 60);
        // This is "clear of the pixels, ready to somewhere else
        public static ConfigurablePoseD CLEAR = new ConfigurablePoseD(-12,-60.1,180);
        public static ConfigurablePoseD PARK_RIGHT = new ConfigurablePoseD(-60,-12,0);


        // These are 'trajectory pieces' which should be named like this:
        // {STARTING_POSITION}_TO_{ENDING_POSITION}
        public static final Function<Function<Pose2d, TrajectorySequenceBuilder>, TrajectorySequence>
            START_TO_LEFT_SPIKE = b ->
                b.apply(START.toPose()).lineToLinearHeading(LEFT_SPIKE.toPose()).build(),
                START_TO_MIDDLE_SPIKE = b ->
                b.apply(START.toPose()).lineToLinearHeading(MIDDLE_SPIKE.toPose()).build(),
            START_TO_RIGHT_SPIKE = b ->
                    //SPLINE
                    b.apply(START.toPose()).splineTo(RIGHT_SPIKE.toVec(), RIGHT_SPIKE.getHeading()).build(),
            LEFT_SPIKE_TO_CLEAR = b ->

                    b.apply(LEFT_SPIKE.toPose()).lineToLinearHeading(CLEAR.toPose()).build(),
                MIDDLE_SPIKE_TO_CLEAR = b ->
                b.apply(MIDDLE_SPIKE.toPose()).lineToLinearHeading(CLEAR.toPose()).build(),
            RIGHT_SPIKE_TO_CLEAR = b ->
                    //SPLINE
                    b.apply(RIGHT_SPIKE.toPose()).splineTo(CLEAR.toVec(), CLEAR.getHeading()).build(),
            CLEAR_TO_PARK_RIGHT = b ->
                b.apply(CLEAR.toPose()).lineToLinearHeading(PARK_RIGHT.toPose()).build();
    }
}
