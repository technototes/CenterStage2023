package org.firstinspires.ftc.twenty403;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.technototes.path.geometry.ConfigurablePoseD;
import com.technototes.path.trajectorysequence.TrajectorySequence;
import com.technototes.path.trajectorysequence.TrajectorySequenceBuilder;

import java.util.function.Function;

public class AutoConstants {
    @Config
    public static class WingRed {
        public static ConfigurablePoseD START = new ConfigurablePoseD(35, 60, -90);
        public static ConfigurablePoseD LEFT_SPIKE = new ConfigurablePoseD(43, 35, -60); // fine tuned
        public static ConfigurablePoseD MIDDLE_SPIKE = new ConfigurablePoseD(35, 31, -90); //  fine tuned
        public static ConfigurablePoseD RIGHT_SPIKE = new ConfigurablePoseD(31, 32, -180); // near the metal,  fine tuned
        // This is "clear of the pixels, ready to go somewhere else"
        public static ConfigurablePoseD CLEAR = new ConfigurablePoseD(39,56,-90);
        public static ConfigurablePoseD MID_CLEAR =  new ConfigurablePoseD(39, 32, -180); // fine tuned OKAYY
        public static ConfigurablePoseD PARK_CENTER = new ConfigurablePoseD(-59,12,-90); // may need to be 180 (0 needs test)

        public static ConfigurablePoseD PARK_CORNER = new ConfigurablePoseD(-60,56,-90); // may need to be 180 (0 needs test)
        public static ConfigurablePoseD RIGHT_CLEAR = new ConfigurablePoseD(-35,56,-90); // for pixel placement
        public static ConfigurablePoseD PLACE_LEFT = new ConfigurablePoseD(-50,30,0); //not fine tuned heading needs to be zero (backwards placement)
        public static ConfigurablePoseD PLACE_MIDDLE = new ConfigurablePoseD(-50,35,0);// not fine tuned
        public static ConfigurablePoseD PLACE_RIGHT = new ConfigurablePoseD(-50,40,0);// not fine tuned
        //testing constants
        public static ConfigurablePoseD TELESTART = new ConfigurablePoseD(0,0,90);
        public static ConfigurablePoseD FORWARD = new ConfigurablePoseD(48,0,0);
        public static ConfigurablePoseD BACKWARD = new ConfigurablePoseD(0, 0, 0);
        public static ConfigurablePoseD SIDE_RIGHT = new ConfigurablePoseD(0,-48,0);
        public static ConfigurablePoseD SIDE_LEFT = new ConfigurablePoseD(0,0,0);
        public static ConfigurablePoseD MID_PARK_CENTER = new ConfigurablePoseD(-35,12,-90);


