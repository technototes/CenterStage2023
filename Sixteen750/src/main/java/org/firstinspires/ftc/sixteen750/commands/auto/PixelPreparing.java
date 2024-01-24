package org.firstinspires.ftc.sixteen750.commands.auto;

import com.technototes.library.command.ParallelCommandGroup;

import org.firstinspires.ftc.sixteen750.Robot;
import org.firstinspires.ftc.sixteen750.commands.auto.blue.StagePlaceLeftTrajectory;

public class PixelPreparing extends ParallelCommandGroup {
    public PixelPreparing (Robot r){
        super(StagePlaceLeftTrajectory(r));
    }
}
