package org.firstinspires.ftc.learnbot;

import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.technototes.library.hardware.motor.Motor;
import com.technototes.library.logger.Loggable;
import org.firstinspires.ftc.learnbot.subsystems.DrivebaseSubsystem;
import org.firstinspires.ftc.learnbot.subsystems.MotorTestSubsystem;
import org.firstinspires.ftc.learnbot.subsystems.TestSubsystem;

public class OnlyMotorRobot implements Loggable {

    public double initialVoltage;
    public MotorTestSubsystem motorTestSubsystem;

    public OnlyMotorRobot(Hardware hw) {
        this.initialVoltage = hw.voltage();
        if (Setup.Connected.MOTOR) {
            this.motorTestSubsystem = new MotorTestSubsystem(hw);
        }
    }
}
