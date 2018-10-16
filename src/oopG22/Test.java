package oopG22;

public class Test {

	// Berechnen der euklidischen Distanz
	public double distance (Bird a, Bird b) {
		double dist;
		double xdist = Math.pow((a.x - b.x), 2);
		double ydist = Math.pow((a.y - b.y), 2);
		return dist = Math.sqrt(xdist + ydist);
	}
	
	// 
	private class Bird {
		double xcoord;
		double ycoord;
		int[] neighbors;
		
		private int[] neighborhood (Bird b) {
			int[] neighbors = new int[19];
			for (int i = 0; i < 20; i++) {
				neighbors[i] = -1;
			}
			int amount = (int) (5 + (Math.random() * 15));
			
			for (int i = 0; i < amount; i++) {
				
			}
			
			return neighbors;
		}
	}
	
	
	
	public static void main(String[] args) {
		
		// Der Schwarm wird von einem Array von Vögeln repräsentiert
		// Die Anzahl der Vögel ist willkürlich vorbestimmt
		Bird[] flock = new Bird[200];
		int amount = (int) (5 + (Math.random() * 15));
		System.out.println("" + amount);
	}
}