        // These are 'trajectory pieces' which should be named like this:
        // {STARTING_POSITION}_TO_{ENDING_POSITION}
        public static final Function<Function<Pose2d, TrajectorySequenceBuilder>, TrajectorySequence>
            START_TO_LEFT_SPIKE = b ->
                b.apply(START.toPose()).lineToLinearHeading(LEFT_SPIKE.toPose()).build(),
            START_TO_MIDDLE_SPIKE = b ->
                b.apply(START.toPose()).lineToLinearHeading(MIDDLE_SPIKE.toPose()).build(),
            START_TO_RIGHT_SPIKE = b ->
                b.apply(START.toPose()).lineToLinearHeading(RIGHT_SPIKE.toPose()).build(),
            LEFT_SPIKE_TO_CLEAR = b ->
                b.apply(LEFT_SPIKE.toPose()).lineToLinearHeading(CLEAR.toPose()).build(),
            MIDDLE_SPIKE_TO_CLEAR = b ->
                b.apply(MIDDLE_SPIKE.toPose()).lineToLinearHeading(CLEAR.toPose()).build(),
            RIGHT_SPIKE_TO_CLEAR = b ->
                b.apply(RIGHT_SPIKE.toPose()).lineToLinearHeading(CLEAR.toPose()).build();
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
                CLEAR_TO_PARK_CORNER = b ->
                b.apply(CLEAR.toPose()).lineToLinearHeading(PARK_CORNER.toPose()).build();
        // pixel placement trajectories
        public static final Function<Function<Pose2d, TrajectorySequenceBuilder>, TrajectorySequence>
                ClEAR_TO_RIGHT_CLEAR = b ->
                b.apply(CLEAR.toPose()).lineToLinearHeading(RIGHT_CLEAR.toPose()).build();
        public static final Function<Function<Pose2d, TrajectorySequenceBuilder>, TrajectorySequence>
                RIGHT_CLEAR_TO_PLACE_LEFT = b ->
                b.apply(RIGHT_CLEAR.toPose()).lineToLinearHeading(PLACE_LEFT.toPose()).build();
        public static final Function<Function<Pose2d, TrajectorySequenceBuilder>, TrajectorySequence>
                RIGHT_CLEAR_TO_PLACE_MIDDLE = b ->
                b.apply(RIGHT_CLEAR.toPose()).lineToLinearHeading(PLACE_MIDDLE.toPose()).build();
        public static final Function<Function<Pose2d, TrajectorySequenceBuilder>, TrajectorySequence>
                RIGHT_CLEAR_TO_PLACE_RIGHT = b ->
                b.apply(RIGHT_CLEAR.toPose()).lineToLinearHeading(PLACE_RIGHT.toPose()).build();
        public static final Function<Function<Pose2d, TrajectorySequenceBuilder>, TrajectorySequence>
                PLACE_LEFT_TO_RIGHT_CLEAR = b ->
                b.apply(PLACE_LEFT.toPose()).lineToLinearHeading(RIGHT_CLEAR.toPose()).build();
        public static final Function<Function<Pose2d, TrajectorySequenceBuilder>, TrajectorySequence>
                PLACE_MIDDLE_TO_RIGHT_CLEAR = b ->
                b.apply(PLACE_MIDDLE.toPose()).lineToLinearHeading(RIGHT_CLEAR.toPose()).build();
        public static final Function<Function<Pose2d, TrajectorySequenceBuilder>, TrajectorySequence>
                PLACE_RIGHT_TO_RIGHT_CLEAR = b ->
                b.apply(PLACE_RIGHT.toPose()).lineToLinearHeading(RIGHT_CLEAR.toPose()).build();
        public static final Function<Function<Pose2d, TrajectorySequenceBuilder>, TrajectorySequence>
                RIGHT_CLEAR_TO_PARK_CORNER = b ->
                b.apply(RIGHT_CLEAR.toPose()).lineToLinearHeading(PARK_CORNER.toPose()).build();
        public static final Function<Function<Pose2d, TrajectorySequenceBuilder>, TrajectorySequence>
                RIGHT_CLEAR_TO_MID_PARK_CENTER = b ->
                b.apply(RIGHT_CLEAR.toPose()).lineToLinearHeading(MID_PARK_CENTER.toPose()).build();
        public static final Function<Function<Pose2d, TrajectorySequenceBuilder>, TrajectorySequence>
                MID_PARK_CENTER_TO_PARK_CENTER= b ->
                b.apply(MID_PARK_CENTER.toPose()).lineToLinearHeading(PARK_CENTER.toPose()).build();


        // testing trajectories
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
        public static ConfigurablePoseD LEFT_SPIKE = new ConfigurablePoseD(32, -32, 180); // near the metal,  fine tuned
        public static ConfigurablePoseD MIDDLE_SPIKE = new ConfigurablePoseD(35, -32, 90); //  fine tuned
        public static ConfigurablePoseD RIGHT_SPIKE = new ConfigurablePoseD(41, -35, 60); // fine tuned
        // This is "clear of the pixels, ready to go somewhere else"
        public static ConfigurablePoseD CLEAR = new ConfigurablePoseD(39,-56,90);
        public static ConfigurablePoseD MID_CLEAR =  new ConfigurablePoseD(39, -32, 180); //  fine tuned
        public static ConfigurablePoseD PARK_CENTER = new ConfigurablePoseD(-59,-12,90); // may need to be 180 (0 needs test)

