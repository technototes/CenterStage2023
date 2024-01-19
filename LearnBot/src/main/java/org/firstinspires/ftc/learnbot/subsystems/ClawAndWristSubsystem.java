package org.firstinspires.ftc.learnbot.subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.util.Range;
import com.technototes.library.hardware.servo.Servo;
import com.technototes.library.logger.Log;
import com.technototes.library.logger.Loggable;
import com.technototes.library.subsystem.Subsystem;

@Config
public class ClawAndWristSubsystem implements Subsystem, Loggable {

    public static double CLAW_STEP = 0.1;
    public static double CLAW_1_HIGH = 1.0;
    public static double CLAW_START = 0.0;
    public static double CLAW_1_LOW = -1.0;

    public static double CLAW_2_HIGH = 1.0;
    public static double CLAW_2_LOW = -1.0;

    public static double WRIST_STEP = 0.1;
    public static double WRIST_HIGH = 1.0;
    public static double WRIST_START = 0.0;
    public static double WRIST_LOW = -1.0;

    @Log(name = "Claw 1")
    public double Claw1Pos;

    @Log(name = "Claw 2")
    public double Claw2Pos;

    @Log(name = "Wrist")
    public double WristPos;

    private Servo claw1;
    private Servo claw2;
    private Servo wrist;

    public ClawAndWristSubsystem(Servo c1, Servo c2, Servo w) {
        claw1 = c1;
        claw2 = c2;
        wrist = w;
        SetClawPos(CLAW_START);
        SetWristPos(WRIST_START);
    }

    public void ClawInc() {
        SetClawPos(Claw1Pos + CLAW_STEP);
    }

    public void ClawDec() {
        SetClawPos(Claw1Pos - CLAW_STEP);
    }

    public void WristInc() {
        SetWristPos(WristPos + WRIST_STEP);
    }

    public void WristDec() {
        SetWristPos(WristPos - WRIST_STEP);
    }

    private void SetClawPos(double p) {
        double claw1Range = CLAW_1_HIGH - CLAW_1_LOW;
        double claw2Range = CLAW_2_HIGH - CLAW_2_LOW;
        Claw1Pos = Range.clip(p, CLAW_1_LOW, CLAW_1_HIGH);
        Claw2Pos = -(CLAW_2_LOW + ((Claw1Pos - CLAW_1_LOW) * claw2Range) / claw1Range);
        claw1.setPosition(Claw1Pos);
        claw2.setPosition(Claw2Pos);
    }

    private void SetWristPos(double p) {
        WristPos = Range.clip(p, WRIST_LOW, WRIST_HIGH);
    }
}
