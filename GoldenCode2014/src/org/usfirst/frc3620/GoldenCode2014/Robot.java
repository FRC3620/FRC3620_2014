// RobotBuilder Version: 1.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.
package org.usfirst.frc3620.GoldenCode2014;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import org.usfirst.frc3620.GoldenCode2014.commands.*;
import org.usfirst.frc3620.GoldenCode2014.subsystems.*;
/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
    Command autonomousCommand;
    static long startAutonomousTime;
    static RobotMode currentRobotMode;
    static NetworkTable telemetryTable, visionTable;
    public static DriverStation driverStation;
    public static OI oi;
    public static Preferences preferences;
    
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    public static PneumaticSubsystem pneumaticSubsystem;
    public static DriveSubsystem driveSubsystem;
    public static SensorSubsystem sensorSubsystem;
    public static CatapultSubsystem catapultSubsystem;
    public static IntakeSubsystem intakeSubsystem;
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
        RobotMap.init();
        
        // make sure we have tables for the commands to use
        telemetryTable = NetworkTable.getTable("telemetry");
        visionTable = NetworkTable.getTable("vision"); 
        
        preferences = Preferences.getInstance();
        System.out.println ("running on cRIO \"" + preferences.getString("who", "**unknown**") + "\"");
        
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
        pneumaticSubsystem = new PneumaticSubsystem();
        driveSubsystem = new DriveSubsystem();
        sensorSubsystem = new SensorSubsystem();
        catapultSubsystem = new CatapultSubsystem();
        intakeSubsystem = new IntakeSubsystem();
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
        // This MUST be here. If the OI creates Commands (which it very likely
        // will), constructing it during the construction of CommandBase (from
        // which commands extend), subsystems are not guaranteed to be
        // yet. Thus, their requires() statements may grab null pointers. Bad
        // news. Don't move it.
        oi = new OI();
        
        // instantiate the command used for the autonomous period
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=AUTONOMOUS
        autonomousCommand = new AutonomousCommandGroup();
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=AUTONOMOUS
        
        driverStation = DriverStation.getInstance();
        currentRobotMode = RobotMode.DISABLED;
        broadcastRobotInitToSubsystems();
        Robot.sensorSubsystem.sendToArduino(0);
       
    }
    public void autonomousInit() {
        startAutonomousTime = System.currentTimeMillis();
        currentRobotMode = RobotMode.AUTONOMOUS;
        broadcastModeChangeToSubsystems();
        // schedule the autonomous command (example)
        if (autonomousCommand != null) {
            autonomousCommand.start();
        }
    }
    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
        broadcastPeriodicToSubsystems();
        Robot.sensorSubsystem.sendToArduino(5);
        
    }
    public void teleopInit() {
        // This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to 
        // continue until interrupted by another command, remove
        // this line or comment it out.
        if (autonomousCommand != null) {
            autonomousCommand.cancel();
        }
        
        currentRobotMode = RobotMode.TELEOP;
        broadcastModeChangeToSubsystems();
        /* Mike: we moved this to the modeChanged method in the DriveSubSystem :) */
        /* driveSubsystem.setReverseMode(false); */
    }
    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
        broadcastPeriodicToSubsystems();
        
    }
    /**
     * This function gets call once at the start of test.
     */
    public void testInit() {
        currentRobotMode = RobotMode.TEST;
        broadcastModeChangeToSubsystems();
    }
    /**
     * This function called periodically during test mode
     */
    public void testPeriodic() {
        LiveWindow.run();
        broadcastPeriodicToSubsystems();
    }
    public void disabledInit() {
        currentRobotMode = RobotMode.DISABLED;
        broadcastModeChangeToSubsystems();
        
    }
    public void disabledPeriodic() {
        broadcastPeriodicToSubsystems();
       
    }
    public static NetworkTable getVisionTable() {
        return visionTable;
    }
    public static NetworkTable getTelemetryTable() {
        return telemetryTable;
    }
    /**
     * let any interested subsystems inititalize.
     */
    void broadcastRobotInitToSubsystems() {
        catapultSubsystem.init();
        driveSubsystem.init();
        intakeSubsystem.init();
        pneumaticSubsystem.init();
        sensorSubsystem.init();
    }
    /**
     * let any interested subsystems know that we changed modes.
     */
    void broadcastModeChangeToSubsystems() {
        getTelemetryTable().putString("mode", currentRobotMode.toString());
        
        /* add calls to interested subsystems here */
        catapultSubsystem.modeChanged();
        driveSubsystem.modeChanged();
        intakeSubsystem.modeChanged();
        pneumaticSubsystem.modeChanged();
        sensorSubsystem.modeChanged();
    }
    /**
     * let any interested subsystems do periodic processing.
     */
    void broadcastPeriodicToSubsystems() {
        getTelemetryTable().putNumber("time.autonomous.elapsed.s", getAutonomousElapsedTime());
        getTelemetryTable().putNumber("voltage", driverStation.getBatteryVoltage());
        getTelemetryTable().putString("alliance", driverStation.getAlliance().name);
        getTelemetryTable().putNumber("time.system.ms", System.currentTimeMillis());
        getTelemetryTable().putNumber("time.match.s", driverStation.getMatchTime());
        getTelemetryTable().putNumber("js1.buttons", driverStation.getStickButtons(1));
        getTelemetryTable().putNumber("js2.buttons", driverStation.getStickButtons(2));
        
        /* add calls to subsystems here */
        catapultSubsystem.periodic();
        driveSubsystem.periodic();
        intakeSubsystem.periodic();
        pneumaticSubsystem.periodic();
        sensorSubsystem.periodic();
    }
    public static double getAutonomousElapsedTime() {
        return (System.currentTimeMillis() - startAutonomousTime) / 1000.0;
    }
    public static RobotMode getCurrentRobotMode() {
        return (currentRobotMode);
    }
}
