package uebung1;

import kinematics.BadPositionException;
import kinematics.Kinematics;
import lejos.nxt.Button;
import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;

public class Searcher {
	
	static final int SCAN_A_FROM = -20;
	static final int SCAN_A_TO = 20;
	static final int SCAN_A_STEP = 5;
	static final int SCAN_B_FROM = Kinematics.MIN_B;
	static final int SCAN_B_TO = Kinematics.MAX_B;
	
	
	LightSensor sensor = new LightSensor(SensorPort.S1);
	Kinematics kinematics = new Kinematics();
	
	int max = 0;
	int aMax = 0;
	int bMax = 0;
	
	int min = 1000;
	int aMin = 0;
	int bMin = 0;
	
	int diff = 0;
	int aDiff = 0;
	int bDiff = 0;
	
	/**
	 * Search Pattern. Not finished!
	 * @throws BadPositionException
	 */
	public void search() throws BadPositionException {
		
		int currValue;
		int currA;
		int currB;
		
		int lastValue;
		
		kinematics.rotateTo(-20,-Kinematics.MAX_B, false);

		currValue = sensor.readValue();
		currA = kinematics.getPositionA();
		currB = kinematics.getPositionB();
		
		boolean moveUp = true;
		for (int i = SCAN_A_FROM; i <= SCAN_A_TO; i += SCAN_A_STEP){
			kinematics.rotateATo(i, false);

			if (moveUp) kinematics.rotateBTo(Kinematics.MAX_B,true);
			else kinematics.rotateBTo(Kinematics.MIN_B,true);
			
			while(Motor.B.isMoving()) {
				lastValue = currValue;
				
				currValue = sensor.readValue();
				currA = kinematics.getPositionA();
				currB = kinematics.getPositionB();
				
				if (currValue > max){
					max = currValue;
					aMax = currA;
					bMax = currB;
				}
				
				if (currValue < min) {
					min = currValue;
					aMin = currA;
					bMin = currB;
				}
				
				if (Math.abs(currValue-lastValue) > diff) {
					diff = Math.abs(currValue-lastValue);
					aDiff = currA;
					bDiff = currB;
				}
			}
			moveUp = !moveUp;
		}
		System.out.println("Maximum: "+max+" Position:("+aMax+","+bMax+")");
		System.out.println("Minimum: "+min+" Position:("+aMin+","+bMin+")");
		System.out.println("Differenz: "+diff+" Position:("+aDiff+","+bDiff+")");
		
		double w1 = Math.toRadians(aMax);
		double w2 = Math.toRadians(bMax);
		int l1 = 165;
		int l2 = 121;
		double[][] matrix = new double[4][4];
		matrix[0][0] =        Math.cos(w1) * Math.cos(w2);
		matrix[0][1] = -      Math.cos(w1) * Math.sin(w2);
		matrix[0][2] = -      Math.sin(w1);
		matrix[0][3] = - l2 * Math.cos(w1) * Math.cos(w2);
		matrix[1][0] =        Math.sin(w1) * Math.cos(w2);
		matrix[1][1] = -      Math.sin(w1) * Math.sin(w2);
		matrix[1][2] =          Math.cos(w1);
		matrix[1][3] = - l2 * Math.sin(w1) * Math.cos(w2);
		matrix[2][0] = -                     Math.sin(w2);
		matrix[2][1] = -                     Math.cos(w2);
		matrix[2][2] =   0;
		matrix[2][3] =   l2 * Math.sin(w2) + l1;
		matrix[3][0] =   0;
		matrix[3][1] =   0;
		matrix[3][2] =   0;
		  matrix[3][3] =   1;

		System.out.println("X-Matrix:"+ matrix[0][3]);
		System.out.println("Y-Matrix:"+ matrix[1][3]);
		System.out.println("Z-Matrix:"+ matrix[2][3]);
		Button.waitForAnyPress();
		System.out.println("R1:"+ Math.toDegrees(w1) + "==" + 
		Math.toDegrees(Math.acos(matrix[1][2])));
		System.out.println("R2:"+ Math.toDegrees(w2) + "==" + 
		Math.toDegrees(Math.asin((matrix[2][3]-l1)/l2)));
		Button.waitForAnyPress();
	}
}
