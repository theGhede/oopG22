package oopG22;

public class Test {
	
	// Vogel - besteht aus Koordinaten und eine Nachbarschaft, errechnet aus den (5-20) nähesten anderen Vögeln
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
				double[] distances = new double[199];
				for (int j = 0; j < 200; j++) {
					if (b.xcoord == flock[j].xcoord && b.ycoord == flock[j].ycoord) {
						distances[j] = distance(b, flock[j]);
					}
				}
			}
			
			return neighbors;
		}
	}
	
	// Der Schwarm wird von einem Array von Vögeln repräsentiert
	// Die Anzahl der Vögel ist willkürlich vorbestimmt
	public Bird[] flock = new Bird[199];
	
	// Berechnen der euklidischen Distanz
	public double distance (Bird a, Bird b) {
		double dist;
		double xdist = Math.pow((a.xcoords - b.xcoords), 2);
		double ydist = Math.pow((a.ycoords - b.ycoords), 2);
		return dist = Math.sqrt(xdist + ydist);
	}
	
	
	public static void main(String[] args) {
		
		int amount = (int) (5 + (Math.random() * 15));
		System.out.println("" + amount);
	}
}