        public static ConfigurablePoseD PARK_CORNER = new ConfigurablePoseD(-60,-56,90); // may need to be 180 (0 needs test)
        public static ConfigurablePoseD LEFT_CLEAR = new ConfigurablePoseD(-35,-56,90);// for pixel placement
        public static ConfigurablePoseD PLACE_LEFT = new ConfigurablePoseD(-50,-40,0);
        public static ConfigurablePoseD PLACE_MIDDLE = new ConfigurablePoseD(-50,-35,0);
        public static ConfigurablePoseD PLACE_RIGHT = new ConfigurablePoseD(-50,-30,0);
        public static ConfigurablePoseD MID_PARK_CENTER = new ConfigurablePoseD(-35,-12,90);



        public static ConfigurablePoseD TELESTART = new ConfigurablePoseD(0,0,90);

        // These are 'trajectory pieces' which should be named like this:
        // {STARTING_POSITION}_TO_{ENDING_POSITION}
        public static final Function<Function<Pose2d, TrajectorySequenceBuilder>, TrajectorySequence>
                START_TO_MID_CLEAR = b ->
                //spline done (spline to clear the metal)
                b.apply(START.toPose()).lineToLinearHeading(MID_CLEAR.toPose()).build(),
                MID_CLEAR_TO_LEFT_SPIKE = b ->
                        b.apply(MID_CLEAR.toPose()).lineToLinearHeading(LEFT_SPIKE.toPose()).build(),
                LEFT_SPIKE_TO_MID_CLEAR = b ->
                        //
                        b.apply(LEFT_SPIKE.toPose()).lineToLinearHeading(MID_CLEAR.toPose()).build(),
                MID_CLEAR_TO_CLEAR = b ->
                        //
                        b.apply(MID_CLEAR.toPose()).lineToLinearHeading(CLEAR.toPose()).build(),
            START_TO_LEFT_SPIKE = b ->
                b.apply(START.toPose()).lineToLinearHeading(LEFT_SPIKE.toPose()).build(),
            START_TO_MIDDLE_SPIKE = b ->
                b.apply(START.toPose()).lineToLinearHeading(MIDDLE_SPIKE.toPose()).build(),
            START_TO_RIGHT_SPIKE = b ->
                b.apply(START.toPose()).lineToLinearHeading(RIGHT_SPIKE.toPose()).build(),
            LEFT_SPIKE_TO_CLEAR = b ->
                b.apply(LEFT_SPIKE.toPose()).lineToLinearHeading(CLEAR.toPose()).build(),
            MIDDLE_SPIKE_TO_CLEAR = b ->
                b.apply(MIDDLE_SPIKE.toPose()).lineToLinearHeading(CLEAR.toPose()).build(),
            RIGHT_SPIKE_TO_CLEAR = b ->
                b.apply(RIGHT_SPIKE.toPose()).lineToLinearHeading(CLEAR.toPose()).build();
        public static final Function<Function<Pose2d, TrajectorySequenceBuilder>, TrajectorySequence>
                CLEAR_TO_PARK_CORNER = b ->
                b.apply(CLEAR.toPose()).lineToLinearHeading(PARK_CORNER.toPose()).build();
        public static final Function<Function<Pose2d, TrajectorySequenceBuilder>, TrajectorySequence>
                ClEAR_TO_LEFT_CLEAR = b ->
                b.apply(CLEAR.toPose()).lineToLinearHeading(LEFT_CLEAR.toPose()).build();
        public static final Function<Function<Pose2d, TrajectorySequenceBuilder>, TrajectorySequence>
                LEFT_CLEAR_TO_PLACE_LEFT = b ->
                b.apply(LEFT_CLEAR.toPose()).lineToLinearHeading(PLACE_LEFT.toPose()).build();
        public static final Function<Function<Pose2d, TrajectorySequenceBuilder>, TrajectorySequence>
                LEFT_CLEAR_TO_PLACE_MIDDLE = b ->
                b.apply(LEFT_CLEAR.toPose()).lineToLinearHeading(PLACE_MIDDLE.toPose()).build();
        public static final Function<Function<Pose2d, TrajectorySequenceBuilder>, TrajectorySequence>
                LEFT_CLEAR_TO_PLACE_RIGHT = b ->
                b.apply(LEFT_CLEAR.toPose()).lineToLinearHeading(PLACE_RIGHT.toPose()).build();
        public static final Function<Function<Pose2d, TrajectorySequenceBuilder>, TrajectorySequence>
                PLACE_LEFT_TO_LEFT_CLEAR = b ->
                b.apply(PLACE_LEFT.toPose()).lineToLinearHeading(LEFT_CLEAR.toPose()).build();
        public static final Function<Function<Pose2d, TrajectorySequenceBuilder>, TrajectorySequence>
                PLACE_MIDDLE_TO_LEFT_CLEAR = b ->
                b.apply(PLACE_MIDDLE.toPose()).lineToLinearHeading(LEFT_CLEAR.toPose()).build();
        public static final Function<Function<Pose2d, TrajectorySequenceBuilder>, TrajectorySequence>
                PLACE_RIGHT_TO_LEFT_CLEAR = b ->
                b.apply(PLACE_RIGHT.toPose()).lineToLinearHeading(LEFT_CLEAR.toPose()).build();
        public static final Function<Function<Pose2d, TrajectorySequenceBuilder>, TrajectorySequence>
                LEFT_CLEAR_TO_PARK_CORNER = b ->
                b.apply(LEFT_CLEAR.toPose()).lineToLinearHeading(PARK_CORNER.toPose()).build();
        public static final Function<Function<Pose2d, TrajectorySequenceBuilder>, TrajectorySequence>
                MID_PARK_CENTER_TO_PARK_CENTER = b ->
                b.apply(MID_PARK_CENTER.toPose()).lineToLinearHeading(PARK_CENTER.toPose()).build();

    }

