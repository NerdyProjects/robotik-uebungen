package uebung1;

import kinematics.BadPositionException;
import kinematics.Kinematics;
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
	}
}
