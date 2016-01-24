
package org.usfirst.frc.team5980.robot;



import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TalonSRX;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
//test comment for GitHub - Anton L
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
	//test comment for master 
	//creating new hardware objects
	TalonSRX leftMotor;
	TalonSRX rightMotor;
	Joystick joystickXbox;
	Encoder leftMotorEncoder;
	Encoder rightMotorEncoder;
	int leftMotorCount = 0;
	int rightMotorCount = 0;

	
	static double forward = 0.5;

    public void robotInit() {
    	//initializing hardware: Speed Controllers, Joysticks, Encoders
    	leftMotor = new TalonSRX(0);
    	
    	rightMotor = new TalonSRX(2);
  
    	joystickXbox = new Joystick(0);
    	leftMotorEncoder = new Encoder(0,1, false, Encoder.EncodingType.k4X);
    	rightMotorEncoder = new Encoder(2,3,false, Encoder.EncodingType.k4X);
    	
    	
    }
    
    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
    	//setting variables to Encoder revolution count
    	leftMotorCount = Math.abs(leftMotorEncoder.get());
    	rightMotorCount = Math.abs(rightMotorEncoder.get());
    	
    	
    	
    	SmartDashboard.putNumber("Left Encoder: ", leftMotorCount);
    	SmartDashboard.putNumber("Right Encoder: ", rightMotorCount);
    	//setting left motors' power based on whether or not it is over Outer Works
    	if (leftMotorCount < 3000) { //37440 is correct count number
    		leftMotor.set(-forward);
    	}
    	else {
    		leftMotor.set(0);
    		
    	}
    	//setting right motors' power based on whether or not it is over Outer Works
    	if (rightMotorCount < 3000) {
    		rightMotor.set(forward);
    	}
    	else {
    		rightMotor.set(0);
    		
    	}
    	
    	
    	
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
    	//setting motors' power based upon joystick values
        leftMotor.set(-joystickXbox.getRawAxis(1));
        rightMotor.set(joystickXbox.getRawAxis(5));
       
       
        
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    
    }
    
  
    
}
