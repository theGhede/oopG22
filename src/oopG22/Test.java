package oopG22;

public class Test {
	
	// Vogel - besteht aus Koordinaten und eine Nachbarschaft, errechnet aus den (5-20) nähesten anderen Vögeln
	private class Bird {
		double xcoord;
		double ycoord;
		int[] neighbors;
		
		private int[] neighborhood (Bird b) {
			int[] neighbors = new int[19];
			for (int i = 0; i < neighbors.length; i++) {
				neighbors[i] = -1;
			}
			
			int amount = (int) (5 + (Math.random() * 15));
			
			for (int i = 0; i < amount; i++) {
				double[] distances = new double[flock.length];
				for (int j = 0; j < flock.length; j++) {
					if (b.xcoord == flock[j].xcoord && b.ycoord == flock[j].ycoord) {
						distances[j] = distance(b, flock[j]);
					}
				}
			}
			
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