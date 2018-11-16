package aufgabe3;

public class Flock extends Swarm {
	/* NOITZE: Objekt variablen von Flock und Swarm sind package, potentiel kann die Objektkopplung stärker werden,
	   sie könnten private sein, dann bräuchte man Getters.
    */
	Bird[] swarm;
	/* NOTIZE: assesrtion { birds.swarmsize >= 20 } schon früher etwähnt.
	   GUT: Die Methode neighborhood ist  overloaded und statisch verbunden. Diese Methode gibt es sowohl in Swarm, als auch in Flock.
	   Das erleichtert die Berechnung von 2 verschiedene neighborhoods, Swarm und Flock neighborhood
	*/

	public void neighborhood(Bird b, int radius) {
		int j = 0;
		int amount = (int) (5 + (15 * Math.random()));
		if (amount <= swarmsize || swarmsize <= 5) amount = swarmsize;
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
 	/* GUT: Dynamisches Binden resetMoved, makeswarm, testDistance, establishDistance sind die Methode,
 	 die dynamisch verbunden sind mit der Methoden von Superklasse Swarm, so unterscheidet man Methoden für Bird Array,
 	  und für Swarm Array.
 	  */
	@Override
	public void resetMoved() {
		for (int i = 0; i < this.swarm.length; i++) {
			this.swarm[i].moved = false;
		}
	}

	@Override
	public void makeswarm(String type, int size, int minDistance) {
		this.type = type;
		this.swarmsize = size;
		this.minDistance = minDistance;
		this.swarm = new Bird[size]; // tu je razlika
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
		for (int i = 0; i < this.swarm.length; i++) {
			this.neighborhood(this.swarm[i], 1);
		}
		this.establishDistance();
		this.resetMoved();
	}
	/* SCHLECHT: Klasse Flock kann den Animal Attributen zugreifen was eine starke Objektkopplung ist, zum beispiel
		b.index
	*/
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
					else if (this.swarm[j].ycoord == this.swarm[i].ycoord) {
						if (this.swarm[j].xcoord < this.swarm[i].xcoord) {
							this.swarm[j].quickLeft(helpDistance);
						} else if (this.swarm[j].xcoord > this.swarm[i].xcoord) {
							this.swarm[j].quickRight(helpDistance);
						}
					}
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

	@Override
	public void establishDistance() {
		if (this.minDistance != 0) {
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
	//Good: Overloaded. Methode start befindet sich auch in der Klasse Swarm, aber mit unterschiedlichen Prametern.
	public void start(double dangerX, double dangerY) throws InterruptedException {
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
		this.select = closest;
		double x = this.swarm[closest].xcoord;
		double y = this.swarm[closest].ycoord;

		/* NOTE: all animals start moving away with 20* (90 - dist), where dist is the
		 * distance of the bird sensing the danger to the danger (= closest to it) - so the further
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
