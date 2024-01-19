package org.firstinspires.ftc.learnbot;

import com.acmerobotics.dashboard.config.Config;
import com.technototes.library.hardware.servo.Servo;
import org.firstinspires.ftc.learnbot.subsystems.ClawAndWristSubsystem;

@Config
public class ClawAndWristBot {

    public String CLAW1 = "claw1";
    public String CLAW2 = "claw2";
    public String WRIST = "wrist";

    public ClawAndWristSubsystem caw;

    public ClawAndWristBot() {
        caw = new ClawAndWristSubsystem(new Servo(CLAW1), new Servo(CLAW2), new Servo(WRIST));
    }
}
