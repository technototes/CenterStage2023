package org.firstinspires.ftc.twenty403.controls;

import com.technototes.library.control.CommandButton;
import com.technototes.library.control.CommandGamepad;
import com.technototes.library.control.Stick;
import org.firstinspires.ftc.twenty403.Robot;
import org.firstinspires.ftc.twenty403.Setup;
import org.firstinspires.ftc.twenty403.commands.DroneCommand;
import org.firstinspires.ftc.twenty403.commands.claw.ClawCloseCommand;
import org.firstinspires.ftc.twenty403.commands.claw.ClawOpenCommand;

public class OperatorController {

    public Robot robot;
    public CommandGamepad gamepad;

    public CommandButton clawOpenButton, clawCloseButton, launchDroneButton;
    public CommandButton override;

    public OperatorController(CommandGamepad g, Robot r) {
        this.robot = r;
        gamepad = g;
        override = g.leftTrigger.getAsButton(0.5);

        AssignNamedControllerButton();
        if (Setup.Connected.CLAWSUBSYSTEM) {
            bindClawControls();
        }
        if (Setup.Connected.DRONESUBSYSTEM) {
            bindDroneControls();
        }
    }

    private void AssignNamedControllerButton() {
        clawOpenButton = gamepad.leftBumper;
        clawCloseButton = gamepad.rightBumper;
        launchDroneButton = gamepad.ps_share;
    }

    public void bindClawControls() {
        clawOpenButton.whenPressed(new ClawOpenCommand(robot.clawSubsystem));
        clawCloseButton.whenPressed(new ClawCloseCommand(robot.clawSubsystem));
    }
    public void bindDroneControls() {
        launchDroneButton.whenPressed(new DroneCommand(robot.droneSubsystem));
    }
}
