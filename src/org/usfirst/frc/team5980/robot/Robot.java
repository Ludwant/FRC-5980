
package org.usfirst.frc.team5980.robot;
//ANOTHER Test Comment

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TalonSRX;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */

	//creating new hardware objects
	TalonSRX leftBackMotor;
	TalonSRX rightBackMotor;
	TalonSRX rightFrontMotor;
	TalonSRX leftFrontMotor;
	TalonSRX ballControlMotor;
	Joystick joystickXbox;
	Encoder leftMotorEncoder;
	Encoder rightMotorEncoder;
	boolean turnNow = false;
	int leftMotorCount = 0;
	int rightMotorCount = 0;

	
	static double forwardSpeed = -0.5;
	static double rotateSpeed = -0.2;
	static double ballControlSpeed = -0.7;

    public void robotInit() {
    	//initializing hardware: Speed Controllers, Joysticks, Encoders
    	leftBackMotor = new TalonSRX(0);
    	leftFrontMotor = new TalonSRX(1);
    	rightBackMotor = new TalonSRX(2);
    	rightFrontMotor = new TalonSRX(3);
    	ballControlMotor = new TalonSRX(4);
    	joystickXbox = new Joystick(0);
    	leftMotorEncoder = new Encoder(0,1, false, Encoder.EncodingType.k4X);
    	rightMotorEncoder = new Encoder(2,3,false, Encoder.EncodingType.k4X);
    	leftBackMotor.setInverted(true);
    	leftFrontMotor.setInverted(true);
    	
    	
    }
    
    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
    	//setting variables to Encoder revolution count
    	leftMotorCount = Math.abs(leftMotorEncoder.get());
    	rightMotorCount = Math.abs(rightMotorEncoder.get());
    	//setting left motors' power based on whether or not it is over Outer Works
    	if (leftMotorCount < 3000 && rightMotorCount < 3000) { //37440 is actual # of counts
    		setDrivePower(forwardSpeed, forwardSpeed);
    	}
    	else {
    		setDrivePower(0,0);
    		turnNow = true;
    	}
    	
    	if(turnNow == true) {
    		if(leftMotorCount < 3500 && rightMotorCount < 3500) {
    			setDrivePower(rotateSpeed, -rotateSpeed);
    		}
    		else {
    			setDrivePower(0,0);
    		}
    	}
    	
    }
    
    final static int FORWARD = 0;
    final static int TURN = 1;
    final static int FINISHED = 2;
    final static int DONE = 3;
    int state = FORWARD;
    double speed = 0.5;
    int forwardDistance = 30000;
    int turnDistance = 1000;
    public void autonomousInit() {
    	rightMotorEncoder.reset();
    }
    public void autonomousPeriodicStateMachine() {
    	switch(state) {
    	case FORWARD:
    		setDrivePower(speed, speed);
    		if (rightMotorEncoder.get() < forwardDistance) {
    			rightMotorEncoder.reset();
    			state = TURN;
    		}
    		break;
    	case TURN:
    		setDrivePower(-speed, speed);
    		if (rightMotorEncoder.get() < turnDistance) state = FINISHED;
    		break;
    	case FINISHED:
    		setDrivePower(0,0);
    		state = DONE;
    		break;
    	default:
    	}
    }
    public void setDrivePower(double left, double right) {
    	leftBackMotor.set(left);
    	rightBackMotor.set(right);
    	leftFrontMotor.set(left);
    	rightFrontMotor.set(right);
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
    	//setting motors' power based upon joystick values
        
    	leftBackMotor.set(joystickXbox.getRawAxis(1));
    	leftFrontMotor.set(joystickXbox.getRawAxis(1));
        rightBackMotor.set(joystickXbox.getRawAxis(5));
        rightFrontMotor.set(joystickXbox.getRawAxis(5));
        if(joystickXbox.getRawAxis(2) > 0.2) {
        	ballControlMotor.set(ballControlSpeed);
        }
        else if(joystickXbox.getRawAxis(2) < -0.2) {
        	ballControlMotor.set(-ballControlSpeed);
        }
       
        
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    
    }
    
    
  
    
}
