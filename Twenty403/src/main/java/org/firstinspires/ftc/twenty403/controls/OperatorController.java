package org.firstinspires.ftc.twenty403.controls;

import com.technototes.library.control.CommandButton;
import com.technototes.library.control.CommandGamepad;
import org.firstinspires.ftc.twenty403.Robot;
import org.firstinspires.ftc.twenty403.Setup;
import org.firstinspires.ftc.twenty403.commands.DroneCommand;
import org.firstinspires.ftc.twenty403.commands.arm.ArmFirstLineSequential;
import org.firstinspires.ftc.twenty403.commands.arm.ArmIntakeSequential;
import org.firstinspires.ftc.twenty403.commands.arm.ArmNeutralSequential;
import org.firstinspires.ftc.twenty403.commands.arm.ArmSecondLineSequential;
import org.firstinspires.ftc.twenty403.commands.arm.ArmThirdLineSequential;
import org.firstinspires.ftc.twenty403.commands.arm.ElbowDecrementCommand;
import org.firstinspires.ftc.twenty403.commands.arm.ElbowIncrementCommand;
import org.firstinspires.ftc.twenty403.commands.arm.ElbowIntakeCommand;
import org.firstinspires.ftc.twenty403.commands.arm.ShoulderIntakeCommand;
import org.firstinspires.ftc.twenty403.commands.arm.ElbowNeutralPosition;
import org.firstinspires.ftc.twenty403.commands.arm.ShoulderSecondLineScoring;
import org.firstinspires.ftc.twenty403.commands.arm.ShoulderThirdLineScoring;
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

public class OperatorController {

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
    public CommandButton Hang;
    public CommandButton HangDown;
    public CommandButton armNeutralButton;
    public CommandButton armSecondLine;
    public CommandButton armThirdLine;
    public CommandButton override;

    public OperatorController(CommandGamepad g, Robot r) {
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
        armSecondLine = gamepad.ps_triangle;
        armThirdLine = gamepad.ps_options;
        armIntakeButton = gamepad.ps_cross;
        shoulderDecrementButton = gamepad.dpadDown;
        shoulderIncrementButton = gamepad.dpadUp;
        elbowDecrementButton = gamepad.dpadRight;
        elbowIncrementButton = gamepad.dpadLeft;
        armNeutralButton = gamepad.ps_square;

        //ScrewExtend = gamepad.leftTrigger.getAsButton();
        //ScrewRetract = gamepad.rightTrigger.getAsButton();
        Hang = gamepad.rightTrigger.getAsButton(); //put all of hang in here
        //HangDown = gamepad.ps_square;

        launchDroneButton = gamepad.ps_share;
    }

    public void bindClawControls() {
        clawOpenButton.whenPressed(new ClawOpenCommand(robot.clawSubsystem));
        clawCloseButton.whenPressed(new ClawCloseCommand(robot.clawSubsystem));

        armIntakeButton.whenPressed(new ArmIntakeSequential(robot.clawSubsystem));
        armNeutralButton.whenPressed(new ArmNeutralSequential(robot.clawSubsystem));
        armFirstLine.whenPressed(new ArmFirstLineSequential((robot.clawSubsystem)));
        armSecondLine.whenPressed(new ArmSecondLineSequential((robot.clawSubsystem)));
        armThirdLine.whenPressed(new ArmThirdLineSequential((robot.clawSubsystem)));
        shoulderIncrementButton.whenPressed(new ShoulderIncrementCommand(robot.clawSubsystem));
        shoulderDecrementButton.whenPressed(new ShoulderDecrementCommand(robot.clawSubsystem));
        elbowIncrementButton.whenPressed(new ElbowIncrementCommand(robot.clawSubsystem));
        elbowDecrementButton.whenPressed(new ElbowDecrementCommand(robot.clawSubsystem));
    }

    public void bindHangControls() {
        ScrewExtend.whenPressed(new LeadScrewUp(robot.hangSubsystem));
        ScrewRetract.whenPressed(new LeadScrewDown(robot.hangSubsystem));
        ScrewRetract.whenReleased(new LeadScrewStop(robot.hangSubsystem));
        ScrewExtend.whenReleased(new LeadScrewStop(robot.hangSubsystem));
        Hang.whilePressed(new HangUp(robot.hangSubsystem));
        HangDown.whenPressed(new HangDown(robot.hangSubsystem));
        Hang.whenReleased(new HangStop(robot.hangSubsystem));
        HangDown.whenReleased(new HangStop(robot.hangSubsystem));
    }

    public void bindDroneControls() {
        launchDroneButton.whenPressed(new DroneCommand(robot.droneSubsystem));
    }
}
