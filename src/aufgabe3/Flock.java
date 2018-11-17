package aufgabe3;

public class Flock extends Swarm {
	/* NOITZE: Objekt variablen von Flock und Swarm sind package, potentiel kann die Objektkopplung stärker werden,
	   sie könnten private sein, dann bräuchte man Getters.
    */
	private Bird[] swarm;
	/* NOTIZE: assesrtion { birds.swarmsize >= 20 } schon früher etwähnt.
	   GUT: Die Methode neighborhood ist  overloaded und statisch verbunden. Diese Methode gibt es sowohl in Swarm, als auch in Flock.
	   Das erleichtert die Berechnung von 2 verschiedene neighborhoods, Swarm und Flock neighborhood
	*/

	public Bird[] getSwarm() {
		return swarm;
	}
	public void setSwarm(Bird[] swarm) {
		this.swarm = swarm;
	}
	
	public void neighborhood(Bird b, int radius) {
		int current = 0;
		int amount = (int) (5 + (15 * Math.random()));
		if (amount > getSwarmsize() || getSwarmsize() <= 5) {
			amount = getSwarmsize();
		}
		Bird[] neighbors = new Bird[amount];
		for (int i = 0; i < this.getSwarm().length; i++) {
			if (i != b.getIndex() && current < amount && b.distance(this.getSwarm()[i]) <= radius) {
				neighbors[current] = this.getSwarm()[i];
				current++;
			}
		}
		if (current < amount) {
			this.neighborhood(b, radius + 1);
		} else {
			b.setNeighbors(neighbors);
		}
	}
 	/* GUT: Dynamisches Binden resetMoved, makeswarm, testDistance, establishDistance sind die Methode,
 	 die dynamisch verbunden sind mit der Methoden von Superklasse Swarm, so unterscheidet man Methoden für Bird Array,
 	  und für Swarm Array.
 	  */
	@Override
	public void resetMoved() {
		for (int i = 0; i < this.getSwarm().length; i++) {
			this.getSwarm()[i].setMoved(false);
		}
	}

	@Override
	public void makeswarm(String type, int size, int minDistance) {
		this.setType(type);
		this.setSwarmsize(size);
		this.setMinDistance(minDistance);
		this.setSwarm(new Bird[size]); // tu je razlika
		double[] xvalues = new double[this.getSwarm().length];
		double[] yvalues = new double[this.getSwarm().length];
		for (int i = 0; i < this.getSwarm().length; i++) {
			xvalues[i] = (double) Math.round(((Math.random() * 400) + 200) * 100) / 100;
			yvalues[i] = (double) Math.round(((Math.random() * 400) + 200) * 100) / 100;
		}

		for (int i = 0; i < this.getSwarm().length; i++) {
			Bird b = new Bird();
			b.setXcoord(xvalues[i]);
			b.setYcoord(yvalues[i]);
			b.setIndex(i);
			this.getSwarm()[i] = b;
			b.setModifier(1);
		}
		for (int i = 0; i < this.getSwarm().length; i++) {
			this.neighborhood(this.getSwarm()[i], 1);
		}
		this.establishDistance();
		this.resetMoved();
	}
	/* SCHLECHT: Klasse Flock kann den Animal Attributen zugreifen was eine starke Objektkopplung ist, zum beispiel
		b.index
	*/
	public void movingDistance(Bird b) {
		if (this.getMinDistance() != 0) {
			for (int i = 0; i < this.getSwarm().length; i++) {
				if (this.getSwarm()[b.getIndex()].distance(this.getSwarm()[i]) < getMinDistance()) {
					this.testDistance();
				}
			}
		}
	}

