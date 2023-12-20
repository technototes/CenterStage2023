package org.firstinspires.ftc.twenty403.commands.auto.blue;

import com.technototes.library.command.CommandScheduler;
import com.technototes.library.command.SequentialCommandGroup;
import org.firstinspires.ftc.twenty403.Robot;
import org.firstinspires.ftc.twenty403.commands.arm.ClawOpenCommand;
import org.firstinspires.ftc.twenty403.commands.arm.ElbowDecrementCommand;
import org.firstinspires.ftc.twenty403.commands.arm.ShoulderFirstLineScoring;
import org.firstinspires.ftc.twenty403.commands.auto.RecordFinalHeading;
import org.firstinspires.ftc.twenty403.subsystems.ArmSubsystem;

public class BlueStageParkCorner extends SequentialCommandGroup {

    public BlueStageParkCorner(Robot robot) {
        super(
            new StagePixelPlaceSelection(robot),
            // stop monitoring command
            //new ShoulderFirstLineScoring(new ArmSubsystem()), // placeholder command
            //new ClawOpenCommand(new ArmSubsystem()), // placeholder command
            // start monitoring command
            new ParkCornerPositioning(robot),
            new ParkCorner(robot, robot.position),
            new RecordFinalHeading(robot.drivebaseSubsystem),
            CommandScheduler.getInstance()::terminateOpMode
        );
    }
}
