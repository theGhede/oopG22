package oopG22;

public class Test {

	
	// Vögel bestehen aus Koordinaten & einer Nachbarschaft
	private class Bird {

		double xcoord;
		double ycoord;
<<<<<<< HEAD
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

=======
		Bird[] neighbors;
		int index;

		// Aufbai der Nachbarschaft eines Vogels mittels "Sonar" bis (5-20) Nachbarn gefunden wurden
		private Bird[] neighborhood(Bird b, int radius) {

			int j = 0;
			int amount = (int) (5 + (15 * Math.random()));
			Bird neighbors[] = new Bird[amount-1];
			for (int i = 0; i < flock.length; i++) {
				if (i != b.index && j < amount) {
					if (distance(b, flock[i]) <= radius) {
						neighbors[j] = flock[i];
						j++;
>>>>>>> 8924d79fcf8a0aad48bf64dadedadae5345ae8ba
					}
				}
			}
			if (j < amount) neighborhood(b, radius + 1);

			return neighbors;
		}

		void moveUp(double k) {
			double move = this.ycoord + k;
			this.ycoord = move;
		}

		void moveDown(double k) {
			double move = this.ycoord - k;
			this.ycoord = move;
		}

		void moveLeft(double k) {
			double move = this.xcoord - k;
			this.xcoord = move;
		}

		void moveRight(double k) {
			double move = this.xcoord + k;
			this.xcoord = move;
		}

		// Nachbarschaftsbewegung, moveUp = y1, moveDown = y2, moveRight = x1, moveLeft = x2
		//Im Flug können sich die Vögeln stärker annähern als die mindestens Distanz

		void moveBird (Bird b, double x1,double x2, double y1, double y2){

			if (!moved[b.index]){
				moveUp(y1);
				moveDown(y2);
				moveLeft(x2);
				moveRight(x1);
			}
			for (int i = 0; i < b.neighbors.length ; i++) {
				moveBird(b.neighbors[i],x1,x2,y1,y2);
			}

		}
	}

	// Der Schwarm wird von einem Array von Vögeln repräsentiert
	// Die Anzahl der Vögel ist willkürlich vorbestimmt
	public Bird[] flock = new Bird[199];
	public boolean[] moved = new boolean[199];

	// Mindestabstand - willkürlich definiert
	public double minDistance = 4;
	
	public void testDistance () {
		double[] distances = new double[flock.length];
		for(int i = 0; i < flock.length; i++) {
			for(int j = 0; j < flock.length; j++) {
				distances[i] = distance(flock[i], flock[j]);
				if(distances[i] < minDistance) {
					double helpDistance = minDistance - distances[i];
					double yDistance = flock[i].xcoord - flock[j].xcoord;
					double xDistance = flock[i].ycoord - flock[j].ycoord;

					if(flock[j].ycoord < flock[i].ycoord && flock[j].xcoord < flock[i].xcoord){
						double angle = Math.atan(xDistance/yDistance);
						double Xmove = helpDistance * Math.sin(angle);
						double Ymove = Xmove/Math.tan(angle);

						flock[j].moveUp((yMove));
						flock[j].moveRight((xMove));

					}
					else if(flock[j].ycoord > flock[i].ycoord && flock[j].xcoord > flock[i].xcoord){
						double angle = Math.atan(yDistance/xDistance);
						double Xmove = helpDistance * Math.sin(angle);
						double Ymove = Xmove/Math.tan(angle);

						flock[j].moveUp((yMove));
						flock[j].moveRight((xMove));
					}
					else if(flock[j].ycoord < flock[i].ycoord && flock[j].xcoord > flock[i].xcoord) {
						double angle = Math.atan(xDistance/yDistance);
						double Xmove = helpDistance * Math.sin(angle);
						double Ymove = Xmove/Math.tan(angle);

						flock[j].moveUp((yMove));
						flock[j].moveRight((xMove));

					}
					else if(flock[j].ycoord > flock[i].ycoord && flock[j].xcoord < flock[i].xcoord) {
						double angle = Math.atan(yDistance/xDistance);
						double Xmove = helpDistance * Math.sin(angle);
						double Ymove = Xmove/Math.tan(angle);

						flock[j].moveUp((yMove));
						flock[j].moveRight((xMove));

					}
					else if(flock[j].ycoord == flock[i].ycoord) {
						
					}
					else if(flock[j].xcoord == flock[i].xcoord) {

					}

				}
			}
		}
	}

	
	
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

