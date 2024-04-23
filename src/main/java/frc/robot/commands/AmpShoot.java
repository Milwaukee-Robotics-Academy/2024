// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Shooter;

public class AmpShoot extends Command {

 private Shooter m_shooter;

    private double m_topMotorSpeed;
    private double m_bottomMotorSpeed;
  /** Creates a new Shoot. */
    public AmpShoot(Shooter shooter) {
        m_shooter = shooter;
        addRequirements(m_shooter);
    }

    @Override
    public void initialize() {

        double low = -.2;
        double high = .2;
        m_topMotorSpeed = MathUtil.clamp(SmartDashboard.getNumber("TopShooterMotor", 0.0)/100, low, high); //Converts the inputted percentage
        m_bottomMotorSpeed = MathUtil.clamp(SmartDashboard.getNumber("BottomShooterMotor", 0.0)/100, low, high); //Converts the inputted percentage

        m_shooter.setMotorSpeed(m_topMotorSpeed, m_bottomMotorSpeed);
       // m_shooter.setMotorSpeed(1.0,1.0);
    }

    @Override
    public void execute() {


    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        m_shooter.stop();
    }
}
