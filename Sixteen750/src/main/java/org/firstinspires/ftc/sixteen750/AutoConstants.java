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
        public static ConfigurablePoseD START = new ConfigurablePoseD(35, 60, -90);
        public static ConfigurablePoseD LEFT_SPIKE = new ConfigurablePoseD(41, 36, -60);
        public static ConfigurablePoseD MIDDLE_SPIKE = new ConfigurablePoseD(35, 32, -90);
        public static ConfigurablePoseD RIGHT_SPIKE = new ConfigurablePoseD(33, 30, -200);
        // This is "clear of the pixels, ready to go somewhere else"
        public static ConfigurablePoseD CLEAR = new ConfigurablePoseD(39,56,-180);
        public static ConfigurablePoseD MID_CLEAR =  new ConfigurablePoseD(39, 32, -180);
        public static ConfigurablePoseD TELESTART = new ConfigurablePoseD(0,0,90);
        public static ConfigurablePoseD PARK_CORNER = new ConfigurablePoseD(-60,55,-180); // may need to be 180 (0 needs test)
        public static ConfigurablePoseD FORWARD = new ConfigurablePoseD(48,0,0);
        public static ConfigurablePoseD BACKWARD = new ConfigurablePoseD(0, 0, 0);
        public static ConfigurablePoseD SIDE_RIGHT = new ConfigurablePoseD(0,-48,0);
        public static ConfigurablePoseD SIDE_LEFT = new ConfigurablePoseD(0,0,0);

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
        public static ConfigurablePoseD LEFT_SPIKE = new ConfigurablePoseD(25, -32, 180);
        public static ConfigurablePoseD MIDDLE_SPIKE = new ConfigurablePoseD(35, -32, 90);
        public static ConfigurablePoseD RIGHT_SPIKE = new ConfigurablePoseD(45, -30, 60);
        // This is "clear of the pixels, ready to go somewhere else"
        public static ConfigurablePoseD CLEAR = new ConfigurablePoseD(39,-56,180);
        public static ConfigurablePoseD MID_CLEAR =  new ConfigurablePoseD(39, -32, 180);
        public static ConfigurablePoseD PARK_CORNER = new ConfigurablePoseD(-60,-56,180); // may need to be 180 (0 needs test)


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
    }

    @Config
    public static class StageRed {
        public static ConfigurablePoseD START = new ConfigurablePoseD(-12, 60, -90);
        public static ConfigurablePoseD LEFT_SPIKE = new ConfigurablePoseD(-11, 35, -45);
        public static ConfigurablePoseD MIDDLE_SPIKE = new ConfigurablePoseD(-12, 32, -90);
        public static ConfigurablePoseD RIGHT_SPIKE = new ConfigurablePoseD(-19, 35, -120);
        // This is "clear of the pixels, ready to go somewhere else"
        public static ConfigurablePoseD CLEAR = new ConfigurablePoseD(-12,56,-90);
        public static ConfigurablePoseD RIGHT_CLEAR = new ConfigurablePoseD(-35,56,-90);
        public static ConfigurablePoseD MID_CLEAR =  new ConfigurablePoseD(-15, 45, -45);
        public static ConfigurablePoseD PARK_CENTER = new ConfigurablePoseD(-59,14,-90); // may need to be 180 (0 needs test)
        public static ConfigurablePoseD MID_PARK_CENTER = new ConfigurablePoseD(-35,14,-90);




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
    }

    @Config
    public static class StageBlue {
        public static ConfigurablePoseD START = new ConfigurablePoseD(-12, -60, 90);
        public static ConfigurablePoseD LEFT_SPIKE = new ConfigurablePoseD(-19, -38, 120);
        public static ConfigurablePoseD MIDDLE_SPIKE = new ConfigurablePoseD(-12, -32, 90);
        public static ConfigurablePoseD RIGHT_SPIKE = new ConfigurablePoseD(-10, -30, 0);
        // This is "clear of the pixels, ready to go somewhere else"
        public static ConfigurablePoseD CLEAR = new ConfigurablePoseD(-12,-56,90);
        public static ConfigurablePoseD MID_CLEAR =  new ConfigurablePoseD(-15, -35  , 0);
        public static ConfigurablePoseD PARK_CENTER = new ConfigurablePoseD(-59,-14,90); // may need to be 180 (0 needs test)
        public static ConfigurablePoseD LEFT_CLEAR = new ConfigurablePoseD(-35,-56,90);
        public static ConfigurablePoseD MID_PARK_CENTER = new ConfigurablePoseD(-35,-14,90);



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
    }
}
