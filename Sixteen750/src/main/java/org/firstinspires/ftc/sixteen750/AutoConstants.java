package org.firstinspires.ftc.sixteen750;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.technototes.path.geometry.ConfigurablePoseD;
import com.technototes.path.trajectorysequence.TrajectorySequence;
import com.technototes.path.trajectorysequence.TrajectorySequenceBuilder;

import java.util.function.Function;

public class AutoConstants {
    public static class Wing {
        public static class Red {
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

        public static class Blue {
            public static ConfigurablePoseD START = new ConfigurablePoseD(0, 0, 0);
        }
    }

    public static class Stage {
        public static class Red {
            public static ConfigurablePoseD START = new ConfigurablePoseD(0, 0, 0);
        }

        public static class Blue {
            public static ConfigurablePoseD START = new ConfigurablePoseD(0, 0, 0);
        }
    }
}
