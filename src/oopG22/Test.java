package oopG22;

public class Test {

	// Berechnen der euklidischen Distanz
	public double distance(Bird a, Bird b) {
		double dist;

		double xdist = Math.pow((a.x - b.x), 2);
		double ydist = Math.pow((a.y - b.y), 2);
		return dist = Math.sqrt(xdist + ydist);


	}


	private class Bird {

		double x;
		double y;
		int index;

		private Bird[] neighbors(Bird b, Bird flock[], int radius) {

			Bird neighbors[] = new Bird[200];
			for (int i = 0, j = 0; i < flock.length - 1; i++) {
				if (i == b.index) break;

				if (distance(b, flock[i]) <= radius) {
					neighbors[j] = flock[i];
					j++;
				}
				if (j < 5) neighbors(b, flock, radius + 1);
			}


			return neighbors;
		}
	}
	
	// Der Schwarm wird von einem Array von Vögeln repräsentiert
	// Die Anzahl der Vögel ist willkürlich vorbestimmt
	public Bird[] flock = new Bird[199];
	
	// Mindestabstand - willkürlich definiert
	public double minDistance = 4;
	
	public void testDistance () {
		double[] distances = new double[flock.length];
		for(int i = 0; i < flock.length; i++) {
			for(int j = 0; j < flock.length; j++) {
			distances[i] = distance(flock[i], flock[j]);
			}
		}
	}
	
	// Nachbarschaftsbewegung
	
	
	// Berechnen der euklidischen Distanz
	public double distance (Bird a, Bird b) {
		double dist;
		double xdist = Math.pow((a.xcoord - b.xcoord), 2);
		double ydist = Math.pow((a.ycoord - b.ycoord), 2);
		return dist = Math.sqrt(xdist + ydist);
	}
	
	
	
	public static void main(String[] args) {

		Bird[] flock = new Bird[200];

		int amount = (int) (5 + (Math.random() * 15));

		System.out.println("" + amount);

	}
}

