package oopG22;

public class Flock extends Swarm {

	Bird[] swarm;

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
			xvalues[i] = (double) Math.round(((Math.random() * 400) + 200) *100) / 100;
			yvalues[i] = (double) Math.round(((Math.random() * 400) + 200) *100) / 100;
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

	// method to start up prebuilt simulations
	public void start(int size, int minD, String type, double dangerX, double dangerY) throws InterruptedException {

		this.makeswarm(type, size, minD);
		// stressed & tired
		for (int i = 0; i < this.swarm.length; i++) {
			this.swarm[i].stressed = true;
			// TODO: fix danger variables
			this.swarm[i].danger(dangerX, dangerY);
		}
		int closest = 0;
		double max = 0, xdist, ydist, dist = 0;
		for (int i = 0; i < this.swarm.length; i++) {
			xdist = Math.pow((this.swarm[i].xcoord - dangerX), 2);
			ydist = Math.pow((this.swarm[i].ycoord - dangerY), 2);
			dist = Math.sqrt(xdist + ydist);
			if (dist > max) {
				closest = i;
				max = dist;
			}
		}
		// the closest bird to danger starts fleeing
		this.select = closest;
		double x = this.swarm[closest].xcoord;
		double y = this.swarm[closest].ycoord;
		// moveRight = x1, moveLeft = x2, moveUp = y1, moveDown = y2
		// danger is east & south or equal
		/* NOTE: all animals start moving away with 20* (90 - dist), where dist is the distance of the bird sensing the danger
		 * (= closest to it) - so the further away the danger the slower the first and overall movement, additionally all birds
		 * in a certain radius to the danger are stressed and fly faster on an individual basis
		 */
		if (x >= dangerX && y >= dangerY) {
			for (int i = 0; i < 20; i++) {
				this.swarm[this.select].moveAnimal(this, 90 - dist, 0, 90 - dist, 0);
				this.resetMoved();
			}
		}
		if (x < dangerX && y > dangerY) {
			for (int i = 0; i < 20; i++) {
				this.swarm[this.select].moveAnimal(this, 0, 90 - dist, 90 - dist, 0);
				this.resetMoved();
			}
		}
		if (x > dangerX && y < dangerY) {
			for (int i = 0; i < 20; i++) {
				this.swarm[this.select].moveAnimal(this, 90 - dist, 0, 0, 90 - dist);
				this.resetMoved();
			}
		}
		if (x < dangerX && y < dangerY) {
			for (int i = 0; i < 20; i++) {
				this.swarm[this.select].moveAnimal(this, 0, 90 - dist, 0, 90 - dist);
				this.resetMoved();
			}
		}

		double[] center = new double[2];
		double averageX = 0, averageY = 0;
		for (int i = 0; i < this.swarm.length; i++) {
			// center & tired movement
			this.swarm[i].modifier = 1;
			averageX += this.swarm[i].xcoord;
			averageY += this.swarm[i].ycoord;
			averageX = averageX / this.swarm.length;
			averageY = averageY / this.swarm.length;
			center[0] = averageX;
			center[1] = averageY;
		}
		for (int i = 0; i < this.swarm.length; i++) {
			this.swarm[i].recenter(center);
		}
	}
}
