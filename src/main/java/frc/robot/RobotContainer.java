// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;


import com.pathplanner.lib.commands.PathPlannerAuto;

import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.commands.Shoot;
import frc.robot.subsystems.Amper;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Shooter;
import com.pathplanner.lib.auto.NamedCommands;

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
  private final Amper m_amper = new Amper();
  // private final Joystick m_joystick = new Joystick(0);
  private final CommandXboxController m_driver = new CommandXboxController(0);

  private final Command m_autonomousCommand;
  private final SlewRateLimiter m_ForwardBackLimiter = new SlewRateLimiter(
      Constants.DriveConstants.kForwardBackSlewRate);
  private final SlewRateLimiter m_TurnLimiter = new SlewRateLimiter(Constants.DriveConstants.kTurnSlewRate);


  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Register note shooter command
    NamedCommands.registerCommand("shooter", 
    new InstantCommand(
      () -> {
        m_shooter.readyFlywheel();
        try 
        {
          Thread.sleep(2000);
        } 
        catch (InterruptedException e) {}
        m_shooter.shoot();
        try
        {
          Thread.sleep(1000);
        }
        catch (InterruptedException e) {}
        m_shooter.stop();
      }
    )    
    );
    NamedCommands.registerCommand("intake",
    new InstantCommand(
      () -> {
        m_shooter.intake();
        try 
        {
          Thread.sleep(5000);
        } 
        catch (InterruptedException e) {}
        m_shooter.stop();           
      }
    )

    );

    // Assign default commands
    // m_drivetrain.setDefaultCommand(
    //     new TankDrive(() -> -m_driver.getLeftY(), () -> -m_driver.getRightY(), m_drivetrain));

    /**
     * Decide if you want to use Arcade drive
     */
    m_drivetrain.setDefaultCommand(
        new RunCommand(
          () -> m_drivetrain.arcadeDrive(
                  // Forward/back motion is controlled by the left stick Y. Can not limit acceleration if the left trigger is held, and will not limit it until speed is high enough.
                  applySlewRateLimiterIfTrue(-m_driver.getLeftY(), m_ForwardBackLimiter, (!m_driver.leftTrigger().getAsBoolean() && m_drivetrain.getRobotSpeed() > Constants.DriveConstants.kForwardBackSlewThreshold)),
                  // Turning is controlled by the right stick X. Applies an acceleration limiter if the right trigger is held.
                  applySlewRateLimiterIfTrue(m_driver.getRightX(), m_TurnLimiter, !(m_driver.rightTrigger().getAsBoolean()))
                ),
          m_drivetrain
          )
        );
    

    m_autonomousCommand = new WaitCommand(1);

    // Show what command your subsystem is running on the SmartDashboard
    SmartDashboard.putData(m_drivetrain);
    SmartDashboard.putData(m_shooter);

    // Configure the button bindings
    configureButtonBindings();
  }

  /**
   * Applies a slew rate limiter to a drive input if the provided condition is true.
   * @param driveInput A double input indicating how far a joystick is on an axis, trigger strength, etc.
   * @param slewRateLimiter The slew rate limiter to apply to the driveInput.
   * @param condition The condition where, if true, applies the slewRateLimiter.
   * @return The modified driveInput.
   */
  public static double applySlewRateLimiterIfTrue(double driveInput, SlewRateLimiter slewRateLimiter, boolean condition)
  {
    if (condition)
    {
      return slewRateLimiter.calculate(driveInput);
    }
    return driveInput;
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
   * Configures the Note shooter and amper controls, according to the bindings buttons 
   * (see Controller Bindings.txt)
   * defined in the class.
   */
  private void initializeShooterControls() {
    // Connect the buttons to commands
    
    m_driver.x().whileTrue(new Shoot(m_shooter).withTimeout(5).handleInterrupt(() -> m_shooter.stop()));
    m_driver.y().whileTrue(new InstantCommand(() -> m_shooter.intake()).handleInterrupt(() -> m_shooter.stop()));
    m_driver.b().onTrue(new InstantCommand(() -> m_shooter.stop()));
    m_driver.a().onTrue(new InstantCommand(() -> m_shooter.readyFlywheel()));
    m_driver.povDown().onTrue(new InstantCommand(() -> m_amper.stop()));
    m_driver.povUp().onTrue(new InstantCommand(() -> m_amper.intake()));
    m_driver.povLeft().onTrue(new InstantCommand(() -> m_amper.drop()));

    
    SmartDashboard.putNumber("TopShooterMotor", 100.0);
    SmartDashboard.putNumber("BottomShooterMotor", 100.0);
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
  return new PathPlannerAuto("Intake Test 1");
  }
}
