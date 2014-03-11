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
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc3620.GoldenCode2014.Robot;
/**
 *
 */
public class AutonomousMove extends Command {
    long startTime;
    long elapsedTime;
    public AutonomousMove() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES
        requires(Robot.driveSubsystem);
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES
    }
    // Called just before this Command runs the first time
    protected void initialize() {
        System.out.println("autonomous drive init");
        Robot.pneumaticSubsystem.hoopUp();
        
    }
    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        Robot.driveSubsystem.allForward();
    }
    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        //watch the sensors and return true if in the right place
        
        //2/27/14 9:30PM encoder value was 52.0
        if (Robot.driveSubsystem.getEncoderDistance() >= 58.0) {
            return true;
        } 
        else if(Robot.getAutonomousElapsedTime() >= 6.0) {
            System.out.println("autonomous move timed out");
            return true;
        } 
        else{
            return false;
                
        }
    }
    // Called once after isFinished returns true
    protected void end() {
        System.out.println("autonomous drive end");
        Robot.driveSubsystem.allStop();
    }
    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        end();
    }
}
