package oopG22;

// regular Animal including new behavior for tiring and stress
public class Bird extends Animal {
	
	Bird[] neighbors;
	boolean stressed;
	boolean tired;
	
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

	// method to be periodically called while moving away from danger to update
	// 'modifier' depending on distance to dangerzone
	public void danger(double xsource, double ysource) {
		// distance needs to be redone for this because the original method's parameters
		// don't apply
		double xdist = Math.pow((this.xcoord - xsource), 2);
		double ydist = Math.pow((this.ycoord - ysource), 2);
		double dist = Math.sqrt(xdist + ydist);
		// make rules for modifiers if stressed and close enough to the danger
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

	public void recenter(double[] center) throws InterruptedException {
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
		if (this.tired) {
			if (this.xcoord - x > 0)
				moveRight(16);

			if (this.xcoord - x < 0)
				moveLeft(16);

			if (this.ycoord - y > 0)
				moveUp(16);

			if (this.ycoord - y < 0)
				moveDown(16);
		}
	}
}
