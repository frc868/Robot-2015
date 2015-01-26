package com.techhounds;

import com.techhounds.commands.Debug;
import com.techhounds.commands.auton.AutonChooser;
import com.techhounds.commands.driving.OperatorHalfDrive;
import com.techhounds.commands.driving.ToggleDriveMode;
import com.techhounds.commands.lift.RunLift;
import com.techhounds.commands.lift.SetLift;
import com.techhounds.subsystems.LiftSubsystem;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * @author Atif Niyaz, Alex Fleig, Matt Simmons, Ayon Mitra, Clayton Detke
 */
public class OI {
	
	private static OI instance;
	
	private static ControllerMap driver;
	private static ControllerMap operator;
	
	private SendableChooser autonChoice;
	
	private boolean isInit;
	
	private static final double DEADZONE = 0.05;
		
	//Driver buttons
	private final int toggleForward = ControllerMap.START;
    private final int toggleHalf = ControllerMap.BACK;
//    private final int toggleLEDs = ControllerMap.BACK;
    private final int liftUp = ControllerMap.Y;
    private final int liftDown = ControllerMap.A;
    private final int liftIn = ControllerMap.X;
    private final int liftOut = ControllerMap.B;
    
    //Tweaker buttons
  	private final int opToggleForward = ControllerMap.START;
    private final int operatorPushHalf = ControllerMap.LB;
    private final int opLiftUp = ControllerMap.Y;
    private final int opLiftDown = ControllerMap.A;
    private final int opLiftIn = ControllerMap.X;
    private final int opLiftOut = ControllerMap.B;
	
	public OI() {
		
		driver = new ControllerMap(new Joystick(RobotMap.Drive.DRIVER_PORT), ControllerMap.LOGITECH);
		operator = new ControllerMap(new Joystick(RobotMap.Drive.OPERATOR_PORT), ControllerMap.LOGITECH);
		
		autonChoice = createChoices("Auton Choices", AutonChooser.AUTON_CHOICES);
		
		isInit = false;
	}
	
	public static OI getInstance() {
		if(instance == null)
			instance = new OI();
		
		return instance;
	}

    public void init() {
    	
    	if(isInit)
    		return;

    	initDriver();
    	initOperator();
    	initSD();
    	
    	isInit = true;
    }
    
    public void initDriver() {

        Button toggleDriveForward = driver.createButton(toggleForward);
        toggleDriveForward.whenPressed(new ToggleDriveMode(true, false));
        
        Button toggleHalfSpeed = driver.createButton(toggleHalf);
        toggleHalfSpeed.whenPressed(new ToggleDriveMode(false, true));
        
//        Button toggleLEDS = driverGamepad.createButton(toggleLEDs);
//        toggleLEDS.whenPressed(new ToggleLEDMode());

        Button setLiftUp = driver.createButton(liftUp);
        setLiftUp.whenPressed(new SetLift(LiftSubsystem.UP));
        setLiftUp.whenReleased(new SetLift(LiftSubsystem.UP, 0));
        
        Button setLiftDown = driver.createButton(liftDown);
        setLiftDown.whenPressed(new SetLift(LiftSubsystem.DOWN));
        setLiftDown.whenReleased(new SetLift(LiftSubsystem.DOWN, 0));

        Button setLiftIn = driver.createButton(liftIn);
        setLiftIn.whenPressed(new SetLift(LiftSubsystem.IN));
        
        Button setLiftOut = driver.createButton(liftOut);
        setLiftOut.whenPressed(new SetLift(LiftSubsystem.OUT));
        
//		Button nudgeLift = driver.createDPadButton(liftNudgeDown);
//		nudgeLift.whenPressed(new NudgeLiftDown());
//		nudgeLift.whenPressed(new Debug("Lift"));
        
		Button test = driver.createDPadButton(ControllerMap.UP);
		test.whenPressed(new Debug("Up"));
    }
    
    public void initOperator() {
    	
    	Button toggleDriveForward = operator.createButton(opToggleForward);
        toggleDriveForward.whenPressed(new ToggleDriveMode(true, false));
        
        Button pushHalfSpeed = operator.createButton(operatorPushHalf);
        pushHalfSpeed.whenPressed(new OperatorHalfDrive(true));
        pushHalfSpeed.whenReleased(new OperatorHalfDrive(false));

        Button setLiftUp = operator.createButton(opLiftUp);
        setLiftUp.whenPressed(new SetLift(LiftSubsystem.UP));
        setLiftUp.whenReleased(new SetLift(LiftSubsystem.UP, 0));
        
        Button setLiftDown = operator.createButton(opLiftDown);
        setLiftDown.whenPressed(new SetLift(LiftSubsystem.DOWN));
        setLiftDown.whenReleased(new SetLift(LiftSubsystem.DOWN, 0));

        Button setLiftIn = operator.createButton(opLiftIn);
        setLiftIn.whenPressed(new SetLift(LiftSubsystem.IN));
        
        Button setLiftOut = operator.createButton(opLiftOut);
        setLiftOut.whenPressed(new SetLift(LiftSubsystem.OUT));
        
		Button test = operator.createDPadButton(ControllerMap.UP);
		test.whenPressed(new Debug("Up"));
    }
    
    public void initSD() {
    	// TODO: Put SmartDashboard Values;
    }
    
    public int getAutonChoice() {
        return ((Integer) this.autonChoice.getSelected()).intValue();
    }
   
    private SendableChooser createChoices(String label, String [] choices) {
        SendableChooser send = new SendableChooser();
	       
	       if(choices.length > 0) {
	           send.addDefault(choices[0], new Integer(0));
	           
	           for(int i = 1; i < choices.length; i++) {
	               send.addObject(choices[i], new Integer(i));
	           }
	           
	           SmartDashboard.putData(label, send);
	       }
	       
	       return send;
	    }
    
    public static double getDriverRightXAxis() {
    	return checkDeadZone(driver.getRightStickX());
    }
    
    public static double getDriverRightYAxis() {
    	return checkDeadZone(driver.getRightStickY());
    }
    
    public static double getDriverleftXAxis() {
    	return checkDeadZone(driver.getLeftStickX());
    }
    public static double getDriverLeftYAxis() {
    	return checkDeadZone(driver.getLeftStickY());
    }
    
    public static double getOperatorRightXAxis() {
    	return checkDeadZone(operator.getRightStickX());
    }
    
    public static double getOperatorRightYAxis() {
    	return checkDeadZone(operator.getRightStickY());
    }
    
    public static double getOperatorLeftXAxis() {
    	return checkDeadZone(operator.getLeftStickX());
    }
    
    public static double getOperatorLeftYAxis() {
    	return checkDeadZone(operator.getLeftStickY());
    }
    
    public static double checkDeadZone(double val) {
    	return Math.abs(val) < DEADZONE ? 0 : val;
    }
    
    
    
}

