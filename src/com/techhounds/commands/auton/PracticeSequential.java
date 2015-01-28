/*
 * Ayon Mitra, Matt Simons, Clayton Detke, the interns
*/

package com.techhounds.commands.auton;

import com.techhounds.commands.SetArms;
import com.techhounds.commands.driving.DriveTime;
import com.techhounds.commands.lift.SetLift;
import com.techhounds.subsystems.ArmsSubsystem;
import com.techhounds.subsystems.LiftSubsystem;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import edu.wpi.first.wpilibj.command.WaitForChildren;

public class PracticeSequential extends CommandGroup {
    
    public  PracticeSequential() {
    	
    	addSequential(new SetArms(ArmsSubsystem.STOPPED, ArmsSubsystem.OPEN));
    	addSequential(new SetLift(LiftSubsystem.DOWN));
    	addSequential(new AutonDrive(10));
    	addSequential(new SetArms(ArmsSubsystem.FEED_IN, ArmsSubsystem.CLOSED));
    	addSequential(new WaitCommand(.5));
    	addSequential(new SetArms(ArmsSubsystem.STOPPED , ArmsSubsystem.OPEN));
    	addSequential(new SetLift(LiftSubsystem.UP));
    	addSequential(new WaitCommand(1));
    	addSequential(new AutonDrive(10));

    	
    }
}
