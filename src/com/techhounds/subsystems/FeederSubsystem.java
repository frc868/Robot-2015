package com.techhounds.subsystems;

import com.techhounds.MultiCANTalon;
import com.techhounds.Robot;
import com.techhounds.RobotMap;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.ControlMode;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class FeederSubsystem extends BasicSubsystem {
    
	/*
	 *-----------------This Subsystem is Final Robot ONLY!---------------------------
	 */
	
	private static FeederSubsystem instance;
	
	public static final double FEED_IN = -0.75, FEED_OUT = 0.75, STOPPED = 0;
	public static final boolean OPEN = false, CLOSED = true;
	public static final double MIN_RIGHT_DIST = .5, MIN_LEFT_DIST = .5;
	
	private double leftMotorMult = 1, rightMotorMult = 1;
	private boolean solEnabled, motorsEnabled, leftEnabled, rightEnabled;
	
	private MultiCANTalon motors;
	private AnalogInput left, right;
	private Solenoid sol;
	
	private FeederSubsystem() {
		super("ArmsSubsystem");
		
		if (!Robot.isFinal()){
			solEnabled = false;
			motorsEnabled = false;
			leftEnabled = false;
			rightEnabled = false;
			return;
		}
					
		if (motorsEnabled = (RobotMap.Feeder.LEFT_MOTOR != RobotMap.DOES_NOT_EXIST && 
				RobotMap.Feeder.RIGHT_MOTOR != RobotMap.DOES_NOT_EXIST)){
			motors = new MultiCANTalon(
					new CANTalon[]{
						new CANTalon(RobotMap.Feeder.LEFT_MOTOR),
						new CANTalon(RobotMap.Feeder.RIGHT_MOTOR)},
					new boolean[]{true, true},
					FeedbackDevice.QuadEncoder,
					false, false, false, false, false);
		}
				
		if(solEnabled = RobotMap.Feeder.SOL != RobotMap.DOES_NOT_EXIST)
			sol = new Solenoid(RobotMap.Feeder.SOL);
		
		if(leftEnabled = RobotMap.Feeder.LEFT_SENSOR != RobotMap.DOES_NOT_EXIST)
			left = new AnalogInput(RobotMap.Feeder.LEFT_SENSOR);
		
		if(rightEnabled = RobotMap.Feeder.RIGHT_SENSOR != RobotMap.DOES_NOT_EXIST)
			right = new AnalogInput(RobotMap.Feeder.RIGHT_SENSOR);
	}
	
	public static FeederSubsystem getInstance() {
		if (instance == null)
			instance = new FeederSubsystem();
		return instance;
	}
		
	public double getPower() {
		return motorsEnabled ? motors.get() : 0;
	}
	
	public void setPower(double power) {
		if (motorsEnabled)
			motors.set(power);
	}
	
	public boolean getPosition() {
		return solEnabled ? sol.get() : OPEN;
	}
	
	public void setPosition(boolean direction) {
		if (solEnabled)
			sol.set(direction);
	}
	
	public double getLeftSensor() {
		return leftEnabled ? left.getVoltage() : MIN_LEFT_DIST;
	}
	
	public double getRightSensor() {
		return rightEnabled ? right.getVoltage() : MIN_RIGHT_DIST;
	}
	
	public double getLeftDistance() {
		double vol = getLeftSensor();

		return -0.0046 * Math.pow(vol, 4) + 0.669 * Math.pow(vol, 3) - 0.2921 * Math.pow(vol, 2) + 0.1078 * vol + 2;
	}
	
	public double getRightDistance(){
		double vol = getRightSensor();

		return -Math.pow(5, -15) * Math.pow(vol, 4) - 0.0037 * Math.pow(vol, 3) + 
				0.0778 * Math.pow(vol, 2) - 0.555 * vol + 1.9968;
	}
	
	public boolean getRightSensorInRange(){
//		return getRightDistance() < MIN_RIGHT_DIST;
		return getRightSensor() > 1.7;
	}
	
	public boolean getLeftSensorInRange(){
//		return getLeftDistance() < MIN_LEFT_DIST;
		return getLeftSensor() > 1.7;
	}
	
	public void stopArms() {
		setPower(0);
	}
	
	public void updateSmartDashboard() {
		SmartDashboard.putNumber("Left Feeder Sensor", getLeftSensor());
		SmartDashboard.putNumber("Right Feeder Sensor", getRightSensor());
		SmartDashboard.putNumber("Left Feeder Sensor Dist", getLeftDistance());
		SmartDashboard.putNumber("Right Feeder Sensor Dist", getRightDistance());
		SmartDashboard.putBoolean("Collector Out", getPosition());
//		SmartDashboard.putString("Feeder Direction", getPower() > 0 ? "OUT" : getPower() == 0 ? "STOPPED" : "IN");
	}

    public void initDefaultCommand() {
        
    }
}

