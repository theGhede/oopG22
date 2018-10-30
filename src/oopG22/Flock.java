package oopG22;

public class Flock extends Swarm {

	Bird[] swarm;

	public void neighborhood(Bird b, int radius) {
		int j = 0;
		int amount = (int) (5 + (15 * Math.random()));
		Bird[] neighbors = new Bird[amount];
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

	@Override
	public void resetMoved() {
		for (int i = 0; i < this.swarm.length; i++) {
			this.swarm[i].moved = false;
		}
	}

	// Make swarm within the center 400x400 of the JFrame; reminder: top-right =
	// (0,0)
	@Override
	public void makeswarm(String type, int size, int minDistance) {
		this.type = type;
		this.swarmsize = size;
		this.minDistance = minDistance;
		this.swarm = new Bird[size];
		double[] xvalues = new double[this.swarm.length];
		double[] yvalues = new double[this.swarm.length];
		for (int i = 0; i < this.swarm.length; i++) {
			xvalues[i] = (double) Math.round(((Math.random() * 400) + 200) * 100) / 100;
			yvalues[i] = (double) Math.round(((Math.random() * 400) + 200) * 100) / 100;
		}

		for (int i = 0; i < this.swarm.length; i++) {
			Bird b = new Bird();
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

	public void movingDistance(Bird b) {
		if (this.minDistance != 0) {
			for (int i = 0; i < this.swarm.length; i++) {
				if (this.swarm[b.index].distance(this.swarm[i]) < minDistance) {
					this.testDistance();
				}
			}
		}
	}

	@Override
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

	// checks if testDistance & moves defined there are needed - this takes a couple
	// of seconds while the randomized swarm is being made
	@Override
	public void establishDistance() {
		if (this.minDistance != 0) {
			// cap improves runtime in cases where one node is caught on the line between
			// two nodes
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

	// method to start up prebuilt simulations
	public void start(double dangerX, double dangerY) throws InterruptedException {
		// stressed & tired
		for (int i = 0; i < this.swarm.length; i++) {
			this.swarm[i].stressed = true;
			this.swarm[i].danger(dangerX, dangerY);
		}
		int closest = 0;
		double min = 800, xdist, ydist, dist = 0;
		for (int i = 0; i < this.swarm.length; i++) {
			xdist = Math.pow((this.swarm[i].xcoord - dangerX), 2);
			ydist = Math.pow((this.swarm[i].ycoord - dangerY), 2);
			dist = Math.sqrt(xdist + ydist);
			if (dist < min) {
				closest = i;
				min = dist;
			}
		}
		// the closest bird to danger starts fleeing
		this.select = closest;
		double x = this.swarm[closest].xcoord;
		double y = this.swarm[closest].ycoord;

		// moveRight = x1, moveLeft = x2, moveUp = y1, moveDown = y2
		// danger is east & south or equal
		/*
		 * NOTE: all animals start moving away with 20* (90 - dist), where dist is the
		 * distance of the bird sensing the danger (= closest to it) - so the further
		 * away the danger the slower the first and overall movement, additionally all
		 * birds in a certain radius to the danger are stressed and fly faster on an
		 * individual basis
		 */
		int fleeingDistance;
		if (dist < 100)
			fleeingDistance = 10;
		else if (dist < 200)
			fleeingDistance = 8;
		else if (dist < 300)
			fleeingDistance = 6;
		else if (dist < 400)
			fleeingDistance = 4;
		else
			fleeingDistance = 2;

		if (x >= dangerX && y >= dangerY) {
			for (int i = 0; i < fleeingDistance; i++) {
				this.swarm[this.select].moveAnimal(this, 16, 0, 16, 0);
				this.resetMoved();
			}
		}
		if (x < dangerX && y > dangerY) {
			for (int i = 0; i < fleeingDistance; i++) {
				this.swarm[this.select].moveAnimal(this, 0, 16, 16, 0);
				this.resetMoved();
			}
		}
		if (x > dangerX && y < dangerY) {
			for (int i = 0; i < fleeingDistance; i++) {
				this.swarm[this.select].moveAnimal(this, 16, 0, 0, 16);
				this.resetMoved();
			}
		}
		if (x < dangerX && y < dangerY) {
			for (int i = 0; i < fleeingDistance; i++) {
				this.swarm[this.select].moveAnimal(this, 0, 16, 0, 16);
				this.resetMoved();
			}
		}

		double[] center = new double[2];
		double averageX = 0, averageY = 0;
		for (int i = 0; i < this.swarm.length; i++) {
			// center & tired movement
			this.swarm[i].stressed = false;
			this.swarm[i].modifier = 1;
			averageX += this.swarm[i].xcoord;
			averageY += this.swarm[i].ycoord;
		}
		center[0] = averageX / this.swarm.length;
		center[1] = averageY / this.swarm.length;

		for (int i = 0; i < this.swarm.length; i++) {
			this.swarm[i].recenter(this, center);
		}
	}
}
