package oopG22;

import java.util.concurrent.TimeUnit;

// Tiere bestehen aus Koordinaten & einer Nachbarschaft; jeder Vogel hat für einfacheren Zugriff einen ID als index gespeichert
public class Animal {

	double xcoord;
	double ycoord;
	Animal[] neighbors;
	int index;
	boolean moved;
	// this lets us manipulate the range of each animals movement without
	// manipulating the movement instructions themselves
	double modifier;

	void moveUp(double k) throws InterruptedException {
		for (int i = 0; i < (k * this.modifier); i++) {
			TimeUnit.MICROSECONDS.sleep(200);
			double move = this.ycoord + 1;
			this.ycoord = move;
		}
	}

	void moveDown(double k) throws InterruptedException {
		for (int i = 0; i < (k * this.modifier); i++) {
			TimeUnit.MICROSECONDS.sleep(200);
			double move = this.ycoord - 1;
			this.ycoord = move;
		}
	}

	void moveLeft(double k) throws InterruptedException {
		for (int i = 0; i < (k * this.modifier); i++) {
			TimeUnit.MICROSECONDS.sleep(200);
			double move = this.xcoord - 1;
			this.xcoord = move;
		}
	}

	void moveRight(double k) throws InterruptedException {
		for (int i = 0; i < (k * this.modifier); i++) {
			TimeUnit.MICROSECONDS.sleep(200);
			double move = this.xcoord + 1;
			this.xcoord = move;
		}
	}

	// quicker methods for keeping minimal distance while making swarms
	// options: some amount of almost duplicate code or slower swarm generation or
	// an additional parameter
	void quickUp(double k) {
		double move = this.ycoord + k;
		this.ycoord = move;
	}

	void quickDown(double k) {
		double move = this.ycoord - k;
		this.ycoord = move;
	}

	void quickLeft(double k) {
		double move = this.xcoord - k;
		this.xcoord = move;
	}

	void quickRight(double k) {
		double move = this.xcoord + k;
		this.xcoord = move;
	}

	/* Im Flug wird überprüft ob der sich gerade fortbewegende Vogel zu nah an
	 * andere annähert Rekursiv fliegen alle Tiere in alle Nachbarschaften mit
	 * gleicher Entfernung in die gleiche Richtung Angestoßen wird die Bewegung
	 * durch einen einzelnes gewähltes Tier
	 */
	public void moveAnimal(Swarm s, double x1, double x2, double y1, double y2) throws InterruptedException {
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

	// Berechnen der euklidischen Distanz
	public double distance(Animal b) {
		double xdist = Math.pow((this.xcoord - b.xcoord), 2);
		double ydist = Math.pow((this.ycoord - b.ycoord), 2);
		return Math.sqrt(xdist + ydist);
	}
}
