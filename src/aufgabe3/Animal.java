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

	private double xcoord;
	private double ycoord;
	private Animal[] neighbors;
	private int index;
	private boolean moved;
	// Invariante (Server & Client) zu modifier: macht inhaltlich nur für positive Werte Sinn
	private double modifier;

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public double getXcoord() {
		return xcoord;
	}

	public void setXcoord(double xcoord) {
		this.xcoord = xcoord;
	}
	
	public double getYcoord() {
		return ycoord;
	}

	public void setYcoord(double ycoord) {
		this.ycoord = ycoord;
	}

	public Animal[] getNeighbors() {
		return neighbors;
	}

	public void setNeighbors(Animal[] neighbors) {
		this.neighbors = neighbors;
	}

	public boolean isMoved() {
		return moved;
	}

	public void setMoved(boolean moved) {
		this.moved = moved;
	}

	public double getModifier() {
		return modifier;
	}

	public void setModifier(double modifier) {
		this.modifier = modifier;
	}

	/* NOTE: logically the following four methods only make sense for k > 0 we also
	 * made a compromise for move to be only done in whole integers to achieve more
	 * fluid movement */
	void moveUp(double k) throws InterruptedException {
		for (int i = 0; i < (k * this.getModifier()); i++) {
			TimeUnit.MICROSECONDS.sleep(200);
			double move = this.getYcoord() + 1;
			this.setYcoord(move);
		}
	}

	void moveDown(double k) throws InterruptedException {
		for (int i = 0; i < (k * this.getModifier()); i++) {
			TimeUnit.MICROSECONDS.sleep(200);
			double move = this.getYcoord() - 1;
			this.setYcoord(move);
		}
	}

	void moveLeft(double k) throws InterruptedException {
		for (int i = 0; i < (k * this.getModifier()); i++) {
			TimeUnit.MICROSECONDS.sleep(200);
			double move = this.getXcoord() - 1;
			this.setXcoord(move);
		}
	}

	void moveRight(double k) throws InterruptedException {
		for (int i = 0; i < (k * this.getModifier()); i++) {
			TimeUnit.MICROSECONDS.sleep(200);
			double move = this.getXcoord() + 1;
			this.setXcoord(move);
		}
	}

	/*
	 * NOTE: while k can be any number for these methods to produce a proper result,
	 * however we assume that it is known to whoever calls these methods that for
	 * k < 0 Up is Down and Left is Right
	 */
	void quickUp(double k) {
		double move = this.getYcoord() + k;
		this.setYcoord(move);
	}

	void quickDown(double k) {
		double move = this.getYcoord() - k;
		this.setYcoord(move);
	}

	void quickLeft(double k) {
		double move = this.getXcoord() - k;
		this.setXcoord(move);
	}

	void quickRight(double k) {
		double move = this.getXcoord() + k;
		this.setXcoord(move);
	}

	public void moveAnimal(Swarm s, double x1, double x2, double y1, double y2) throws InterruptedException {
		// NOTE: calling this method without having called resetMoved is possible but wouldn't be very
		// goal oriented for this reason we made assertions elsewhere that resetMoved is called
		// before this is
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

	// GOOD: having this method and using it appropriately leads to looser object coupling 
	// (mentioning this here instead of where the method is called since in Swarm alone it's used
	// half a dozen of times)
	public double distance(Animal b) {
		double xdist = Math.pow((this.getXcoord() - b.getXcoord()), 2);
		double ydist = Math.pow((this.getYcoord() - b.getYcoord()), 2);
		return Math.sqrt(xdist + ydist);
	}
}
