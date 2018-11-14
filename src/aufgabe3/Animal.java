package aufgabe3;

import java.util.concurrent.TimeUnit;

/* GOOD: High class cohesion - We do not feel that it could be improved by moving any of
 * the methods here to any other existing or even new class. Every method does
 * something specific and useful with/to/for our individual Animal. We have no methods
 * concerning singular individual animals in other classes either.
 * We see the distance from one animal to another from this animals point of view
 * (this animal wants to know how far the other one is away from itself). Depending on
 * point of view this could potentially be part of a different class if we wanted to.
 * (movingDistance is commented on in Swarm) */
public class Animal {

	double xcoord;
	double ycoord;
	Animal[] neighbors;
	int index;
	boolean moved;
	double modifier;

	/* NOTE: logically the following four methods only make sense for k > 0 we also
	 * made a compromise for move to be only done in whole integers to achieve more
	 * fluid movement */
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

	/*
	 * NOTE: while k can be any number for these methods to produce a proper result,
	 * however we assume that it is known to whoever calls these methods that for
	 * k < 0 Up is Down and Left is Right
	 */
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

	public void moveAnimal(Swarm s, double x1, double x2, double y1, double y2) throws InterruptedException {
		// NOTE: calling this method without having called resetMoved is possible but wouldn't be very
		// goal oriented for this reason we made assertions elsewhere that resetMoved is called
		// before this is
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

	// GOOD: having this method and using it appropriately leads to looser object coupling 
	// (mentioning this here instead of where the method is called since in Swarm alone it's used
	// half a dozen of times)
	public double distance(Animal b) {
		double xdist = Math.pow((this.xcoord - b.xcoord), 2);
		double ydist = Math.pow((this.ycoord - b.ycoord), 2);
		return Math.sqrt(xdist + ydist);
	}
}
