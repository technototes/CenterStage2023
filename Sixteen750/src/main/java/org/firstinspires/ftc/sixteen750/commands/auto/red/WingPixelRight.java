package org.firstinspires.ftc.sixteen750.commands.auto.red;

import com.technototes.library.command.SequentialCommandGroup;
import com.technototes.path.command.TrajectorySequenceCommand;

import org.firstinspires.ftc.sixteen750.AutoConstants;
import org.firstinspires.ftc.sixteen750.AutoConstants.WingRed;
import org.firstinspires.ftc.sixteen750.Robot;

public class WingPixelRight extends SequentialCommandGroup {
    /*
//failed spline trajectory
    public WingPixelRight(Robot r) {
        super(
            new TrajectorySequenceCommand(r.drivebase, AutoConstants.WingRed.START_TO_RIGHT_SPIKE)
                .andThen(
                        new TrajectorySequenceCommand(r.drivebase,
                                AutoConstants.WingRed.RIGHT_SPIKE_TO_CLEAR))
                .andThen(

                        new TrajectorySequenceCommand(r.drivebase,
                                AutoConstants.WingRed.CLEAR_TO_PARK_RIGHT))
        );
    }

     */
    public WingPixelRight(Robot r) {
        super(
                new TrajectorySequenceCommand(r.drivebase, AutoConstants.WingRed.START_TO_MID_CLEAR)
                        .andThen(
                                new TrajectorySequenceCommand(r.drivebase,
                                        AutoConstants.WingRed.MID_CLEAR_TO_RIGHT_SPIKE2))
                        .andThen(
                                new TrajectorySequenceCommand(r.drivebase,
                                        WingRed.RIGHT_SPIKE_2_TO_MID_CLEAR))
                        .andThen(
                                new TrajectorySequenceCommand(r.drivebase,
                                        WingRed.MID_CLEAR_TO_CLEAR))
                        .andThen(
                                new TrajectorySequenceCommand(r.drivebase,
                                        WingRed.CLEAR_TO_PARK_RIGHT))
                        );
}
}
