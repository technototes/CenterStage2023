package org.firstinspires.ftc.twenty403.controls;

import com.technototes.library.control.CommandButton;
import com.technototes.library.control.CommandGamepad;
import org.firstinspires.ftc.twenty403.Robot;
import org.firstinspires.ftc.twenty403.Setup;
import org.firstinspires.ftc.twenty403.commands.DroneCommand;
import org.firstinspires.ftc.twenty403.commands.arm.ArmFirstLineCommand;
import org.firstinspires.ftc.twenty403.commands.arm.ArmIntakeCommand;
import org.firstinspires.ftc.twenty403.commands.arm.ArmNeutralPosition;
import org.firstinspires.ftc.twenty403.commands.arm.ClawCloseCommand;
import org.firstinspires.ftc.twenty403.commands.arm.ClawOpenCommand;
import org.firstinspires.ftc.twenty403.commands.arm.ShoulderDecrementCommand;
import org.firstinspires.ftc.twenty403.commands.arm.ShoulderIncrementCommand;
import org.firstinspires.ftc.twenty403.commands.hang.HangDown;
import org.firstinspires.ftc.twenty403.commands.hang.HangStop;
import org.firstinspires.ftc.twenty403.commands.hang.HangUp;
import org.firstinspires.ftc.twenty403.commands.hang.LeadScrewDown;
import org.firstinspires.ftc.twenty403.commands.hang.LeadScrewStop;
import org.firstinspires.ftc.twenty403.commands.hang.LeadScrewUp;

public class ArmTestController {

    public Robot robot;
    public CommandGamepad gamepad;

    public CommandButton clawOpenButton;
    public CommandButton clawCloseButton;
    public CommandButton launchDroneButton;
    public CommandButton armIntakeButton;
    public CommandButton shoulderDecrementButton;
    public CommandButton shoulderIncrementButton;
    public CommandButton elbowDecrementButton;
    public CommandButton elbowIncrementButton;
    public CommandButton armFirstLine;
    public CommandButton ScrewExtend;
    public CommandButton ScrewRetract;
    public CommandButton HangUp;
    public CommandButton HangDown;
    public CommandButton armNeutralButton;
    // public CommandButton armSecondLine;
    // public CommandButton armThirdLine;
    public CommandButton override;

    public ArmTestController(CommandGamepad g, Robot r) {
        this.robot = r;
        gamepad = g;
        override = g.leftTrigger.getAsButton(0.5);

        AssignNamedControllerButton();
        if (Setup.Connected.ARMSUBSYSTEM) {
            bindClawControls();
        }
        if (Setup.Connected.HANGSUBSYSTEM) {
            bindHangControls();
        }
        if (Setup.Connected.DRONESUBSYSTEM) {
            bindDroneControls();
        }
    }

    private void AssignNamedControllerButton() {
        clawOpenButton = gamepad.leftBumper;
        clawCloseButton = gamepad.rightBumper;
        armFirstLine = gamepad.ps_circle;

        armIntakeButton = gamepad.ps_cross;
        shoulderDecrementButton = gamepad.dpadDown;
        shoulderIncrementButton = gamepad.dpadUp;
        armNeutralButton = gamepad.dpadRight;

        ScrewExtend = gamepad.leftTrigger.getAsButton();
        ScrewRetract = gamepad.rightTrigger.getAsButton();
        HangUp = gamepad.ps_triangle;
        HangDown = gamepad.ps_square;
        launchDroneButton = gamepad.ps_share;
    }

    public void bindClawControls() {
        clawOpenButton.whenPressed(new ClawOpenCommand(robot.clawSubsystem));
        clawCloseButton.whenPressed(new ClawCloseCommand(robot.clawSubsystem));

        armIntakeButton.whenPressed(new ArmIntakeCommand(robot.clawSubsystem));
        armNeutralButton.whenPressed(new ArmNeutralPosition(robot.clawSubsystem));
        // armSecondLine.whenPressed(new ArmSecondLineScoring((robot.clawSubsystem)));
        // armThirdLine.whenPressed(new ArmThirdLineScoring((robot.clawSubsystem)));
        armFirstLine.whenPressed(new ArmFirstLineCommand(robot.clawSubsystem));
        shoulderIncrementButton.whenPressed(new ShoulderIncrementCommand(robot.clawSubsystem));
        shoulderDecrementButton.whenPressed(new ShoulderDecrementCommand(robot.clawSubsystem));
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

    public void bindDroneControls() {
        launchDroneButton.whenPressed(new DroneCommand(robot.droneSubsystem));
    }
}
