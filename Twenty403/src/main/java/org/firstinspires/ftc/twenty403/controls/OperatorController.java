package org.firstinspires.ftc.twenty403.controls;

import com.technototes.library.control.CommandButton;
import com.technototes.library.control.CommandGamepad;
import org.firstinspires.ftc.twenty403.Robot;
import org.firstinspires.ftc.twenty403.Setup;
import org.firstinspires.ftc.twenty403.commands.DroneCommand;
import org.firstinspires.ftc.twenty403.commands.arm.ArmFirstLineCommand;
import org.firstinspires.ftc.twenty403.commands.arm.ArmIntakeCommand;
import org.firstinspires.ftc.twenty403.commands.arm.ArmNeutralCommand;
import org.firstinspires.ftc.twenty403.commands.arm.ArmSecondLineCommand;
import org.firstinspires.ftc.twenty403.commands.arm.ArmThirdLineCommand;
import org.firstinspires.ftc.twenty403.commands.arm.HangSequential;
import org.firstinspires.ftc.twenty403.commands.arm.ShoulderDecrementCommand;
import org.firstinspires.ftc.twenty403.commands.arm.ShoulderIncrementCommand;
import org.firstinspires.ftc.twenty403.commands.arm.ShoulderNeutralCommand;
import org.firstinspires.ftc.twenty403.commands.arm.WristDecrementCommand;
import org.firstinspires.ftc.twenty403.commands.arm.WristIncrementCommand;

public class OperatorController {

    public Robot robot;
    public CommandGamepad gamepad;
    public CommandButton launchDroneButton;
    public CommandButton armIntakeButton;
    public CommandButton shoulderDecrementButton;
    public CommandButton shoulderIncrementButton;
    public CommandButton shoulderResetZero;
    public CommandButton wristDecrementButton;
    public CommandButton wristIncrementButton;
    public CommandButton armFirstLine;
    public CommandButton ScrewExtend;
    public CommandButton ScrewRetract;
    public CommandButton HangButton;
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
        if (Setup.Connected.DRONESUBSYSTEM) {
            bindDroneControls();
        }
    }

    private void AssignNamedControllerButton() {
        armFirstLine = gamepad.ps_circle;
        armSecondLine = gamepad.ps_triangle;
        //armThirdLine = gamepad.ps_options;
        armIntakeButton = gamepad.ps_cross;
        shoulderResetZero = gamepad.ps_options;
        shoulderDecrementButton = gamepad.dpadDown;
        shoulderIncrementButton = gamepad.dpadUp;
        wristDecrementButton = gamepad.dpadRight;
        wristIncrementButton = gamepad.dpadLeft;
        armNeutralButton = gamepad.ps_square;

        HangButton = gamepad.rightTrigger.getAsButton(); //put all of hang in here

        launchDroneButton = gamepad.ps_share;
    }

    public void bindClawControls() {
        armIntakeButton.whenPressed(new ArmIntakeCommand(robot.armSubsystem));
        armNeutralButton.whenPressed(new ArmNeutralCommand(robot.armSubsystem));
        armFirstLine.whenPressed(new ArmFirstLineCommand((robot.armSubsystem)));
        armSecondLine.whenPressed(new ArmSecondLineCommand((robot.armSubsystem)));
        //armThirdLine.whenPressed(new ArmThirdLineCommand((robot.armSubsystem)));
        shoulderResetZero.whenPressed(new ShoulderNeutralCommand((robot.armSubsystem)));
        shoulderIncrementButton.whenPressed(new ShoulderIncrementCommand(robot.armSubsystem));
        shoulderDecrementButton.whenPressed(new ShoulderDecrementCommand(robot.armSubsystem));
        wristIncrementButton.whenPressed(new WristIncrementCommand(robot.armSubsystem));
        wristDecrementButton.whenPressed(new WristDecrementCommand(robot.armSubsystem));
        HangButton.whenPressed(new HangSequential(robot.armSubsystem));
    }

    public void bindDroneControls() {
        launchDroneButton.whenPressed(new DroneCommand(robot.droneSubsystem));
    }
}
