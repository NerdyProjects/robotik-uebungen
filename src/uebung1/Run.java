package uebung1;
import kinematics.BadPositionException;
import kinematics.Kinematics;
import lejos.nxt.Button;


public class Run {

	/**
	 * @param args
	 * @throws BadPositionException 
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws BadPositionException, InterruptedException {
		Searcher s = new Searcher();
		s.search();
		
		Kinematics kinematics = new Kinematics();
		kinematics.rotateTo(s.aMin, s.bMin, false);
		Button.waitForAnyPress();
		kinematics.rotateTo(s.aMax, s.bMax, false);
		Button.waitForAnyPress();
		kinematics.rotateTo(s.aDiff, s.bDiff, false);
		Button.waitForAnyPress();
		kinematics.reset();
	}

}
