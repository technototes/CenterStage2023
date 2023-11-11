package org.firstinspires.ftc.twenty403.controls;

import com.technototes.library.command.CommandScheduler;
import com.technototes.library.control.CommandButton;
import com.technototes.library.control.CommandGamepad;
import com.technototes.library.control.Stick;
import org.firstinspires.ftc.twenty403.Robot;
import org.firstinspires.ftc.twenty403.Setup;
import org.firstinspires.ftc.twenty403.commands.DroneCommand;
import org.firstinspires.ftc.twenty403.commands.auto.claw.ClawCloseCommand;
import org.firstinspires.ftc.twenty403.commands.auto.claw.ClawOpenCommand;
import org.firstinspires.ftc.twenty403.commands.driving.JoystickDriveCommand;
import org.firstinspires.ftc.twenty403.commands.driving.ResetGyroCommand;

public class SingleController {

    public Robot robot;
    public Setup setup;
    public CommandGamepad gamepad;

    public Stick driveLeftStick, driveRightStick;
    public CommandButton resetGyroButton, driveStraight, turboButton;
    public CommandButton clawOpenButton, clawCloseButton, armFirstLineButton, armSecondLineButton, armThirdLineButton, launchDroneButton;

    public SingleController(CommandGamepad g, Robot r, Setup s) {
        this.robot = r;
        this.setup = s;
        gamepad = g;

        AssignNamedControllerButton();

        if (Setup.Connected.DRIVEBASE) {
            bindDriveControls();
        }
        if (Setup.Connected.CLAWSUBSYSTEM) {
            bindClawControls();
        }

        if (Setup.Connected.DRONESUBSYSTEM) {
            bindDroneControls();
        }
    }

    private void AssignNamedControllerButton() {
        resetGyroButton = gamepad.rightStickButton;
        driveLeftStick = gamepad.leftStick;
        driveRightStick = gamepad.rightStick;
        turboButton = gamepad.leftStickButton;
        driveStraight = gamepad.rightTrigger.getAsButton(0.5);

        clawOpenButton = gamepad.leftBumper;
        clawCloseButton = gamepad.rightBumper;
        launchDroneButton = gamepad.ps_share;
    }

    public void bindDriveControls() {
        CommandScheduler
            .getInstance()
            .scheduleJoystick(
                new JoystickDriveCommand(robot.drivebaseSubsystem, driveLeftStick, driveRightStick)
            );
        //turboButton.whenPressed(new TurboCommand(robot.drivebaseSubsystem));
        //turboButton.whenReleased(new SlowCommand(robot.drivebaseSubsystem));
        resetGyroButton.whenPressed(new ResetGyroCommand(robot.drivebaseSubsystem));
    }

    public void bindClawControls() {
        clawOpenButton.whenPressed(new ClawOpenCommand(robot.clawSubsystem));
        clawCloseButton.whenPressed(new ClawCloseCommand(robot.clawSubsystem));
        //        armFirstLineButton.whenPressed(new 6)
    }

    public void bindDroneControls() {
        launchDroneButton.whenPressed(new DroneCommand(robot.droneSubsystem));
    }

    public void bindLiftControls() {
        // TODO: Name & Bind lift controls

    }
}
