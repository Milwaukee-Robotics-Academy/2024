// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.util.sendable.SendableRegistry;
import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.Constants.DriveConstants;
import frc.robot.Robot;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Drivetrain extends SubsystemBase {
  // The Drivetrain subsystem incorporates the sensors and actuators attached to
  // the robots chassis.
  // These include four drive motors, a left and right encoder and a gyro.

  private final CANSparkMax m_leftLeader = new CANSparkMax(DriveConstants.kLeftMotorPort1, MotorType.kBrushless);
  private final CANSparkMax m_leftFollower = new CANSparkMax(DriveConstants.kLeftMotorPort2, MotorType.kBrushless);
  private final CANSparkMax m_rightLeader = new CANSparkMax(DriveConstants.kRightMotorPort1, MotorType.kBrushless);
  private final CANSparkMax m_rightFollower = new CANSparkMax(DriveConstants.kRightMotorPort2, MotorType.kBrushless);
  private RelativeEncoder m_leftEncoder;
  private RelativeEncoder m_rightEncoder;

  private AHRS m_gyro;
  private Rotation2d m_gyroOffset = new Rotation2d();

  // Odometry class for tracking robot pose
  private final DifferentialDriveOdometry m_odometry;

  private final DifferentialDrive m_drive = new DifferentialDrive(m_leftLeader::set, m_rightLeader::set);

  /** Create a new drivetrain subsystem. */
  public Drivetrain() {
    super();

    SendableRegistry.addChild(m_drive, m_leftLeader);
    SendableRegistry.addChild(m_drive, m_rightLeader);

    m_leftLeader.restoreFactoryDefaults();
    m_rightLeader.restoreFactoryDefaults();
    m_leftFollower.restoreFactoryDefaults();
    m_rightFollower.restoreFactoryDefaults();

    m_leftFollower.follow(m_leftLeader);
    m_rightFollower.follow(m_rightLeader);

    // We need to invert one side of the drivetrain so that positive voltages
    // result in both sides moving forward. Depending on how your robot's
    // gearbox is constructed, you might have to invert the left side instead.
    m_rightLeader.setInverted(true);

    m_gyro = new AHRS(SPI.Port.kMXP);

    m_rightLeader.setOpenLoopRampRate(0.2);
    m_leftLeader.setOpenLoopRampRate(0.2);

    double conversionFactor = 0.1524 * Math.PI / 10.71;
    //
    double velocityConversionFactor = (1 / 60.0) * (0.1524 * 2 * Math.PI) / 10.71;
    m_leftEncoder = m_leftLeader.getEncoder();
    m_leftEncoder.setPositionConversionFactor(conversionFactor);
    m_leftEncoder.setVelocityConversionFactor(velocityConversionFactor);
    m_rightEncoder = m_rightLeader.getEncoder();
    m_rightEncoder.setPositionConversionFactor(conversionFactor);
    m_rightEncoder.setVelocityConversionFactor(velocityConversionFactor);
    resetEncoders();
    m_odometry = new DifferentialDriveOdometry(
        m_gyro.getRotation2d(), m_leftEncoder.getPosition(), m_rightEncoder.getPosition());

    // Let's name the sensors on the LiveWindow
    addChild("Drive", m_drive);
    addChild("Gyro", m_gyro);
  }

  /** The log method puts interesting information to the SmartDashboard. */
  public void log() {
    SmartDashboard.putNumber("Left Distance", m_leftEncoder.getPosition());
    SmartDashboard.putNumber("Right Distance", m_rightEncoder.getPosition());
    SmartDashboard.putNumber("Left Speed", m_leftEncoder.getVelocity());
    SmartDashboard.putNumber("Right Speed", m_rightEncoder.getVelocity());
    SmartDashboard.putNumber("Gyro", m_gyro.getAngle());
  }

  /**
   * Tank style driving for the Drivetrain.
   *
   * @param left  Speed in range [-1,1]
   * @param right Speed in range [-1,1]
   */
  public void drive(double left, double right) {
    m_drive.tankDrive(left, right);
  }

  /**
   * Get the robot's heading.
   *
   * @return The robots heading in degrees.
   */
  public double getHeading() {
    return m_gyro.getAngle();
  }

  /** Reset the robots sensors to the zero states. */
  public void resetEncoders() {
    m_leftEncoder.setPosition(0);
    m_rightEncoder.setPosition(0);
  }

  /** Zeroes the heading of the robot. */
  public void zeroHeading(Rotation2d rotation) {
    m_gyro.zeroYaw();
    m_gyroOffset = rotation;
  }

  /**
   * Returns the currently-estimated pose of the robot.
   *
   * @return The pose.
   */
  public Pose2d getPose() {
    return m_odometry.getPoseMeters();
  }

  /**
   * Returns the heading of the robot.
   *
   * @return the robot's heading
   */
  public Rotation2d getGyroRotation2d() {
    return Rotation2d.fromDegrees(-m_gyro.getAngle()).minus(m_gyroOffset);
  }

  public double getGyroRotation2dDegrees() {
    return getGyroRotation2d().getDegrees();
  }

  /**
   * Returns the turn rate of the robot.
   *
   * @return The turn rate of the robot, in degrees per second
   */
  public double getTurnRate() {
    return -m_gyro.getRate();
  }

  /**
   * Resets the odometry to the specified pose.
   *
   * @param pose The pose to which to set the odometry.
   */
  public void resetOdometry(Pose2d pose) {
    resetEncoders();
    zeroHeading(pose.getRotation());
    m_odometry.resetPosition(
        getGyroRotation2d(), m_leftEncoder.getPosition(), m_rightEncoder.getPosition(), pose);
  }

  /**
   * Get the average distance of the encoders since the last reset.
   *
   * @return The distance driven (average of left and right encoders).
   */
  public double getDistance() {
    return (m_leftEncoder.getPosition() + m_rightEncoder.getPosition()) / 2;
  }

  /** Call log method every loop. */
  @Override
  public void periodic() {
    log();
  }
}
