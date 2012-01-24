/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.templates;


import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Joystick;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
/**
 * 
 * @author Alex
 */
public class RobotTemplate extends IterativeRobot {
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
     private RobotDrive robotdt;
    Gyro gyro1 = new Gyro(8); //horizontal
    //Gyro gyro2; //vertical
    double Kp=0.03;
    //private Jaguar rmotor; //balancing motors
    //private Jaguar lmotor;
    Joystick joystick1 = new Joystick(1);
    public void robotInit() {
        GyroInit();
        gyro1.reset();
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        int counter = 0;
        double gyroangle = 0.0;
        if(joystick1.getRawButton(1)&&Math.abs(
            joystick1.getY())>0.25){
            if(counter==0){
                gyroangle = DegreeAngle(gyro1.getAngle());
                counter ++;
            }
            GyroAutoStraight(gyroangle,joystick1.getY());
        }
        else{
            counter = 0;
        }
        if(joystick1.getRawButton(3)){
            TurnToAngle(0);
        }
        if(joystick1.getRawButton(2)){
            TurnToAngle(180);
        }
        if(joystick1.getRawButton(4)){
            TurnToAngle(-90);
        }
        if(joystick1.getRawButton(5)){
            TurnToAngle(90);
        }
    }
    
   
    double DegreeAngle(double angle){
        double retangle = angle%360 + (angle>180 ? -360:0);
        return retangle;            
    }
    public void GyroInit(){
        getWatchdog().setExpiration(0.1);
    }
    
    protected void GyroAutoScore(){
        gyro1.reset();
        while(isAutonomous()){
            getWatchdog().feed();
            double angle=DegreeAngle(gyro1.getAngle());
            robotdt.drive(0.0,-angle*Kp);
            Timer.delay(0.005);
        }
        robotdt.drive(0.0,0.0);
    }
    /*protected void GyroAutoBalance(){
       double angle=DegreeAngle(gyro2.getAngle());
       //rmotor.set(angle*Kp);
       //lmotor.set(angle*Kp);
       robotdt.drive(-0.1*angle, 0.0);
    }*/
    protected void GyroAutoStraight(double target, double speed){
        double angle=DegreeAngle(gyro1.getAngle());
        //(angle>0 ? rmotor:lmotor).set(Math.abs(angle));
        robotdt.drive(speed,-(target-angle)*Kp);
    }
    protected void TurnToAngle(double newangle){
        double curangle = newangle-DegreeAngle(gyro1.getAngle());
        robotdt.drive(0.0,-curangle);
    }
    
 
   
    
}
