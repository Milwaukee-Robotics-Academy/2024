// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

public final class Constants {
  public static final class DriveConstants {
    public static final int kLeftLeaderMotor = 1;
    public static final int kLeftFollowerMotor = 2;

    public static final int kRightLeaderMotor = 3;
    public static final int kRightFollowerMotor = 4;

    public static final int kFlywheelMotorPort = 5;
    public static final int kShooterMotorPort = 6;

    public static final boolean kLeftReversed = false;
    public static final boolean kRightReversed = true;

    public static final double kTrackwidthMeters = 0.56;
    public static final int kRangeFinderPort = 6;
    public static final int kAnalogGyroPort = 1;

    public static final int kEncoderCPR = 1024;
    public static final double kWheelDiameterInches = 6;
    public static final double kEncoderDistancePerPulse =
        // Assumes the encoders are directly mounted on the wheel shafts
        (kWheelDiameterInches * Math.PI) / (double) kEncoderCPR;
    public static final double kS = 0.11673;
    public static final double kV = 1.3815;
    public static final double kA = 0.12011;
    public static final int kForwardBackSlewRate = 3;
    public static final int kTurnSlewRate = 2;
  }
}
