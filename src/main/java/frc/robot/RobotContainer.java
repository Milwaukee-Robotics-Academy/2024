// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;
import com.pathplanner.lib.commands.PathPlannerAuto;

import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.CommandGenericHID;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.commands.Shoot;
import frc.robot.commands.AmpShoot;
import frc.robot.commands.DriveForTime;
import frc.robot.commands.TankDrive;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Shooter;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in
 * the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of
 * the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final Drivetrain m_drivetrain = new Drivetrain();
  private final Shooter m_shooter = new Shooter();
  // private final Joystick m_joystick = new Joystick(0);
  private final CommandXboxController m_driver = new CommandXboxController(0);
 // private final CommandXboxController m_operator = new CommandXboxController(1);

  private final Command m_autonomousCommand;
  private final SlewRateLimiter m_ForwardBackLimiter = new SlewRateLimiter(
      Constants.DriveConstants.kForwardBackSlewRate);
  private final SlewRateLimiter m_TurnLimiter = new SlewRateLimiter(Constants.DriveConstants.kTurnSlewRate);

  private final SendableChooser<Command> autoChooser;

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Put Some buttons on the SmartDashboard

    // Assign default commands
    // m_drivetrain.setDefaultCommand(
    // new TankDrive(() -> -m_driver.getLeftY(), () -> -m_driver.getRightY(),
    // m_drivetrain));

    /**
     * Decide if you want to use Arcade drive
     */
    m_drivetrain.setDefaultCommand(
        new RunCommand(
            () -> m_drivetrain.arcadeDrive(
                m_ForwardBackLimiter.calculate(m_driver.getLeftY()),
                m_TurnLimiter.calculate(-m_driver.getRightX())),
            m_drivetrain));
    //  m_shooter.setDefaultCommand(
    //    new RunCommand(() ->  m_shooter.stop(), m_shooter)
    //  );
    m_autonomousCommand =  new Shoot(m_shooter).withTimeout(2).andThen( new DriveForTime(m_drivetrain, -.5, 2));

    // Build an auto chooser. This will use Commands.none() as the default option.
    autoChooser = AutoBuilder.buildAutoChooser();
    SmartDashboard.putData("Auto Chooser", autoChooser);

    // Show what command your subsystem is running on the SmartDashboard
    SmartDashboard.putData(m_drivetrain);
    SmartDashboard.putData(m_shooter);

    // Configure the button bindings
    configureButtonBindings();
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be
   * created by
   * instantiating a {@link edu.wpi.first.wpilibj.GenericHID} or one of its
   * subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing
   * it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    // Create some buttons
    initializeShooterControls();

  }

  /**
   * Configures the Note shooter controls, according to the shooter buttons
   * defined in the class.
   */
  private void initializeShooterControls() {
    // Connect the buttons to commands
    m_driver.start().onTrue(new InstantCommand(() -> m_drivetrain.invertControls()));
    m_driver.x().whileTrue(new Shoot(m_shooter));
   // m_driver.x().whileTrue(new RunCommand(() -> m_shooter.shoot(),m_shooter));
    m_driver.y().whileTrue(new RunCommand(() -> m_shooter.intake(),m_shooter).withName("Intake").finallyDo(() -> m_shooter.stop()));
    m_driver.b().onTrue(new RunCommand(() -> m_shooter.stop(),m_shooter).withName("Stop"));
    m_driver.a().whileTrue(new AmpShoot(m_shooter).withTimeout(5));
    SmartDashboard.putNumber("TopShooterMotor", 100.0);
    SmartDashboard.putNumber("BottomShooterMotor", 100.0);


  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
  // return m_autonomousCommand;
   return autoChooser.getSelected();
    // return new PathPlannerAuto("New New Auto");
  }

  public void namedCommand() {
    NamedCommands.registerCommand("Shoot",
        new SequentialCommandGroup(
            new InstantCommand(m_shooter::shoot),
            new WaitCommand(1.2),
            new InstantCommand(m_shooter::stop)));
  }

}