    @Config
    public static class StageRed {
        public static ConfigurablePoseD START = new ConfigurablePoseD(-12, 60, -90);
        public static ConfigurablePoseD LEFT_SPIKE = new ConfigurablePoseD(-8, 33, -45);  // near the metal,  fine tuned
        public static ConfigurablePoseD MIDDLE_SPIKE = new ConfigurablePoseD(-12, 32, -90); //fine tuned
        public static ConfigurablePoseD RIGHT_SPIKE = new ConfigurablePoseD( -18, 36, -120); //  fine tuned
        // This is "clear of the pixels, ready to go somewhere else"
        public static ConfigurablePoseD CLEAR = new ConfigurablePoseD(-12,56,-90);
        public static ConfigurablePoseD RIGHT_CLEAR = new ConfigurablePoseD(-35,56,-90);
        public static ConfigurablePoseD MID_CLEAR =  new ConfigurablePoseD(-15, 45, -45); // fine tuned (-15, 45, -45)
        public static ConfigurablePoseD PARK_CORNER = new ConfigurablePoseD(-60,56,-90); // may need to be 180 (0 needs test)

        public static ConfigurablePoseD PARK_CENTER = new ConfigurablePoseD(-59,12,-90); // may need to be 180 (0 needs test)
        public static ConfigurablePoseD MID_PARK_CENTER = new ConfigurablePoseD(-35,12,-90);
        public static ConfigurablePoseD PLACE_LEFT = new ConfigurablePoseD(-50,30,0); // not fine tuned
        public static ConfigurablePoseD PLACE_MIDDLE = new ConfigurablePoseD(-50,35,0); // not fine tuned
        public static ConfigurablePoseD PLACE_RIGHT = new ConfigurablePoseD(-50,40,0); // not fine tuned

