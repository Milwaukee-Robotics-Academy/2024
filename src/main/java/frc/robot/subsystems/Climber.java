// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.Constants;


public class Climber extends SubsystemBase {
  private CANSparkMax m_RightClimber = new CANSparkMax(Constants.kRightClimberMortorPort, MotorType.kBrushed);
   private CANSparkMax m_LeftClimber = new CANSparkMax(Constants.kLeftClimberMotorPort, MotorType.kBrushed);
  /** Creates a new Climber. */
  public Climber() {
          m_LeftClimber.restoreFactoryDefaults();
      m_LeftClimber.setSmartCurrentLimit(80);
      m_LeftClimber.setIdleMode(IdleMode.kBrake);
      m_LeftClimber.setInverted(false);
      m_RightClimber.restoreFactoryDefaults();
      m_RightClimber.setSmartCurrentLimit(80);
      m_RightClimber.setIdleMode(IdleMode.kBrake);
      m_RightClimber.setInverted(true);
  }

  public void up() {
    m_RightClimber.set(-0.4);
    m_LeftClimber.set(-0.4);
  }
public void down() {
  m_RightClimber.set(0.4);
  m_LeftClimber.set(0.4);
}
public void stop() {
  m_RightClimber.set(0);
  m_LeftClimber.set(0);
}
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
 
   
    
    