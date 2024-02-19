// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Shooter;

public class SetShooterSetpoint extends Command {
  private final Shooter m_shooter;
  private final double m_setpoint;

  /**
   * Create a new SetElevatorSetpoint command.
   *
   * @param setpoint The setpoint to set the elevator to
   * @param shooter The elevator to use
   */
  public SetShooterSetpoint(double setpoint, Shooter shooter) {
    m_shooter = shooter;
    m_setpoint = setpoint;
    addRequirements(m_shooter);
  }

  // Called just before this Command runs the first time
  @Override
  public void initialize() {
    m_shooter.set(m_setpoint);
   
  }

}