        // These are 'trajectory pieces' which should be named like this:
        // {STARTING_POSITION}_TO_{ENDING_POSITION}
        public static final Function<Function<Pose2d, TrajectorySequenceBuilder>, TrajectorySequence>
                START_TO_MID_CLEAR = b ->
                //spline done (spline to clear the metal)
                b.apply(START.toPose()).lineToLinearHeading(MID_CLEAR.toPose()).build(),
                MID_CLEAR_TO_LEFT_SPIKE = b ->
                        b.apply(MID_CLEAR.toPose()).lineToLinearHeading(LEFT_SPIKE.toPose()).build(),
                LEFT_SPIKE_TO_MID_CLEAR = b ->
                        //
                        b.apply(LEFT_SPIKE.toPose()).lineToLinearHeading(MID_CLEAR.toPose()).build(),
                MID_CLEAR_TO_CLEAR = b ->
                        //
                        b.apply(MID_CLEAR.toPose()).lineToLinearHeading(CLEAR.toPose()).build(),
            START_TO_LEFT_SPIKE = b ->
                b.apply(START.toPose()).lineToLinearHeading(LEFT_SPIKE.toPose()).build(),
            START_TO_MIDDLE_SPIKE = b ->
                b.apply(START.toPose()).lineToLinearHeading(MIDDLE_SPIKE.toPose()).build(),
            START_TO_RIGHT_SPIKE = b ->
                b.apply(START.toPose()).lineToLinearHeading(RIGHT_SPIKE.toPose()).build(),
            LEFT_SPIKE_TO_CLEAR = b ->
                b.apply(LEFT_SPIKE.toPose()).lineToLinearHeading(CLEAR.toPose()).build(),
            MIDDLE_SPIKE_TO_CLEAR = b ->
                b.apply(MIDDLE_SPIKE.toPose()).lineToLinearHeading(CLEAR.toPose()).build(),
            RIGHT_SPIKE_TO_CLEAR = b ->
                b.apply(RIGHT_SPIKE.toPose()).lineToLinearHeading(CLEAR.toPose()).build(),
        CLEAR_TO_PARK_CENTER = b ->
                b.apply(CLEAR.toPose()).lineToLinearHeading(PARK_CENTER.toPose()).build(),
        CLEAR_TO_MID_PARK_CENTER = b ->
                b.apply(CLEAR.toPose()).lineToLinearHeading(MID_PARK_CENTER.toPose()).build(),
                CLEAR_TO_RIGHT_CLEAR = b ->
                        b.apply(CLEAR.toPose()).lineToLinearHeading(RIGHT_CLEAR.toPose()).build(),
                RIGHT_CLEAR_TO_MID_PARK_CENTER = b ->
                        b.apply(RIGHT_CLEAR.toPose()).lineToLinearHeading(MID_PARK_CENTER.toPose()).build(),
        MID_PARK_CENTER_TO_PARK_CENTER = b ->
                b.apply(MID_PARK_CENTER.toPose()).lineToLinearHeading(PARK_CENTER.toPose()).build();
        public static final Function<Function<Pose2d, TrajectorySequenceBuilder>, TrajectorySequence>
                ClEAR_TO_RIGHT_CLEAR = b ->
                b.apply(CLEAR.toPose()).lineToLinearHeading(RIGHT_CLEAR.toPose()).build();
        public static final Function<Function<Pose2d, TrajectorySequenceBuilder>, TrajectorySequence>
                RIGHT_CLEAR_TO_PLACE_LEFT = b ->
                b.apply(RIGHT_CLEAR.toPose()).lineToLinearHeading(PLACE_LEFT.toPose()).build();
        public static final Function<Function<Pose2d, TrajectorySequenceBuilder>, TrajectorySequence>
                RIGHT_CLEAR_TO_PLACE_MIDDLE = b ->
                b.apply(RIGHT_CLEAR.toPose()).lineToLinearHeading(PLACE_MIDDLE.toPose()).build();
        public static final Function<Function<Pose2d, TrajectorySequenceBuilder>, TrajectorySequence>
                RIGHT_CLEAR_TO_PLACE_RIGHT = b ->
                b.apply(RIGHT_CLEAR.toPose()).lineToLinearHeading(PLACE_RIGHT.toPose()).build();
        public static final Function<Function<Pose2d, TrajectorySequenceBuilder>, TrajectorySequence>
                PLACE_LEFT_TO_MID_PARK_CENTER = b ->
                b.apply(PLACE_LEFT.toPose()).lineToLinearHeading(MID_PARK_CENTER.toPose()).build();
        public static final Function<Function<Pose2d, TrajectorySequenceBuilder>, TrajectorySequence>
                PLACE_MIDDLE_TO_MID_PARK_CENTER = b ->
                b.apply(PLACE_MIDDLE.toPose()).lineToLinearHeading(MID_PARK_CENTER.toPose()).build();
        public static final Function<Function<Pose2d, TrajectorySequenceBuilder>, TrajectorySequence>
                PLACE_RIGHT_TO_MID_PARK_CENTER = b ->
                b.apply(PLACE_RIGHT.toPose()).lineToLinearHeading(MID_PARK_CENTER.toPose()).build();
        public static final Function<Function<Pose2d, TrajectorySequenceBuilder>, TrajectorySequence>
                RIGHT_CLEAR_TO_PARK_CORNER = b ->
                b.apply(RIGHT_CLEAR.toPose()).lineToLinearHeading(PARK_CORNER.toPose()).build();
        public static final Function<Function<Pose2d, TrajectorySequenceBuilder>, TrajectorySequence>
                MID_PARK_CENTER_TO_RIGHT_CLEAR = b ->
                b.apply(MID_PARK_CENTER.toPose()).lineToLinearHeading(RIGHT_CLEAR.toPose()).build();
    }