	@Override
	public void testDistance() {
		for (int i = 0; i < this.getSwarm().length; i++) {
			for (int j = 0; j < this.getSwarm().length; j++) {
				if (this.getSwarm()[i].distance(this.getSwarm()[j]) < getMinDistance() && i != j) {
					double helpDistance = getMinDistance() - this.getSwarm()[i].distance(this.getSwarm()[j]);
					double xDistance = (this.getSwarm()[j].getXcoord() - this.getSwarm()[i].getXcoord());
					double yDistance = (this.getSwarm()[j].getYcoord() - this.getSwarm()[i].getYcoord());
					double xMove = this.distanceHelper(helpDistance, xDistance, yDistance)[0];
					double yMove = this.distanceHelper(helpDistance, xDistance, yDistance)[1];
					if (this.getSwarm()[j].getYcoord() < this.getSwarm()[i].getYcoord() && this.getSwarm()[j].getXcoord() < this.getSwarm()[i].getXcoord()) {
						this.getSwarm()[j].quickUp(yMove);
						this.getSwarm()[j].quickLeft(xMove);
					} else if (this.getSwarm()[j].getYcoord() > this.getSwarm()[i].getYcoord()
							&& this.getSwarm()[j].getXcoord() > this.getSwarm()[i].getXcoord()) {
						this.getSwarm()[j].quickDown(yMove);
						this.getSwarm()[j].quickRight(xMove);
					} else if (this.getSwarm()[j].getYcoord() < this.getSwarm()[i].getYcoord()
							&& this.getSwarm()[j].getXcoord() > this.getSwarm()[i].getXcoord()) {
						this.getSwarm()[j].quickUp(yMove);
						this.getSwarm()[j].quickRight(xMove);
					} else if (this.getSwarm()[j].getYcoord() > this.getSwarm()[i].getYcoord()
							&& this.getSwarm()[j].getXcoord() < this.getSwarm()[i].getXcoord()) {
						this.getSwarm()[j].quickDown(yMove);
						this.getSwarm()[j].quickLeft(xMove);
					} else if (this.getSwarm()[j].getYcoord() == this.getSwarm()[i].getYcoord()) {
						if (this.getSwarm()[j].getXcoord() < this.getSwarm()[i].getXcoord()) {
							this.getSwarm()[j].quickLeft(helpDistance);
						} else if (this.getSwarm()[j].getXcoord() > this.getSwarm()[i].getXcoord()) {
							this.getSwarm()[j].quickRight(helpDistance);
						}
					} else if (this.getSwarm()[j].getXcoord() == this.getSwarm()[i].getXcoord()) {
						if (this.getSwarm()[j].getYcoord() < this.getSwarm()[i].getYcoord()) {
							this.getSwarm()[j].quickUp(helpDistance);
						} else if (this.getSwarm()[j].getYcoord() > this.getSwarm()[i].getYcoord()) {
							this.getSwarm()[j].quickDown(helpDistance);
						}
					}
				}
			}
		}
	}

	@Override
	public void establishDistance() {
		if (this.getMinDistance() != 0) {
			int cap = this.getSwarmsize() / 5;
			for (int i = 0; i < this.getSwarm().length; i++) {
				for (int j = 0; j < this.getSwarm().length; j++) {
					if (this.getSwarm()[i].distance(getSwarm()[j]) > this.getMinDistance() && cap != 0) {
						this.testDistance();
						cap--;
					}
				}
			}
		}
	}
	//Good: Overloaded. Methode start befindet sich auch in der Klasse Swarm, aber mit unterschiedlichen Prametern.
	public void start(double dangerX, double dangerY) throws InterruptedException {
		for (int i = 0; i < this.getSwarm().length; i++) {
			this.getSwarm()[i].setStressed(true);
			this.getSwarm()[i].danger(dangerX, dangerY);
		}
		int closest = 0;
		double min = 800, xdist, ydist, dist = 0;
		for (int i = 0; i < this.getSwarm().length; i++) {
			xdist = Math.pow((this.getSwarm()[i].getXcoord() - dangerX), 2);
			ydist = Math.pow((this.getSwarm()[i].getYcoord() - dangerY), 2);
			dist = Math.sqrt(xdist + ydist);
			if (dist < min) {
				closest = i;
				min = dist;
			}
		}
		this.setSelect(closest);
		double x = this.getSwarm()[closest].getXcoord();
		double y = this.getSwarm()[closest].getYcoord();

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
				this.getSwarm()[this.getSelect()].moveAnimal(this, 16, 0, 16, 0);
				this.resetMoved();
			}
		}
		if (x < dangerX && y > dangerY) {
			for (int i = 0; i < fleeingDistance; i++) {
				this.getSwarm()[this.getSelect()].moveAnimal(this, 0, 16, 16, 0);
				this.resetMoved();
			}
		}
		if (x > dangerX && y < dangerY) {
			for (int i = 0; i < fleeingDistance; i++) {
				this.getSwarm()[this.getSelect()].moveAnimal(this, 16, 0, 0, 16);
				this.resetMoved();
			}
		}
		if (x < dangerX && y < dangerY) {
			for (int i = 0; i < fleeingDistance; i++) {
				this.getSwarm()[this.getSelect()].moveAnimal(this, 0, 16, 0, 16);
				this.resetMoved();
			}
		}

		double[] center = new double[2];
		double averageX = 0, averageY = 0;
		for (int i = 0; i < this.getSwarm().length; i++) {
			this.getSwarm()[i].setStressed(false);
			this.getSwarm()[i].setModifier(1);
			averageX += this.getSwarm()[i].getXcoord();
			averageY += this.getSwarm()[i].getYcoord();
		}
		center[0] = averageX / this.getSwarm().length;
		center[1] = averageY / this.getSwarm().length;

		for (int i = 0; i < this.getSwarm().length; i++) {
			this.getSwarm()[i].recenter(this, center);
		}
	}
}
