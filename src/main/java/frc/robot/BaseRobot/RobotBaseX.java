package frc.robot.BaseRobot;

//Controllers
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Joystick;

//Motor Control
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;

import edu.wpi.first.math.controller.PIDController;

//Base Robot
import edu.wpi.first.wpilibj.PowerDistribution;

//Other
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class RobotBaseX {
    // Controllers
    public static XboxController xbox = new XboxController(1);
    public static Joystick main = new Joystick(0);

    // Motor Control
    public static CANSparkMax rightDrive = new CANSparkMax(1, MotorType.kBrushless);
    public static CANSparkMax leftDrive = new CANSparkMax(2, MotorType.kBrushless);

    // Sensors
    public static RelativeEncoder rightEncoder = rightDrive.getEncoder();
    public static RelativeEncoder leftEncoder = leftDrive.getEncoder();

    // Base robot
    public static PowerDistribution PDP = new PowerDistribution();

    // PID
    public static PIDController pid = new PIDController(0.5, 0.01, 4);


    // Shuffleboard
    public static void shuffleboard() {
        SmartDashboard.putNumber("Right Encoder", rightEncoder.getPosition());
        SmartDashboard.putNumber("Left Encoder", leftEncoder.getPosition());
        SmartDashboard.putNumber("Right Motor Temprature", rightDrive.getMotorTemperature());
        SmartDashboard.putNumber("Left Motor Temprature", leftDrive.getMotorTemperature());
    }

    public static void initStuff() {
        rightDrive.restoreFactoryDefaults();
        leftDrive.restoreFactoryDefaults();
        leftDrive.setInverted(true);
    }
}