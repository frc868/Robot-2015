package com.techhounds.subsystems;

import com.techhounds.MultiMotor;
import com.techhounds.Robot;
import com.techhounds.RobotMap;
import com.techhounds.commands.lift.RunLift;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Victor;

/**
 * @author Alex Fleig, Matt Simons, Ayon Mitra, Clayton Detke
 */
public class LiftSubsystem extends BasicSubsystem {	
	
	private static LiftSubsystem instance;
	
	public static final double LIFT_POWER = 0.5;
	
	public static final int UP = 1;
	public static final int DOWN = 2;
	public static final int STOPPED = 3;
	
	public static final boolean IN = false;
	public static final boolean OUT = true;
	
	private int direction = STOPPED;
	public double power = 0;
	
	private MultiMotor motors;
	
	private Solenoid sol;
	
	private DigitalInput checkTop;
	private DigitalInput checkBottom;
	
	private LiftSubsystem() {
		motors = new MultiMotor(
					new Victor[]{
							new Victor(RobotMap.Lift.LIFT_MOTOR_1),
							new Victor(RobotMap.Lift.LIFT_MOTOR_2)},
					new boolean[]{false, false}
				);
		checkTop = new DigitalInput(RobotMap.Lift.DIGITAL_INPUT_TOP);
		checkBottom = new DigitalInput(RobotMap.Lift.DIGITAL_INPUT_BOTTOM);
		sol = new Solenoid(RobotMap.Lift.LIFT_SOL);
	}
	
	public static LiftSubsystem getInstance() {
		if (instance == null) {
			instance = new LiftSubsystem();
		}
		return instance;
	}
	
	public boolean isAtTop() {
		return !checkTop.get();
	}
	
	public boolean isAtBottom() {
		return !checkBottom.get();
	}
	
	public double getPower() {
		return Math.abs(motors.get());
	}
	
	public int getDirection(){
		return direction;
	}
	
	public void setPower() {
		motors.set(power);
	}
	
	public void setLift(int dir, double power) {
		
		power = Math.max(Math.min(power, 1), 0);
		
		if (dir == UP){
			power *= -1;
		}else if (dir == STOPPED){
			power = 0;
		}
		
		this.direction = dir;
		this.power = power;
	}
	
	public void stopLift() {
		setLift(STOPPED, 0);
	}
	
	public boolean getPosition() {
		return sol.get();
	}
	
	public void setPosition(boolean position) {
		sol.set(position);
	}

	@Override
	public void updateSmartDashboard() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void initDefaultCommand() {
		setDefaultCommand(new RunLift());
		
	}
	

}