    @Config
    public static class StageBlue {
        public static ConfigurablePoseD START = new ConfigurablePoseD(-12, -60, 90);
        public static ConfigurablePoseD LEFT_SPIKE = new ConfigurablePoseD(-18, -36, 120); //  fine tuned
        public static ConfigurablePoseD MIDDLE_SPIKE = new ConfigurablePoseD(-12, -32, 90); //  fine tuned
        public static ConfigurablePoseD RIGHT_SPIKE = new ConfigurablePoseD(-7, -33, 45); // near the metal, fine tuned
        // This is "clear of the pixels, ready to go somewhere else"
        public static ConfigurablePoseD CLEAR = new ConfigurablePoseD(-12,-56,90);
        public static ConfigurablePoseD MID_CLEAR =  new ConfigurablePoseD(-15, -45  , 45); //  fine tuned
        public static ConfigurablePoseD PARK_CORNER = new ConfigurablePoseD(-60,-56,90); // may need to be 180 (0 needs test)
        public static ConfigurablePoseD PARK_CENTER = new ConfigurablePoseD(-59,-12,90); // may need to be 180 (0 needs test)
        public static ConfigurablePoseD LEFT_CLEAR = new ConfigurablePoseD(-35,-56,90);
        public static ConfigurablePoseD MID_PARK_CENTER = new ConfigurablePoseD(-35,-12,90);
        public static ConfigurablePoseD PLACE_LEFT = new ConfigurablePoseD(-50,-40,0);
        public static ConfigurablePoseD PLACE_MIDDLE = new ConfigurablePoseD(-50,-35,0);
        public static ConfigurablePoseD PLACE_RIGHT = new ConfigurablePoseD(-50,-30,0);



