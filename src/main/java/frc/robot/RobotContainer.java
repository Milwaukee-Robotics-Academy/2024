// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.Shoot;
import frc.robot.commands.TankDrive;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Shooter;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final Drivetrain m_drivetrain = new Drivetrain();
  private final Shooter m_shooter = new Shooter();
  // private final Joystick m_joystick = new Joystick(0);
  private final XboxController m_driver = new XboxController(0);
  
  private JoystickButton startFlywheel = new JoystickButton(m_driver, XboxController.Button.kX.value);
  private JoystickButton shootNote = new JoystickButton(m_driver, XboxController.Button.kA.value);
  private JoystickButton startIntake = new JoystickButton(m_driver, XboxController.Button.kY.value);
  private JoystickButton stopFlywheel = new JoystickButton(m_driver, XboxController.Button.kB.value);

  private final Command m_autonomousCommand;

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Put Some buttons on the SmartDashboard
    

    // Assign default commands
    m_drivetrain.setDefaultCommand(
        new TankDrive(() -> -m_driver.getLeftY(), () -> -m_driver.getRightY(), m_drivetrain));
    
    m_autonomousCommand = new WaitCommand(1);

    // Show what command your subsystem is running on the SmartDashboard
    SmartDashboard.putData(m_drivetrain);
    SmartDashboard.putData(m_shooter);

    // Configure the button bindings
    configureButtonBindings();
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link edu.wpi.first.wpilibj.GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    // Create some buttons
    initializeShooterControls();



  }

  /**
   * Configures the Note shooter controls, according to the shooter buttons defined in the class.
   */
  private void initializeShooterControls()
  {
    // Connect the buttons to commands
    startFlywheel.onTrue(new InstantCommand(() -> m_shooter.readyFlywheel()));
    shootNote.whileTrue(new Shoot(m_shooter));
    startIntake.onTrue(new InstantCommand(() -> m_shooter.intake()));
    stopFlywheel.onTrue(new InstantCommand(() -> m_shooter.stop()));
    SmartDashboard.putNumber("LeftSparkMotor", 0.0);
    SmartDashboard.putNumber("RightSparkMotor", 0.0);
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    return m_autonomousCommand;
  }
}
