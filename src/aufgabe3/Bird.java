package aufgabe3;

public class Bird extends Animal {

	/* GUT: Die Methode distance wurde von Animal vererbt, das ist eine Methode die Klassenzusammenhält erhöht,
		die Methode wird ganz oft benutzt. Genau so wie alle move Methoden.
	*/
	/* NOTIZE: Objekt variablen von Bird und Animal sind package, daher könnte die Objektkopplung potentiel stärker sein,
	   sie könnten private sein, dann bräuchte man Getters.
	 */
	private Bird[] neighbors;
	private boolean stressed;
	private boolean tired;
	public boolean isStressed() {
		return stressed;
	}

	public void setStressed(boolean stressed) {
		this.stressed = stressed;
	}

	public Bird[] getNeighbors() {
		return neighbors;
	}

	public void setNeighbors(Bird[] neighbors) {
		this.neighbors = neighbors;
	}

	/* GUT: statisch gebunden, overloaded. Die Methode moveAnimal befindet sich sowohl in Klasse Bird als auch in die
	Klasse Anmial(unterschiedliche Parametern). Anstatt dass wir 2 verschieden Methode haben, was unübersichtlich ist,
	benutzen wir die gleiche Methode sowohl für ein Swarm, als auch für ein Flock */
	public void moveAnimal(Flock s, double x1, double x2, double y1, double y2) throws InterruptedException {
		if (!this.isMoved()) {
			this.moveUp(y1);
			this.moveDown(y2);
			this.moveRight(x1);
			this.moveLeft(x2);
			this.setMoved(true);
			s.movingDistance(this);
		}
		if (this.getNeighbors() != null) {
			for (int i = 0; i < this.getNeighbors().length; i++) {
				if (!this.getNeighbors()[i].isMoved()) {
					this.getNeighbors()[i].moveAnimal(s, x1, x2, y1, y2);
				}
			}
		}
	}

	public void danger(double xsource, double ysource) {
		double xdist = Math.pow((this.getXcoord() - xsource), 2);
		double ydist = Math.pow((this.getYcoord() - ysource), 2);
		double dist = Math.sqrt(xdist + ydist);
		if (dist > 300 && this.isStressed()) {
			this.setModifier(1);
			this.tired = false;
		}
		if (200 < dist && dist <= 300 && this.isStressed()) {
			this.setModifier(1.2);
			this.tired = false;
		}
		if (100 < dist && dist <= 200 && this.isStressed()) {
			this.setModifier(1.4);
			this.tired = true;
		}
		if (dist <= 100 && this.isStressed()) {
			this.setModifier(1.6);
			this.tired = true;
		}
	}

	/* GUT: eine Objektmethode die Klassenzusammenhält erhöht. Recenter ist eine Methode, die von allen objekten von
	   typ Bird ausgeführt werden kann. */
	public void recenter(Flock s, double[] center) throws InterruptedException {
		double x = center[0];
		double y = center[1];
		if (!this.tired) {
			if (this.getXcoord() - x > 0)
				moveLeft(Math.min(20, this.getXcoord() - x));

			if (this.getXcoord() - x < 0)
				moveRight(Math.min(20, x - this.getXcoord()));

			if (this.getYcoord() - y > 0)
				moveDown(Math.min(20, this.getYcoord() - y));

			if (this.getYcoord() - y < 0)
				moveUp(Math.min(20, y - this.getYcoord()));
		}
		double pushed = 0, min = 800;
		if (this.tired) {
			for (int i = 0; i < s.getSwarm().length; i++) {
				if (!s.getSwarm()[i].tired && this.distance(s.getSwarm()[i]) < min) {
					min = this.distance(s.getSwarm()[i]);
					pushed = 2 * Math.sqrt(min);
				}
			}
			if (this.getXcoord() - x > 0)
				moveRight(pushed);

			if (this.getXcoord() - x < 0)
				moveLeft(pushed);

			if (this.getYcoord() - y > 0)
				moveUp(pushed);

			if (this.getYcoord() - y < 0)
				moveDown(pushed);
		}
		s.movingDistance(this);
	}
}
