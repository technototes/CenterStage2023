package org.firstinspires.ftc.sixteen750;

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
        public static ConfigurablePoseD MID_CLEAR =  new ConfigurablePoseD(36, 32, -180);
        public static ConfigurablePoseD RIGHT_SPIKE =  new ConfigurablePoseD(25, 35, -180);
        public static ConfigurablePoseD TELESTART = new ConfigurablePoseD(0,0,90);

        // This is "clear of the pixels, ready to somewhere else
        public static ConfigurablePoseD CLEAR = new ConfigurablePoseD(35,60.1,-180);
        public static ConfigurablePoseD PARK_CORNER = new ConfigurablePoseD(-60,47,-180);
        public static ConfigurablePoseD FORWARD = new ConfigurablePoseD(3,1,-180);
        public static ConfigurablePoseD BACKWARD = new ConfigurablePoseD(47, 0, -180);
        public static ConfigurablePoseD SIDE_RIGHT = new ConfigurablePoseD(47,48,-180);
        public static ConfigurablePoseD SIDE_LEFT = new ConfigurablePoseD(47,0,-180);


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

                MID_CLEAR_TO_RIGHT_SPIKE = b ->
                b.apply(MID_CLEAR.toPose()).lineToLinearHeading(RIGHT_SPIKE.toPose()).build();
        public static final Function<Function<Pose2d, TrajectorySequenceBuilder>, TrajectorySequence>

                RIGHT_SPIKE_TO_MID_CLEAR = b ->
                //
                b.apply(RIGHT_SPIKE.toPose()).lineToLinearHeading(MID_CLEAR.toPose()).build();
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
                CLEAR_TO_PARK_CORNER = b ->
                b.apply(CLEAR.toPose()).lineToLinearHeading(PARK_CORNER.toPose()).build();
        public static final Function<Function<Pose2d, TrajectorySequenceBuilder>, TrajectorySequence>
                BACKWARD_TO_FORWARD = b ->
                b.apply(BACKWARD.toPose()).lineToLinearHeading(FORWARD.toPose()).build();
        public static final Function<Function<Pose2d, TrajectorySequenceBuilder>, TrajectorySequence>
                FORWARD_TO_BACKWARD = b ->
                b.apply(FORWARD.toPose()).lineToLinearHeading(BACKWARD.toPose()).build();
        public static final Function<Function<Pose2d, TrajectorySequenceBuilder>, TrajectorySequence>
                SIDE_LEFT_TO_SIDE_RIGHT = b ->
                b.apply(SIDE_LEFT.toPose()).lineToLinearHeading(SIDE_RIGHT.toPose()).build();
        public static final Function<Function<Pose2d, TrajectorySequenceBuilder>, TrajectorySequence>
                SIDE_RIGHT_TO_SIDE_LEFT = b ->
                b.apply(SIDE_RIGHT.toPose()).lineToLinearHeading(SIDE_LEFT.toPose()).build();
    }
    @Config
    public static class WingBlue {
        public static ConfigurablePoseD START = new ConfigurablePoseD(35, -60, 90);
        public static ConfigurablePoseD LEFT_SPIKE = new ConfigurablePoseD(23, -30, 180);
        public static ConfigurablePoseD MIDDLE_SPIKE = new ConfigurablePoseD(35, -32, 90);
        public static ConfigurablePoseD RIGHT_SPIKE = new ConfigurablePoseD(46, -30, 60);
        public static ConfigurablePoseD MID_CLEAR =  new ConfigurablePoseD(36, -32, 180);

        // This is "clear of the pixels, ready to somewhere else
        public static ConfigurablePoseD CLEAR = new ConfigurablePoseD(35,-60.1,180);
        public static ConfigurablePoseD PARK_CORNER = new ConfigurablePoseD(-60,-60,180);
        // These are 'trajectory pieces' which should be named like this:
        // {STARTING_POSITION}_TO_{ENDING_POSITION}
        public static final Function<Function<Pose2d, TrajectorySequenceBuilder>, TrajectorySequence>
                START_TO_MID_CLEAR = b ->
                //spline done (spline to clear the metal)
                b.apply(START.toPose()).lineToLinearHeading(MID_CLEAR.toPose()).build(),
                MID_CLEAR_TO_LEFT_SPIKE = b ->
                        b.apply(MID_CLEAR.toPose()).lineToLinearHeading(RIGHT_SPIKE.toPose()).build(),
                LEFT_SPIKE_TO_MID_CLEAR = b ->
                        //
                        b.apply(RIGHT_SPIKE.toPose()).lineToLinearHeading(MID_CLEAR.toPose()).build(),
                MID_CLEAR_TO_CLEAR = b ->
                        //
                        b.apply(MID_CLEAR.toPose()).lineToLinearHeading(CLEAR.toPose()).build(),
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

            CLEAR_TO_PARK_CORNER = b ->
                b.apply(CLEAR.toPose()).lineToLinearHeading(PARK_CORNER.toPose()).build();

    }


    @Config
    public static class StageRed {
        public static ConfigurablePoseD START = new ConfigurablePoseD(-12, 60, -90);
        //left spike next to metal
        public static ConfigurablePoseD LEFT_SPIKE = new ConfigurablePoseD(0, 30, 0);
        public static ConfigurablePoseD MIDDLE_SPIKE = new ConfigurablePoseD(-12, 25, -90);
        public static ConfigurablePoseD RIGHT_SPIKE = new ConfigurablePoseD(-23, 30, -120);
        // This is "clear of the pixels, ready to somewhere else
        public static ConfigurablePoseD CLEAR = new ConfigurablePoseD(-12,60.1,-180);
        public static ConfigurablePoseD MID_CLEAR =  new ConfigurablePoseD(-12, 32, 0);

        public static ConfigurablePoseD PARK_CENTER = new ConfigurablePoseD(-60,14,-180);

        // These are 'trajectory pieces' which should be named like this:
        // {STARTING_POSITION}_TO_{ENDING_POSITION}
        public static final Function<Function<Pose2d, TrajectorySequenceBuilder>, TrajectorySequence>
                START_TO_MID_CLEAR = b ->
                //spline done (spline to clear the metal)
                b.apply(START.toPose()).lineToLinearHeading(MID_CLEAR.toPose()).build(),
                MID_CLEAR_TO_LEFT_SPIKE = b ->
                        b.apply(MID_CLEAR.toPose()).lineToLinearHeading(RIGHT_SPIKE.toPose()).build(),
                LEFT_SPIKE_TO_MID_CLEAR = b ->
                        //
                        b.apply(RIGHT_SPIKE.toPose()).lineToLinearHeading(MID_CLEAR.toPose()).build(),
                MID_CLEAR_TO_CLEAR = b ->
                        //
                        b.apply(MID_CLEAR.toPose()).lineToLinearHeading(CLEAR.toPose()).build(),
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
                CLEAR_TO_PARK_CENTER = b ->
                        b.apply(CLEAR.toPose()).lineToLinearHeading(PARK_CENTER.toPose()).build();
    }
    @Config
    public static class StageBlue {
        public static ConfigurablePoseD START = new ConfigurablePoseD(-12, -60, 90);
        public static ConfigurablePoseD LEFT_SPIKE = new ConfigurablePoseD(-23, -30, 120);
        public static ConfigurablePoseD MIDDLE_SPIKE = new ConfigurablePoseD(-12, -25, 90);
        //right spike next to metal
        public static ConfigurablePoseD RIGHT_SPIKE = new ConfigurablePoseD(0, -30, 0);
        // This is "clear of the pixels, ready to somewhere else
        public static ConfigurablePoseD CLEAR = new ConfigurablePoseD(-12,-60.1,180);
        public static ConfigurablePoseD MID_CLEAR =  new ConfigurablePoseD(-12, -32, 0);

        public static ConfigurablePoseD PARK_CENTER = new ConfigurablePoseD(-60,-12,0);


        // These are 'trajectory pieces' which should be named like this:
        // {STARTING_POSITION}_TO_{ENDING_POSITION}
        public static final Function<Function<Pose2d, TrajectorySequenceBuilder>, TrajectorySequence>
            START_TO_LEFT_SPIKE = b ->
                b.apply(START.toPose()).lineToLinearHeading(LEFT_SPIKE.toPose()).build(),
                START_TO_MIDDLE_SPIKE = b ->
                b.apply(START.toPose()).lineToLinearHeading(MIDDLE_SPIKE.toPose()).build(),
                START_TO_MID_CLEAR = b ->
                        //spline done (spline to clear the metal)
                        b.apply(START.toPose()).lineToLinearHeading(MID_CLEAR.toPose()).build(),
                MID_CLEAR_TO_RIGHT_SPIKE = b ->
                        b.apply(MID_CLEAR.toPose()).lineToLinearHeading(RIGHT_SPIKE.toPose()).build(),
                RIGHT_SPIKE_TO_MID_CLEAR = b ->
                        //
                        b.apply(RIGHT_SPIKE.toPose()).lineToLinearHeading(MID_CLEAR.toPose()).build(),
                MID_CLEAR_TO_CLEAR = b ->
                        //
                        b.apply(MID_CLEAR.toPose()).lineToLinearHeading(CLEAR.toPose()).build(),
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
            CLEAR_TO_PARK_CENTER = b ->
                b.apply(CLEAR.toPose()).lineToLinearHeading(PARK_CENTER.toPose()).build();
    }
}
