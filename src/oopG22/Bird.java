package oopG22;

public class Bird extends Animal {

	/* GUT: Die Methode distance wurde von Animal vererbt, das ist eine Methode die Klassenzusammenhält erhöht,
		die Methode wird ganz oft benutzt. Genau so wie alle move Methoden.
	*/
	/* NOTIZE: Objekt variablen von Bird und Animal sind package, daher könnte die Objektkopplung potentiel stärker sein,
	   sie könnten private sein, dann bräuchte man Getters.
	 */
	Bird[] neighbors;
	boolean stressed;
	boolean tired;
	/* GUT: statisch gebunden, overloaded. Die Methode moveAnimal befindet sich sowohl in Klasse Bird als auch in die
	Klasse Anmial(unterschiedliche Parametern). Anstatt dass wir 2 verschieden Methode haben, was unübersichtlich ist,
	benutzen wir die gleiche Methode sowohl für ein Swarm, als auch für ein Flock */
	public void moveAnimal(Flock s, double x1, double x2, double y1, double y2) throws InterruptedException {
		if (!this.moved) {
			this.moveUp(y1);
			this.moveDown(y2);
			this.moveRight(x1);
			this.moveLeft(x2);
			this.moved = true;
			s.movingDistance(this);
		}
		if (this.neighbors != null) {
			for (int i = 0; i < this.neighbors.length; i++) {
				if (!this.neighbors[i].moved) {
					this.neighbors[i].moveAnimal(s, x1, x2, y1, y2);
				}
			}
		}
	}

	public void danger(double xsource, double ysource) {
		double xdist = Math.pow((this.xcoord - xsource), 2);
		double ydist = Math.pow((this.ycoord - ysource), 2);
		double dist = Math.sqrt(xdist + ydist);
		if (dist > 300 && this.stressed) {
			this.modifier = 1;
			this.tired = false;
		}
		if (200 < dist && dist <= 300 && this.stressed) {
			this.modifier = 1.2;
			this.tired = false;
		}
		if (100 < dist && dist <= 200 && this.stressed) {
			this.modifier = 1.4;
			this.tired = true;
		}
		if (dist <= 100 && this.stressed) {
			this.modifier = 1.6;
			this.tired = true;
		}
	}

	/* GUT: eine Objektmethode die Klassenzusammenhält erhöht. Recenter ist eine Methode, die von allen objekten von
	   typ Bird ausgeführt werden kann. */
	public void recenter(Flock s, double[] center) throws InterruptedException {
		double x = center[0];
		double y = center[1];
		if (!this.tired) {
			if (this.xcoord - x > 0)
				moveLeft(Math.min(20, this.xcoord - x));

			if (this.xcoord - x < 0)
				moveRight(Math.min(20, x - this.xcoord));

			if (this.ycoord - y > 0)
				moveDown(Math.min(20, this.ycoord - y));

			if (this.ycoord - y < 0)
				moveUp(Math.min(20, y - this.ycoord));
		}
		double pushed = 0, min = 800;
		if (this.tired) {
			for (int i = 0; i < s.swarm.length; i++) {
				if (!s.swarm[i].tired && this.distance(s.swarm[i]) < min) {
					min = this.distance(s.swarm[i]);
					pushed = 2 * Math.sqrt(min);
				}
			}
			if (this.xcoord - x > 0)
				moveRight(pushed);

			if (this.xcoord - x < 0)
				moveLeft(pushed);

			if (this.ycoord - y > 0)
				moveUp(pushed);

			if (this.ycoord - y < 0)
				moveDown(pushed);
		}
		s.movingDistance(this);
	}
}
