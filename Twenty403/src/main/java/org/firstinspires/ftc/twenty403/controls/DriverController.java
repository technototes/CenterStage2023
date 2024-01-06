package org.firstinspires.ftc.twenty403.controls;

import com.technototes.library.command.CommandScheduler;
import com.technototes.library.control.CommandAxis;
import com.technototes.library.control.CommandButton;
import com.technototes.library.control.CommandGamepad;
import com.technototes.library.control.Stick;
import org.firstinspires.ftc.twenty403.Robot;
import org.firstinspires.ftc.twenty403.Setup;
import org.firstinspires.ftc.twenty403.commands.arm.ArmIntakeCommand;
import org.firstinspires.ftc.twenty403.commands.arm.ArmNeutralCommand;
import org.firstinspires.ftc.twenty403.commands.arm.ArmSecondLineCommand;
import org.firstinspires.ftc.twenty403.commands.arm.HangSequential;
import org.firstinspires.ftc.twenty403.commands.arm.IntakeManualSlurpCommand;
import org.firstinspires.ftc.twenty403.commands.arm.IntakeSpitCommand;
import org.firstinspires.ftc.twenty403.commands.arm.IntakeStopCommand;
import org.firstinspires.ftc.twenty403.commands.arm.ShoulderDecrementCommand;
import org.firstinspires.ftc.twenty403.commands.arm.ShoulderIncrementCommand;
import org.firstinspires.ftc.twenty403.commands.arm.ShoulderNeutralCommand;
import org.firstinspires.ftc.twenty403.commands.arm.WristDecrementCommand;
import org.firstinspires.ftc.twenty403.commands.arm.WristIncrementCommand;
import org.firstinspires.ftc.twenty403.commands.driving.JoystickDriveCommand;
import org.firstinspires.ftc.twenty403.commands.driving.NormalModeCommand;
import org.firstinspires.ftc.twenty403.commands.driving.ResetGyroCommand;
import org.firstinspires.ftc.twenty403.commands.driving.SnailModeCommand;
import org.firstinspires.ftc.twenty403.commands.driving.TurboModeCommand;

public class DriverController {

    public Robot robot;
    public CommandGamepad gamepad;

    public Stick driveLeftStick, driveRightStick;
    public CommandButton resetGyroButton;
    public CommandButton turboButton;
    public CommandButton snailButton;
    public CommandAxis straightTrigger;
    public CommandButton HangButton;
    public CommandButton HangDown;

    public DriverController(CommandGamepad g, Robot r) {
        this.robot = r;
        gamepad = g;

        AssignNamedControllerButton();
        if (Setup.Connected.DRIVEBASE) {
            bindDriveControls();
        }
    }

    private void AssignNamedControllerButton() {
        resetGyroButton = gamepad.ps_options;
        driveLeftStick = gamepad.leftStick;
        driveRightStick = gamepad.rightStick;
        turboButton = gamepad.rightBumper;
        snailButton = gamepad.leftBumper;
        straightTrigger = gamepad.rightTrigger;
        HangButton = gamepad.ps_share;
    }

    public void bindDriveControls() {
        CommandScheduler
            .getInstance()
            .scheduleJoystick(
                new JoystickDriveCommand(
                    robot.drivebaseSubsystem,
                    driveLeftStick,
                    driveRightStick,
                    straightTrigger
                )
            );

        turboButton.whenPressed(new TurboModeCommand(robot.drivebaseSubsystem));
        turboButton.whenReleased(new NormalModeCommand(robot.drivebaseSubsystem));

        snailButton.whenPressed(new SnailModeCommand(robot.drivebaseSubsystem));
        snailButton.whenReleased(new NormalModeCommand(robot.drivebaseSubsystem));

        resetGyroButton.whenPressed(new ResetGyroCommand(robot.drivebaseSubsystem));
        HangButton.whenPressed(new HangSequential(robot.armSubsystem));
    }
}
