package oopG22;

public class Swarm {
	// what kind of swarm it is
	String type;
	int swarmsize;
	int minDistance;
	// selects first animal to move (type dependent rules)
	int select;
	Animal[] swarm;

	// Aufbau der Nachbarschaft eines Vogels mittels "Sonar" bis (5-20) Nachbarn
	// gefunden wurden
	public void neighborhood(Animal b, int radius) {
		int j = 0;
		int amount = (int) (5 + (15 * Math.random()));
		Animal[] neighbors = new Animal[amount];
		for (int i = 0; i < this.swarm.length; i++) {
			if (i != b.index && j < amount && b.distance(this.swarm[i]) <= radius) {
				neighbors[j] = this.swarm[i];
				j++;
			}
		}
		if (j < amount) {
			this.neighborhood(b, radius + 1);
		} else {
			b.neighbors = neighbors;
		}
	}

	public void resetMoved() {
		for (int i = 0; i < this.swarm.length; i++) {
			this.swarm[i].moved = false;
		}
	}

	// Make swarm within the center 400x400 of the JFrame; reminder: top-right =
	// (0,0)
	public void makeswarm(String type, int size, int minDistance) {
		this.type = type;
		this.swarmsize = size;
		this.minDistance = minDistance;
		this.swarm = new Animal[size];
		double[] xvalues = new double[this.swarm.length];
		double[] yvalues = new double[this.swarm.length];
		for (int i = 0; i < this.swarm.length; i++) {
			xvalues[i] = (double) Math.round(((Math.random() * 400) + 200) *100) / 100;
			yvalues[i] = (double) Math.round(((Math.random() * 400) + 200) *100) / 100;
		}
		for (int i = 0; i < this.swarm.length; i++) {
			Animal b = new Animal();
			b.xcoord = xvalues[i];
			b.ycoord = yvalues[i];
			b.index = i;
			this.swarm[i] = b;
			b.modifier = 1;
		}
		// Find neighbors for each Animal
		for (int i = 0; i < this.swarm.length; i++) {
			this.neighborhood(this.swarm[i], 1);
		}
		// check and repair minimal distance infringements
		this.establishDistance();
		this.resetMoved();
	}

	public void movingDistance(Animal b) {
		if (this.minDistance != 0) {
			for (int i = 0; i < this.swarm.length; i++) {
				if (this.swarm[b.index].distance(this.swarm[i]) < minDistance) {
					this.testDistance();
				}
			}
		}
	}

	// checks if testDistance & moves defined there are needed - this takes a couple
	// of seconds while the randomized swarm is being made
	public void establishDistance() {
		if (this.minDistance != 0) {
			// cap improves runtime in cases where one node is caught on the line between two nodes
			int cap = this.swarmsize / 5;
			for (int i = 0; i < this.swarm.length; i++) {
				for (int j = 0; j < this.swarm.length; j++) {
					if (this.swarm[i].distance(swarm[j]) > this.minDistance && cap != 0) {
						this.testDistance();
						cap--;
					}
				}
			}
		}
	}

	// angle etc in eigene Methode ausgelagert
	public double[] distanceHelper(double helpDistance, double xDistance, double yDistance) {
		// [xMove, yMove]
		double[] res = new double[2];
		double angle = Math.atan(xDistance / yDistance);
		double xMove = helpDistance * Math.sin(angle);
		double yMove = xMove / Math.tan(angle);
		res[0] = xMove;
		res[1] = yMove;
		return res;
	}

	// Behandlung von Mindestabstandsverletzungen
	// Die Magnitüde & Richtung der Bewegung richtet sich nach helpDistance und
	// geschieht entlang der Geraden zwischen Punkt i & j
	public void testDistance() {
		for (int i = 0; i < this.swarm.length; i++) {
			for (int j = 0; j < this.swarm.length; j++) {
				if (this.swarm[i].distance(this.swarm[j]) < minDistance && i != j) {
					double helpDistance = minDistance - this.swarm[i].distance(this.swarm[j]);
					double xDistance = (this.swarm[i].xcoord - this.swarm[j].xcoord);
					double yDistance = (this.swarm[i].ycoord - this.swarm[j].ycoord);
					double xMove = this.distanceHelper(helpDistance, xDistance, yDistance)[0];
					double yMove = this.distanceHelper(helpDistance, xDistance, yDistance)[1];
					
					if (this.swarm[j].ycoord < this.swarm[i].ycoord && this.swarm[j].xcoord < this.swarm[i].xcoord) {
						this.swarm[j].quickDown((yMove));
						this.swarm[j].quickLeft((xMove));
					} else if (this.swarm[j].ycoord > this.swarm[i].ycoord
							&& this.swarm[j].xcoord > this.swarm[i].xcoord) {
						this.swarm[j].quickUp((yMove));
						this.swarm[j].quickRight((xMove));
					} else if (this.swarm[j].ycoord < this.swarm[i].ycoord
							&& this.swarm[j].xcoord > this.swarm[i].xcoord) {
						this.swarm[j].quickDown((yMove));
						this.swarm[j].quickRight((xMove));
					} else if (this.swarm[j].ycoord > this.swarm[i].ycoord
							&& this.swarm[j].xcoord < this.swarm[i].xcoord) {
						this.swarm[j].quickUp((yMove));
						this.swarm[j].quickLeft((xMove));
					}
					// Vogel wird nach links bewegt, falls er sich rechts von dem Anderen befindet,
					// sonst nach rechts
					else if (this.swarm[j].ycoord == this.swarm[i].ycoord) {
						if (this.swarm[j].xcoord < this.swarm[i].xcoord) {
							this.swarm[j].quickLeft(helpDistance);
						} else if (this.swarm[j].xcoord > this.swarm[i].xcoord) {
							this.swarm[j].quickRight(helpDistance);
						}
					}
					// Vogel wird nach unten bewegt, falls er sich unter dem Anderen befindet, sonst
					// nach oben
					else if (this.swarm[j].xcoord == this.swarm[i].xcoord) {
						if (this.swarm[j].ycoord < this.swarm[i].ycoord) {
							this.swarm[j].quickDown(helpDistance);
						} else if (this.swarm[j].ycoord > this.swarm[i].ycoord) {
							this.swarm[j].quickUp(helpDistance);
						}
					}
				}
			}
		}
	}

	// method to start up prebuilt simulations
	public void start() throws InterruptedException {		
		
		// all bird swarms behave the same ... the rules of movement are predetermined
		this.select = (int) (Math.random() * this.swarm.length - 1);
		double a = 4 + (double) Math.round((Math.random() * 14) *100) / 100;
		double b = 8 + (double) Math.round((Math.random() * 8) *100) / 100;
		for (int i = 0; i < 10; i++) {
			this.swarm[this.select].moveAnimal(this, a, 0, 0, b);
			this.resetMoved();
		}		
		this.select = (int) (Math.random() * this.swarm.length - 1);
		// diagonal movement - works with NO, SO, SW, NO only
		for (int i = 0; i < 200 / 4; i++) {
			this.swarm[this.select].moveAnimal(this, 0, 8, 8, 0);
			this.resetMoved();
		}
	}
}
