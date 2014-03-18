// RobotBuilder Version: 1.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.
package org.usfirst.frc3620.GoldenCode2014.commands;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc3620.GoldenCode2014.Robot;
/**
 *
 */
public class  AutonomousVisionTestCommand extends Command {
    public AutonomousVisionTestCommand() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
	
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES
    }
    long t0;
    
    // Called just before this Command runs the first time
    protected void initialize() {
        t0 = System.currentTimeMillis();
        Robot.visionSubsystem.processOne(null);
    }
    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }
    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        boolean rv = false;
        long t1 = System.currentTimeMillis();
        if (t1 - t0 > 5000) {
            System.out.println ("timed out");
            rv = true;
        } else {
            rv = Robot.visionSubsystem.isGoalIsHot();
            if (rv) System.out.println("goal was hot!");
        }
        return rv;
    }
    // Called once after isFinished returns true
    protected void end() {
    }
    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        end();
    }
}
