// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkLowLevel.MotorType;

import frc.robot.Constants.ElevatorConstants;
import frc.robot.Robot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/**
 * The elevator subsystem uses PID to go to a given height. Unfortunately, in it's current state PID
 * values for simulation are different than in the real world do to minor differences.
 */
public class Shooter extends SubsystemBase {
    private CANSparkMax m_motor = new CANSparkMax(3, MotorType.kBrushless);
    private RelativeEncoder m_encoder= m_motor.getEncoder();

  public void set(Double speed){
    m_motor.set(speed);
  }

  public void stop(){
    m_motor.set(0);
  }
  /** The log method puts interesting information to the SmartDashboard. */
  public void log() {
  //  SmartDashboard.putData("Shooter Speed", m_pot);
  }


  /** Call log method every loop. */
  @Override
  public void periodic() {
    log();
  }
}
