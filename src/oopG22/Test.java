package oopG22;

public class Test {

	private class Bird {

		double xcoord;
		double ycoord;
<<<<<<< HEAD
=======
		int[] neighbors;
>>>>>>> 3cd3f6eaaad5c0a59a9098c208ab10223b16c106
		int index;

		private Bird[] neighborhood(Bird b, int radius) {

			Bird neighbors[] = new Bird[flock.length];
			int j = 0;
			for (int i = 0; i < flock.length; i++) {
				if (i == b.index) break;

				if (distance(b, flock[i]) <= radius) {
					neighbors[j] = flock[i];
					j++;
				}
			}
			if (j < 5) neighbors(b, radius + 1);

			return neighbors;
		}
		
		void moveUp(int k) {
			double move = this.ycoord + k;
			this.ycoord = move;
		}
		
		void moveDown(int k) {
			double move = this.ycoord - k;
			this.ycoord = move;
		}

		void moveLeft(int k) {
			double move = this.xcoord - k;
			this.xcoord = move;
		}

		void moveRight(int k) {
			double move = this.xcoord + k;
			this.xcoord = move;
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

