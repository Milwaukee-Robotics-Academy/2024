// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkLowLevel.MotorType;

import frc.robot.Constants.DriveConstants;
import frc.robot.Constants.ElevatorConstants;
import frc.robot.Robot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/**
 * The elevator subsystem uses PID to go to a given height. Unfortunately, in it's current state PID
 * values for simulation are different than in the real world do to minor differences.
 */
public class Shooter extends SubsystemBase 
{
    private CANSparkMax m_flywheel = new CANSparkMax(DriveConstants.kFlywheelMotorPort, MotorType.kBrushless);
    private RelativeEncoder m_encoder = m_flywheel.getEncoder();
    private CANSparkMax m_trigger_motor = new CANSparkMax(DriveConstants.kShooterMotorPort, MotorType.kBrushless);

    public Shooter()
    {
      this.stop();
    }

  public void set(Double speed)
  {
    m_trigger_motor.set(speed);
    m_flywheel.set(speed);
  }

  public void stop()
  {
    m_trigger_motor.stopMotor();
    m_flywheel.stopMotor();
  }


  public void intake()
  {
    m_trigger_motor.set(0.25);
    m_flywheel.set(0.25);
  }

  public void readyFlywheel()
  {
    m_flywheel.set(-1);
  }

  public void shoot()
  {
    m_trigger_motor.set(-1);
  }
  /** The log method puts interesting information to the SmartDashboard. */
  public void log() 
  {
  //  SmartDashboard.putData("Shooter Speed", m_pot);
  }


  /** Call log method every loop. */
  @Override
  public void periodic() 
  {
    log();
  }
}
