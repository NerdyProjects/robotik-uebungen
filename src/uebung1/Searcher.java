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
	
	/**
	 * Search Pattern. Not finished!
	 * @throws BadPositionException
	 */
	public void search() throws BadPositionException {
		
		int max = 0;
		int aMax = 0;
		int bMax = 0;
		
		int min = 1000;
		int aMin = 0;
		int bMin = 0;
		
		int diff = 0;
		int aDiff = 0;
		int bDiff = 0;
		
		int currValue;
		int currA;
		int currB;
		
		int b4Value;
		int b4A;
		int b4B;
		
		kinematics.rotateTo(-20,-Kinematics.MAX_B, false);

		currValue = sensor.readValue();
		currA = kinematics.getPositionA();
		currB = kinematics.getPositionB();
		
		boolean switcher = true;
		for (int i = SCAN_A_FROM; i <= SCAN_A_TO; i += SCAN_A_STEP){
			kinematics.rotateATo(i, false);

			if (switcher) kinematics.rotateBTo(Kinematics.MAX_B,true);
			else kinematics.rotateBTo(Kinematics.MIN_B,true);
			
			while(Motor.B.isMoving()) {
				b4Value = currValue;
				b4A = currA;
				b4B = currB;
				
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
				
				if (Math.abs(currValue-b4Value) > diff) {
					diff = Math.abs(currValue-b4Value);
					aDiff = currA;
					bDiff = currB;
				}
			}
			switcher = !switcher;
		}
		System.out.println("Maximum: "+max+" Position:("+aMax+","+bMax+")");
		System.out.println("Minimum: "+min+" Position:("+aMin+","+bMin+")");
		System.out.println("Differenz: "+diff+" Position:("+aDiff+","+bDiff+")");
		kinematics.rotateTo(aMin, bMin, false);
		Button.waitForAnyPress();
		kinematics.rotateTo(aMax, bMax, false);
		Button.waitForAnyPress();
		kinematics.rotateTo(aDiff, bDiff, false);
		Button.waitForAnyPress();
		kinematics.reset();
	}
}