        // These are 'trajectory pieces' which should be named like this:
        // {STARTING_POSITION}_TO_{ENDING_POSITION}
        public static final Function<Function<Pose2d, TrajectorySequenceBuilder>, TrajectorySequence>
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
            START_TO_LEFT_SPIKE = b ->
                b.apply(START.toPose()).lineToLinearHeading(LEFT_SPIKE.toPose()).build(),
            START_TO_MIDDLE_SPIKE = b ->
                b.apply(START.toPose()).lineToLinearHeading(MIDDLE_SPIKE.toPose()).build(),
            START_TO_RIGHT_SPIKE = b ->
                b.apply(START.toPose()).lineToLinearHeading(RIGHT_SPIKE.toPose()).build(),
            LEFT_SPIKE_TO_CLEAR = b ->
                b.apply(LEFT_SPIKE.toPose()).lineToLinearHeading(CLEAR.toPose()).build(),
            MIDDLE_SPIKE_TO_CLEAR = b ->
                b.apply(MIDDLE_SPIKE.toPose()).lineToLinearHeading(CLEAR.toPose()).build(),
            RIGHT_SPIKE_TO_CLEAR = b ->
                b.apply(RIGHT_SPIKE.toPose()).lineToLinearHeading(CLEAR.toPose()).build(),
        CLEAR_TO_PARK_CENTER = b ->
                b.apply(CLEAR.toPose()).lineToLinearHeading(PARK_CENTER.toPose()).build(),
        CLEAR_TO_MID_PARK_CENTER = b ->
                b.apply(CLEAR.toPose()).lineToLinearHeading(MID_PARK_CENTER.toPose()).build(),
        CLEAR_TO_LEFT_CLEAR = b ->
                b.apply(CLEAR.toPose()).lineToLinearHeading(LEFT_CLEAR.toPose()).build(),
        LEFT_CLEAR_TO_MID_PARK_CENTER = b ->
                b.apply(LEFT_CLEAR.toPose()).lineToLinearHeading(MID_PARK_CENTER.toPose()).build(),
        MID_PARK_CENTER_TO_PARK_CENTER = b ->
                b.apply(MID_PARK_CENTER.toPose()).lineToLinearHeading(PARK_CENTER.toPose()).build();
        public static final Function<Function<Pose2d, TrajectorySequenceBuilder>, TrajectorySequence>
                ClEAR_TO_LEFT_CLEAR = b ->
                b.apply(CLEAR.toPose()).lineToLinearHeading(LEFT_CLEAR.toPose()).build();
        public static final Function<Function<Pose2d, TrajectorySequenceBuilder>, TrajectorySequence>
                LEFT_CLEAR_TO_PLACE_LEFT = b ->
                b.apply(LEFT_CLEAR.toPose()).lineToLinearHeading(PLACE_LEFT.toPose()).build();
        public static final Function<Function<Pose2d, TrajectorySequenceBuilder>, TrajectorySequence>
                LEFT_CLEAR_TO_PLACE_MIDDLE = b ->
                b.apply(LEFT_CLEAR.toPose()).lineToLinearHeading(PLACE_MIDDLE.toPose()).build();
        public static final Function<Function<Pose2d, TrajectorySequenceBuilder>, TrajectorySequence>
                LEFT_CLEAR_TO_PLACE_RIGHT = b ->
                b.apply(LEFT_CLEAR.toPose()).lineToLinearHeading(PLACE_RIGHT.toPose()).build();
        public static final Function<Function<Pose2d, TrajectorySequenceBuilder>, TrajectorySequence>
                PLACE_LEFT_TO_MID_PARK_CENTER = b ->
                b.apply(PLACE_LEFT.toPose()).lineToLinearHeading(MID_PARK_CENTER.toPose()).build();
        public static final Function<Function<Pose2d, TrajectorySequenceBuilder>, TrajectorySequence>
                PLACE_MIDDLE_TO_MID_PARK_CENTER = b ->
                b.apply(PLACE_MIDDLE.toPose()).lineToLinearHeading(MID_PARK_CENTER.toPose()).build();
        public static final Function<Function<Pose2d, TrajectorySequenceBuilder>, TrajectorySequence>
                PLACE_RIGHT_TO_MID_PARK_CENTER = b ->
                b.apply(PLACE_RIGHT.toPose()).lineToLinearHeading(MID_PARK_CENTER.toPose()).build();
        public static final Function<Function<Pose2d, TrajectorySequenceBuilder>, TrajectorySequence>
                MID_PARK_CENTER_TO_LEFT_CLEAR = b ->
                b.apply(MID_PARK_CENTER.toPose()).lineToLinearHeading(LEFT_CLEAR.toPose()).build();
        public static final Function<Function<Pose2d, TrajectorySequenceBuilder>, TrajectorySequence>
                LEFT_CLEAR_TO_PARK_CORNER = b ->
                b.apply(LEFT_CLEAR.toPose()).lineToLinearHeading(PARK_CORNER.toPose()).build();
    }
}
