package oopG22;

public class Test {

	// Berechnen der euklidischen Distanz
	public double distance (Bird a, Bird b) {
		double dist;
		double xdist = Math.pow((a.x - b.x), 2);
		double ydist = Math.pow((a.y - b.y), 2);
		return dist = Math.sqrt(xdist + ydist);
	}
	
	private class Bird {
		double xcoord;
		double ycoord;
		
		private int[] neighbors (Bird b) {
			
		}
	}
	
	public static void main(String[] args) {

	}
}