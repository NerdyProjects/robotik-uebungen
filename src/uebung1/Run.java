package uebung1;
import kinematics.BadPositionException;
import kinematics.Kinematics;


public class Run {

	/**
	 * @param args
	 * @throws BadPositionException 
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws BadPositionException, InterruptedException {
		Searcher s = new Searcher();
		s.search();
	}

}
