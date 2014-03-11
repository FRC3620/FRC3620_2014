// RobotBuilder Version: 1.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.
package org.usfirst.frc3620.GoldenCode2014.subsystems;
import org.usfirst.frc3620.GoldenCode2014.RobotMap;
import org.usfirst.frc3620.GoldenCode2014.commands.*;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import java.util.TimerTask;
import org.usfirst.frc3620.GoldenCode2014.CatapultState;
import org.usfirst.frc3620.GoldenCode2014.Robot;
import org.usfirst.frc3620.GoldenCode2014.RobotMode;
/**
 *
 */
public class CatapultSubsystem extends Subsystem {
    boolean isCocked = false;
    CatapultState catpultState = CatapultState.COCKED;
    NetworkTable telemetryTable = Robot.getTelemetryTable();
    boolean DEBUG = false;
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    SpeedController chooChoomotor = RobotMap.catapultSubsystemChooChoomotor;
    AnalogChannel chooChooEncoder = RobotMap.catapultSubsystemChooChooEncoder;
    DigitalInput chooChooLimitSwitch = RobotMap.catapultSubsystemChooChooLimitSwitch;
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    public void initDefaultCommand() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    public void turnMotorHalfSpeed() {
        chooChoomotor.set(-0.75);
    }
    public void turnMotor() {
        chooChoomotor.set(-1.0);
    }
    public void motorOff() {
        chooChoomotor.set(0);
    }
    public boolean inPosition() {
        long now = System.currentTimeMillis();
        double encoderValue = chooChooEncoder.getAverageVoltage();
        /*
         averageEncoderVoltageFDR.add(now, encoderValue);
         encoderVoltageFDR.add(now, chooChooEncoder.getVoltage());
         */
        boolean chooChooSwitch = chooChooLimitSwitch.get();
        boolean rv = false;
        // if (((0.7 < encoderValue) && (encoderValue < 1.153)) || (chooChooSwitch == true)) { //Checks if the motor is in the
        if (chooChooSwitch) {
            rv = true;                                        //cocked position
        } else {
            rv = false;
        }
        SmartDashboard.putBoolean("catapult.inPosition", rv);
        return rv;
    }
    //NetworkTable visionTable;
    public boolean visionSeesHotGoal() {
      
        // rest of code
        boolean rv;
        rv = GoalWatcher.visionSeesHotGoal(Robot.getVisionTable());
        if(!rv){
        // this is just for debugging, look for a signal from the dashboard to 
        // fire if the vision code doesn't see anything yet
            rv = SmartDashboard.getBoolean("okToFire", false);
        }
        
        if(rv){
            System.out.println("sees hot goal");
        Robot.sensorSubsystem.sendToArduino(SensorSubsystem.PURPLE);
        }
        else{System.out.println("does not see hot goal");}
        SmartDashboard.putBoolean("catapult.seeHotGoal", rv);
        return rv;
    }
    public boolean isCocked() {
        return isCocked;
    }
    public void setIsCocked(boolean newIsCocked) {
        isCocked = newIsCocked;
    }
    public CatapultState getCatapultState() {
        return catpultState;
    }
    public void setCatapultState(CatapultState newState) {
        catpultState = newState;
        SmartDashboard.putString("catapult.state", catpultState.label);
    }
    /**
     * add any needed code to run when robot powers up.
     */
    public void init() {
        SmartDashboard.putBoolean("okToFire", false);
        setIsCocked(true);
        setCatapultState(CatapultState.COCKED);
        java.util.Timer timer = new java.util.Timer();
        timer.schedule(new CatapultTask(), 0, 5);
    }
    /**
     * add any needed code to run if the mode changes.
     */
    public void modeChanged() {
        // 
    }
    /**
     * add any needed code to run everytime periodic is called.
     */
    public void periodic() {
        SmartDashboard.putBoolean("choochoo.switch", chooChooLimitSwitch.get());
        telemetryTable.putNumber("choochoo.power", chooChoomotor.get());
    }
    class CatapultTask extends TimerTask {
        long t0 = 0;
        long shootingT0 = 0;
        public void run() {
            SmartDashboard.putNumber("shooter.loop.lastTime", System.currentTimeMillis());
            if (catpultState == CatapultState.COCKED) {
                /*
                 * we are in the cocked state. We will stay in this state until
                 * someone else (a Command in Teleop or Autonomous) calls
                 * setCatapultState(SHOOTING_DELAY) or
                 * setCatapultState(SHOOTING).
                 */
                motorOff();
            } else if (catpultState == CatapultState.SHOOTING_DELAY) {
                if (Robot.pneumaticSubsystem.getHoopDown() == true) {
                    setCatapultState(CatapultState.SHOOTING);
                } else {
                    Robot.pneumaticSubsystem.hoopDown();
                }
            } else if (catpultState == CatapultState.SHOOTING) {
                /*
                 * we are shooting, we stay in this state until the cam comes
                 * up (inPosition() goes false).
                 * 
                 * when in this state, we run the motor at full power.
                 * 
                 * when the cam comes up, we kill the motor, then advance to 
                 * CatapultState.SHOT.
                 */
                if (inPosition()) {
                    // this is probably unnecessary; we should have pushed the
                    // hoop down in the precious state, or at the beginning
                    // of Autonomous.
                    Robot.pneumaticSubsystem.hoopDown();
                    
                    turnMotor();
                } else {
                    if(Robot.getCurrentRobotMode() == RobotMode.AUTONOMOUS){
                    }
                    else{
                    Robot.pneumaticSubsystem.hoopUp();
                    }
                    motorOff();
                    setCatapultState(CatapultState.SHOT);
                }
            } else if (catpultState == CatapultState.SHOT) {
                /*
                 * we just shot. We will stay in this state until
                 * someone else (a Command in Teleop or Autonomous) calls
                 * setCatapultState(CLOCKING_CAM).
                 */
                motorOff();
            } else if (catpultState == CatapultState.COCKING_CAM) {
                /*
                 * we are cocking and waiting for the camswitch to go
                 * down (inPosition() == true).
                 * 
                 * We run the motor at full power in this state.
                 * 
                 * When the camswitch goes down, then we change the motor
                 * to half-power, record the current time, and advance to 
                 * state COCKING_TIME.
                 */
                if (!inPosition()) {
                    turnMotor();
                } else {
                    turnMotorHalfSpeed();
                    t0 = System.currentTimeMillis();
                    setCatapultState(CatapultState.COCKING_TIMER);
                    telemetryTable.putNumber("catapult.cam.time", t0);
                }
            } else if (catpultState == CatapultState.COCKING_TIMER) {
                /*
                 * we are cocking, the camswitch has go down, and we are
                 * waiting for a timer to expire.
                 * 
                 * We run the motor at partial power in this state.
                 * 
                 * When the timer expires, we kill the motor, and advance to 
                 * state COCKED.
                 */
                long elapsedTime = (System.currentTimeMillis() - t0);
                double slider = DriverStation.getInstance().getAnalogIn(1);
                double delay = 100 + (34 * slider);
                telemetryTable.putNumber("catapult.delay", delay);
                if (DEBUG) {
                    System.out.println("elsped: " + elapsedTime + ", delay = " + delay);
                }
                if (elapsedTime <= delay) {
                    turnMotorHalfSpeed();
                } else {
                    motorOff();
                    setCatapultState(CatapultState.COCKED);
                    telemetryTable.putNumber("catapult.motoroff.time", System.currentTimeMillis());
                }
            }
        }
    }
}
