package org.firstinspires.ftc.twenty403.controls;

import com.technototes.library.command.Command;
import com.technototes.library.control.CommandButton;
import com.technototes.library.control.CommandGamepad;
import com.technototes.library.control.Stick;
import org.firstinspires.ftc.twenty403.Robot;
import org.firstinspires.ftc.twenty403.Setup;
import org.firstinspires.ftc.twenty403.commands.claw.ArmFirstLineCommand;
import org.firstinspires.ftc.twenty403.commands.claw.ArmIntakeCommand;
// import org.firstinspires.ftc.twenty403.commands.claw.ArmSecondLineScoring;
// import org.firstinspires.ftc.twenty403.commands.claw.ArmThirdLineScoring;
import org.firstinspires.ftc.twenty403.commands.claw.ClawCloseCommand;
import org.firstinspires.ftc.twenty403.commands.claw.ClawOpenCommand;
import org.firstinspires.ftc.twenty403.commands.hang.HangDown;
import org.firstinspires.ftc.twenty403.commands.hang.HangStop;
import org.firstinspires.ftc.twenty403.commands.hang.HangUp;
import org.firstinspires.ftc.twenty403.commands.hang.LeadScrewDown;
import org.firstinspires.ftc.twenty403.commands.hang.LeadScrewStop;
import org.firstinspires.ftc.twenty403.commands.hang.LeadScrewUp;
import org.firstinspires.ftc.twenty403.subsystems.HangSubsystem;

import java.util.Set;

public class OperatorController {

    public Robot robot;
    public CommandGamepad gamepad;

    public CommandButton clawOpenButton;
    public CommandButton clawCloseButton;
    public CommandButton armIntakeButton;
    public CommandButton armFirstLine;
    public CommandButton ScrewExtend;
    public CommandButton ScrewRetract;
    public CommandButton HangUp;
    public CommandButton HangDown;
    // public CommandButton armSecondLine;
    // public CommandButton armThirdLine;
    public CommandButton override;

    public OperatorController(CommandGamepad g, Robot r) {
        this.robot = r;
        gamepad = g;
        override = g.leftTrigger.getAsButton(0.5);

        AssignNamedControllerButton();
        if (Setup.Connected.CLAWSUBSYSTEM) {
            bindClawControls();
        }
        if (Setup.Connected.HANGSUBSYSTEM) {
            bindHangControls();
        }
    }

    private void AssignNamedControllerButton() {
        clawOpenButton = gamepad.leftBumper;
        clawCloseButton = gamepad.rightBumper;
        armIntakeButton = gamepad.ps_cross;
        // armSecondLine = gamepad.dpadLeft;
        // armThirdLine = gamepad.dpadUp;
        armFirstLine = gamepad.dpadDown;

        ScrewExtend = gamepad.ps_triangle;
        ScrewRetract = gamepad.ps_cross;
        HangUp = gamepad.ps_circle;
        HangDown = gamepad.ps_square;
    }

    public void bindClawControls() {
        clawOpenButton.whenPressed(new ClawOpenCommand(robot.clawSubsystem));
        clawCloseButton.whenPressed(new ClawCloseCommand(robot.clawSubsystem));

        armIntakeButton.whenPressed(new ArmIntakeCommand((robot.clawSubsystem)));
        // armSecondLine.whenPressed(new ArmSecondLineScoring((robot.clawSubsystem)));
        // armThirdLine.whenPressed(new ArmThirdLineScoring((robot.clawSubsystem)));
        armFirstLine.whenPressed(new ArmFirstLineCommand((robot.clawSubsystem)));
    }
    public void bindHangControls() {
        ScrewExtend.whenPressed(new LeadScrewUp(robot.hangSubsystem));
        ScrewRetract.whenPressed(new LeadScrewDown(robot.hangSubsystem));
        ScrewRetract.whenReleased(new LeadScrewStop(robot.hangSubsystem));
        ScrewExtend.whenReleased(new LeadScrewStop(robot.hangSubsystem));
        HangUp.whenPressed(new HangUp(robot.hangSubsystem));
        HangDown.whenPressed(new HangDown(robot.hangSubsystem));
        HangUp.whenReleased(new HangStop(robot.hangSubsystem));
        HangDown.whenReleased(new HangStop(robot.hangSubsystem));
    }
}
