package oopG22;

import java.util.concurrent.TimeUnit;

public class Animal {

	double xcoord;
	double ycoord;
	Animal[] neighbors;
	int index;
	boolean moved;
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

	public double distance(Animal b) {
		double xdist = Math.pow((this.xcoord - b.xcoord), 2);
		double ydist = Math.pow((this.ycoord - b.ycoord), 2);
		return Math.sqrt(xdist + ydist);
	}
}
