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
        public static ConfigurablePoseD LEFT_SPIKE = new ConfigurablePoseD(0, 0, 0);
        public static ConfigurablePoseD MIDDLE_SPIKE = new ConfigurablePoseD(35, 32, -90);
        public static ConfigurablePoseD RIGHT_SPIKE = new ConfigurablePoseD(0, 0, 0);
        // This is "clear of the pixels, ready to somewhere else
        public static ConfigurablePoseD CLEAR = new ConfigurablePoseD(35,60,-180);
        public static ConfigurablePoseD PARK_RIGHT = new ConfigurablePoseD(-60,60,-180);

        // These are 'trajectory pieces' which should be named like this:
        // {STARTING_POSITION}_TO_{ENDING_POSITION}
        public static final Function<Function<Pose2d, TrajectorySequenceBuilder>, TrajectorySequence>
                START_TO_LEFT_SPIKE = b ->
                //spine
                b.apply(START.toPose()).lineToLinearHeading(LEFT_SPIKE.toPose()).build(),
            START_TO_MIDDLE_SPIKE = b ->
                b.apply(START.toPose()).lineToLinearHeading(MIDDLE_SPIKE.toPose()).build(),
            START_TO_RIGHT_SPIKE = b ->
                b.apply(START.toPose()).lineToLinearHeading(RIGHT_SPIKE.toPose()).build(),
            LEFT_SPIKE_TO_CLEAR = b ->
                    //spline
                b.apply(LEFT_SPIKE.toPose()).lineToLinearHeading(CLEAR.toPose()).build(),
            MIDDLE_SPIKE_TO_CLEAR = b ->
                b.apply(MIDDLE_SPIKE.toPose()).lineToLinearHeading(CLEAR.toPose()).build(),
            RIGHT_SPIKE_TO_CLEAR = b ->
                    //spline
                b.apply(RIGHT_SPIKE.toPose()).lineToLinearHeading(CLEAR.toPose()).build(),
            CLEAR_TO_PARK_RIGHT = b ->
                b.apply(CLEAR.toPose()).lineToLinearHeading(PARK_RIGHT.toPose()).build();
    }
    @Config
    public static class WingBlue {
        public static ConfigurablePoseD START = new ConfigurablePoseD(35, -60, 90);
        public static ConfigurablePoseD LEFT_SPIKE = new ConfigurablePoseD(0, 0, 0);
        public static ConfigurablePoseD MIDDLE_SPIKE = new ConfigurablePoseD(35, -32, 90);
        public static ConfigurablePoseD RIGHT_SPIKE = new ConfigurablePoseD(0, 0, 0);
        // This is "clear of the pixels, ready to somewhere else
        public static ConfigurablePoseD CLEAR = new ConfigurablePoseD(35,-60,180);
        public static ConfigurablePoseD PARK_LEFT = new ConfigurablePoseD(-60,-60,180);
        // These are 'trajectory pieces' which should be named like this:
        // {STARTING_POSITION}_TO_{ENDING_POSITION}
        public static final Function<Function<Pose2d, TrajectorySequenceBuilder>, TrajectorySequence>
                START_TO_LEFT_SPIKE = b ->
                //spline
                b.apply(START.toPose()).lineToLinearHeading(LEFT_SPIKE.toPose()).build(),
            START_TO_MIDDLE_SPIKE = b ->
                b.apply(START.toPose()).lineToLinearHeading(MIDDLE_SPIKE.toPose()).build(),
            START_TO_RIGHT_SPIKE = b ->
                b.apply(START.toPose()).lineToLinearHeading(RIGHT_SPIKE.toPose()).build(),
            LEFT_SPIKE_TO_CLEAR = b ->
                    //spline
                b.apply(LEFT_SPIKE.toPose()).lineToLinearHeading(CLEAR.toPose()).build(),
            MIDDLE_SPIKE_TO_CLEAR = b ->
                b.apply(MIDDLE_SPIKE.toPose()).lineToLinearHeading(CLEAR.toPose()).build(),
            RIGHT_SPIKE_TO_CLEAR = b ->
                b.apply(RIGHT_SPIKE.toPose()).lineToLinearHeading(CLEAR.toPose()).build(),
            CLEAR_TO_PARK_LEFT = b ->
                b.apply(CLEAR.toPose()).lineToLinearHeading(PARK_LEFT.toPose()).build();

    }


    @Config
    public static class StageRed {
        public static ConfigurablePoseD START = new ConfigurablePoseD(0, 0, 0);
        public static ConfigurablePoseD LEFT_STRIKE = new ConfigurablePoseD(0, 0, 0);
        public static ConfigurablePoseD MIDDLE_STRIKE = new ConfigurablePoseD(0, 0, 0);
        public static ConfigurablePoseD RIGHT_STRIKE = new ConfigurablePoseD(0, 0, 0);
        // This is "clear of the pixels, ready to somewhere else
        public static ConfigurablePoseD CLEAR = new ConfigurablePoseD(0,0,0);

        // These are 'trajectory pieces' which should be named like this:
        // {STARTING_POSITION}_TO_{ENDING_POSITION}
        public static final Function<Function<Pose2d, TrajectorySequenceBuilder>, TrajectorySequence>
            START_TO_LEFT_STRIKE = b ->
                b.apply(START.toPose()).lineToLinearHeading(LEFT_STRIKE.toPose()).build(),
            START_TO_MIDDLE_STRIKE = b ->
                b.apply(START.toPose()).lineToLinearHeading(MIDDLE_STRIKE.toPose()).build(),
            START_TO_RIGHT_STRIKE = b ->
                b.apply(START.toPose()).lineToLinearHeading(RIGHT_STRIKE.toPose()).build(),
            LEFT_STRIKE_TO_CLEAR = b ->
                b.apply(LEFT_STRIKE.toPose()).lineToLinearHeading(CLEAR.toPose()).build(),
            MIDDLE_STRIKE_TO_CLEAR = b ->
                b.apply(MIDDLE_STRIKE.toPose()).lineToLinearHeading(CLEAR.toPose()).build(),
            RIGHT_STRIKE_TO_CLEAR = b ->
                b.apply(RIGHT_STRIKE.toPose()).lineToLinearHeading(CLEAR.toPose()).build();
    }
    @Config
    public static class StageBlue {
        public static ConfigurablePoseD START = new ConfigurablePoseD(0, 0, 0);
        public static ConfigurablePoseD LEFT_STRIKE = new ConfigurablePoseD(0, 0, 0);
        public static ConfigurablePoseD MIDDLE_STRIKE = new ConfigurablePoseD(0, 0, 0);
        public static ConfigurablePoseD RIGHT_STRIKE = new ConfigurablePoseD(0, 0, 0);
        // This is "clear of the pixels, ready to somewhere else
        public static ConfigurablePoseD CLEAR = new ConfigurablePoseD(0,0,0);

        // These are 'trajectory pieces' which should be named like this:
        // {STARTING_POSITION}_TO_{ENDING_POSITION}
        public static final Function<Function<Pose2d, TrajectorySequenceBuilder>, TrajectorySequence>
            START_TO_LEFT_STRIKE = b ->
                b.apply(START.toPose()).lineToLinearHeading(LEFT_STRIKE.toPose()).build(),
            START_TO_MIDDLE_STRIKE = b ->
                b.apply(START.toPose()).lineToLinearHeading(MIDDLE_STRIKE.toPose()).build(),
            START_TO_RIGHT_STRIKE = b ->
                b.apply(START.toPose()).lineToLinearHeading(RIGHT_STRIKE.toPose()).build(),
            LEFT_STRIKE_TO_CLEAR = b ->
                b.apply(LEFT_STRIKE.toPose()).lineToLinearHeading(CLEAR.toPose()).build(),
            MIDDLE_STRIKE_TO_CLEAR = b ->
                b.apply(MIDDLE_STRIKE.toPose()).lineToLinearHeading(CLEAR.toPose()).build(),
            RIGHT_STRIKE_TO_CLEAR = b ->
                b.apply(RIGHT_STRIKE.toPose()).lineToLinearHeading(CLEAR.toPose()).build();
    }
}
