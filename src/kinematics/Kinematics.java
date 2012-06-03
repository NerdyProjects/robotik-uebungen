package kinematics;

import lejos.nxt.Motor;

public class Kinematics {
	private static int translationA = 7;
	private static int translationB = 1;
	private static int defaultSpeed = 40;
	public static int	maxA = 45;
	public static int	maxB = 30;
	
	
	/**
	 * Constructor
	 */
	public Kinematics() {
		Motor.A.setSpeed(defaultSpeed*translationA);
		Motor.B.setSpeed(defaultSpeed*translationB);
		reset();
	}
	
	
	/**
	 * Resets the Position back to 0/0
	 */
	public void reset() {
		Motor.A.rotateTo(0);
		Motor.B.rotateTo(0);
		Motor.A.resetTachoCount();
		Motor.B.resetTachoCount();
	}
	
	/**
	 * Rotates both Motor to given degrees
	 * @param a	Position for motor a
	 * @param b Position for motor b
	 * @param immediateReturn
	 * @throws BadPositionException 
	 */
	public void rotateTo(int a, int b, boolean immediateReturn) throws BadPositionException {
		rotateATo(a, immediateReturn);
		rotateBTo(b, immediateReturn);
	}
	
	/**
	 * @param position
	 * @param immediateReturn
	 * @throws BadPositionException 
	 */
	public void rotateATo(int position, boolean immediateReturn) throws BadPositionException {
		if (position > maxA || position < maxA*-1) throw new BadPositionException();
		Motor.A.rotateTo(position*translationA, immediateReturn);
	}
	
	/**
	 * @param position
	 * @param immediateReturn
	 * @throws BadPositionException 
	 */
	public void rotateBTo(int position,  boolean immediateReturn) throws BadPositionException {
		if (position > maxB || position < maxB*-1) throw new BadPositionException();
		Motor.B.rotateTo(position*translationB, immediateReturn);
	}
	
	/**
	 * Returns the Position of Engine A.
	 * @return Position (Degree) of Engine A.
	 */
	public int getPositionA() {
		return Motor.A.getPosition()/translationA;
	}
	
	/**
	 * Returns the Position of Engine B.
	 * @return Position (Degree) of Engine B.
	 */
	public int getPositionB() {
		return Motor.B.getPosition()/translationB;
	}
}
