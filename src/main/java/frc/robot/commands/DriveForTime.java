// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Drivetrain;

public class DriveForTime extends Command {
  Drivetrain drivetrain;
  double timeToDrive;
  Timer timer=new Timer();

  /** Creates a new DriveForTime. */
  public DriveForTime(Drivetrain drivetrain, double timeToDrive) {
    this.drivetrain = drivetrain;
    this.timeToDrive=timeToDrive;
    addRequirements (drivetrain);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    timer.restart();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    drivetrain.arcadeDrive(.7, 0);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    drivetrain.arcadeDrive(0,0 );
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return timer.get() > timeToDrive;
  }
}
