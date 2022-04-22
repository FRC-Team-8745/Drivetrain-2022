package frc.robot;

import static frc.robot.constants.Constants.*;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj2.command.*;

public class Shooter extends SubsystemBase {
	public static CANSparkMax shooter = new CANSparkMax(3, MotorType.kBrushless);
	public static RelativeEncoder encoder = shooter.getEncoder();

	public double RPM;
	public double Ramp;
	public double Time;
	// public boolean canLoad;

	@Override
	public void periodic() {
		RPM = Robot.turret.getShooterRPMFromDistance(Robot.limelight.getDistance()) + shooterSpeedOffset;
		Time = Robot.turret.getShooterTimingFromDistance(Robot.limelight.getDistance());
		Ramp = Robot.turret.getShooterRampFromDistance(Robot.limelight.getDistance());
	}

	public Shooter() {
		// Set shooter ramp rate
		shooter.setOpenLoopRampRate(0.5);
	}

	// Stop the shooter
	public void stop() {
		shooter.set(0);
	}

	// Sets the RPM of the shooter motor
	public void setRPM(double rpm) {
		shooter.setVoltage((rpm / kmaxRPM) * kbatteryMax);
	}

	public void unloadSingle() {
		System.out.println("Unloading");
		// Unload a single ball from the loader & shooter
		new SequentialCommandGroup(
				new InstantCommand(() -> Robot.loader.set(-1)),
				new InstantCommand(() -> shooter.set(-0.2)),
				new WaitCommand(2),
				new InstantCommand(() -> Robot.loader.stopMotor()),
				new InstantCommand(() -> shooter.set(0)),
				new InstantCommand(() -> System.out.println("done"))).schedule();
	}

	// Load a single ball to a point directly before the shooter
	SequentialCommandGroup loadSingle = new SequentialCommandGroup(
			new InstantCommand(() -> Robot.loader.set(1)),
			new WaitCommand(1.5),
			new InstantCommand(() -> Robot.loader.stopMotor()));

	public void shootSingle() {
		// Shoot the ball at full speed
		new SequentialCommandGroup(
				new InstantCommand(() -> setRPM(kFenderSpeed)),
				new WaitCommand(1.5),
				new InstantCommand(() -> Robot.loader.set(1)),
				new WaitCommand(1),
				new InstantCommand(() -> Robot.loader.stopMotor()),
				new InstantCommand(() -> shooter.set(0))).schedule();
	}

	public void shootDouble() {
		// Shoot two balls at full speed
		new SequentialCommandGroup(
				new InstantCommand(() -> setRPM(RPM)),
				new WaitCommand(2),
				new InstantCommand(() -> Robot.loader.set(1)),
				new WaitCommand(1.75),
				new InstantCommand(() -> Robot.loader.stopMotor()),
				new WaitCommand(Time),
				new InstantCommand(() -> Robot.loader.set(1)),
				new WaitCommand(1.25),
				new InstantCommand(() -> Robot.loader.stopMotor()),
				new InstantCommand(() -> shooter.set(0))).schedule();
	}

	public void shootFull() {
		// Shoot and load a single ball
		new SequentialCommandGroup(
				new InstantCommand(() -> setRPM(kFenderSpeed)),
				new InstantCommand(() -> Robot.loader.set(1)),
				new WaitCommand(4),
				new InstantCommand(() -> shooter.set(0)),
				new InstantCommand(() -> Robot.loader.stopMotor())).schedule();
	}
}