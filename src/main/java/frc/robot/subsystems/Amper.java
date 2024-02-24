// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DriveConstants;

public class Amper extends SubsystemBase {
  /** Creates a new Amper. */
  public final CANSparkMax m_ampanator = new CANSparkMax(DriveConstants.kAmperMotorPort, MotorType.kBrushless);
  private RelativeEncoder m_ampanatorEncoder = m_ampanator.getEncoder();
  public Amper() {
      m_ampanator.restoreFactoryDefaults();
      m_ampanator.setSmartCurrentLimit(80);
      m_ampanator.setIdleMode(IdleMode.kBrake);
      m_ampanator.setInverted(false);
      SmartDashboard.putNumber("Amp-Encoder/speed",m_ampanatorEncoder.getVelocity());
      SmartDashboard.putNumber("Amp-Encoder/distance",m_ampanatorEncoder.getPosition());

    this.stop();
  }
  
  public void intake() {
    m_ampanator.set(0.2);
  }
  
  public void drop() {
    m_ampanator.set(-1);
  }
  
  public void stop() {
    m_ampanator.set(0);
  }

  public double getMotorSpeed() {
    return m_ampanator.getEncoder().getVelocity();
  }
  
  public void log() 
  {
  //  SmartDashboard.putData("Shooter Speed", m_pot);
  }
  

  public void periodic() 
  {
    log();
    SmartDashboard.putNumber("AmperMotorSpeed", getMotorSpeed());
    
  }
}
