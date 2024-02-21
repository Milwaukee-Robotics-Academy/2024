// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

public final class Constants {
  public static final class DriveConstants {
    public static final int kLeftMotorPort1 = 1;
    public static final int kLeftMotorPort2 = 2;

    public static final int kRightMotorPort1 = 3;
    public static final int kRightMotorPort2 = 4;

    public static final int kFlywheelMotorPort = 6;
    public static final int kShooterMotorPort = 5;

    public static final int kAmperMotorPort = 7;

    public static final int[] kLeftEncoderPorts = {0, 1};
    public static final int[] kRightEncoderPorts = {2, 3};
    public static final boolean kLeftEncoderReversed = false;
    public static final boolean kRightEncoderReversed = false;

    public static final int kRangeFinderPort = 6;
    public static final int kAnalogGyroPort = 1;

    public static final int kEncoderCPR = 1024;
    public static final double kWheelDiameterInches = 6;
    public static final double kEncoderDistancePerPulse =
        // Assumes the encoders are directly mounted on the wheel shafts
        (kWheelDiameterInches * Math.PI) / (double) kEncoderCPR;

    public static final int kForwardBackSlewRate = 1;
    public static final int kTurnSlewRate = 1;
    public static final double kForwardBackSlewThreshold = 0; 
    public static final double kTurnSlewThreshold = 1; // TODO change this
  }
}
