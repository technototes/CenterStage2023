package org.firstinspires.ftc.twenty403.controls;

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

public class OperatorController {

    public Robot robot;
    public CommandGamepad gamepad;

    public CommandButton clawOpenButton, clawCloseButton, armIntakeButton, armFirstLine;
    // public CommandButton armThirdLine, armSecondLine;
    public CommandButton override;

    public OperatorController(CommandGamepad g, Robot r) {
        this.robot = r;
        gamepad = g;
        override = g.leftTrigger.getAsButton(0.5);

        AssignNamedControllerButton();
        if (Setup.Connected.CLAWSUBSYSTEM) {
            bindClawControls();
        }
    }

    private void AssignNamedControllerButton() {
        clawOpenButton = gamepad.leftBumper;
        clawCloseButton = gamepad.rightBumper;
        armIntakeButton = gamepad.ps_cross;
        // armSecondLine = gamepad.dpadLeft;
        // armThirdLine = gamepad.dpadUp;
        armFirstLine = gamepad.dpadDown;
    }

    public void bindClawControls() {
        clawOpenButton.whenPressed(new ClawOpenCommand(robot.clawSubsystem));
        clawCloseButton.whenPressed(new ClawCloseCommand(robot.clawSubsystem));

        armIntakeButton.whenPressed(new ArmIntakeCommand((robot.clawSubsystem)));
        // armSecondLine.whenPressed(new ArmSecondLineScoring((robot.clawSubsystem)));
        // armThirdLine.whenPressed(new ArmThirdLineScoring((robot.clawSubsystem)));
        armFirstLine.whenPressed(new ArmFirstLineCommand((robot.clawSubsystem)));
    }
}
