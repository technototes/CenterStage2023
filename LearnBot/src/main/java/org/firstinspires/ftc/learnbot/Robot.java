package org.firstinspires.ftc.learnbot;

import com.technototes.library.logger.Loggable;
import com.technototes.library.util.Alliance;
import org.firstinspires.ftc.learnbot.subsystems.SpinnySubsystem;
import org.firstinspires.ftc.learnbot.subsystems.StuffSubsystem;

public class Robot implements Loggable {

    public StartingPosition position;
    public Alliance alliance;
    public double initialVoltage;
    public SpinnySubsystem spinner;
    public StuffSubsystem stuff;

    public Robot(Hardware hw, Alliance team, StartingPosition pos) {
        this.position = pos;
        this.alliance = team;
        this.initialVoltage = hw.voltage();
        this.spinner = new SpinnySubsystem(hw);
        this.stuff = new StuffSubsystem(hw);
    }
}
