package frc.robot.subsystems;

import edu.wpi.first.wpilibj.XboxController.Axis;
import edu.wpi.first.wpilibj.XboxController.Button;

/**
 * A group of button bindings for tank-driving a robot.
 */
public class TankDriveControls
{
    private Axis panAxis = Axis.kRightX;
    private Axis driveAxis = Axis.kLeftY;
    private Button brakeButton = Button.kRightBumper;
    private Button slowButton = Button.kLeftBumper;
    private Button ramButton = Button.kLeftStick;

    /**
     * A default set of tank drive controls:<br>
     *  - Right stick x: pan axis<br>
     *  - Left stick y: drive axis<br>
     *  - Right bumper: brake button<br>
     *  - Left bumper: slow button<br>
     *  - Left stick button: ram button
     */
    public TankDriveControls() {}

    /**
     * Configure the set of tank drive controls.
     * Leave fields null if you want their defaults, as defined in the no-argument constructor. 
     * @param panAxis The joystick axis pans the robot.
     * @param driveAxis The joystick axis that drives the robot.
     * @param brakeButton The button that slows the robot to a halt.
     * @param slowButton The button that slows the robot down for more precise movement.
     * @param ramButton The button that speeds the robot up so it can ram things.
     */
    public TankDriveControls(Axis panAxis, Axis driveAxis, Button brakeButton, Button slowButton, Button ramButton)
    {
        if (panAxis != null)
        {
            this.panAxis = panAxis;
        }
        if (driveAxis != null)
        {
            this.driveAxis = driveAxis;
        }
        if (brakeButton != null)
        {
            this.brakeButton = brakeButton;
        }
        if (slowButton != null)
        {
            this.slowButton = slowButton;
        }
        if (ramButton != null)
        {
            this.ramButton = ramButton;
        }
    }
}