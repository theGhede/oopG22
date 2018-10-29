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
		double[] xvalues = new double[this.swarm.length];
		double[] yvalues = new double[this.swarm.length];
		for (int i = 0; i < this.swarm.length; i++) {
			xvalues[i] = (Math.random() * 400) + 200;
			yvalues[i] = (Math.random() * 400) + 200;
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
	public void start(Flock s, int size, int minD, String type, double dangerX, double dangerY) throws InterruptedException {

		s.makeswarm(type, size, minD);
		// stressed & tired
		for (int i = 0; i < s.swarm.length; i++) {
			s.swarm[i].stressed = true;
			// TODO: fix danger variables
			s.swarm[i].danger(dangerX, dangerY);
		}
		int closest = 0;
		double max = 0, xdist, ydist, dist = 0;
		for (int i = 0; i < s.swarm.length; i++) {
			xdist = Math.pow((s.swarm[i].xcoord - dangerX), 2);
			ydist = Math.pow((s.swarm[i].ycoord - dangerY), 2);
			dist = Math.sqrt(xdist + ydist);
			if (dist > max) {
				closest = i;
				max = dist;
			}
		}
		// the closest bird to danger starts fleeing
		s.select = closest;
		double x = s.swarm[closest].xcoord;
		double y = s.swarm[closest].ycoord;
		// moveRight = x1, moveLeft = x2, moveUp = y1, moveDown = y2
		// danger is east & south or equal
		if (x >= dangerX && y >= dangerY) {
			for (int i = 0; i < 20; i++) {
				s.swarm[s.select].moveAnimal(s, 90 - dist, 0, 90 - dist, 0);
				s.resetMoved();
			}
		}
		if (x < dangerX && y > dangerY) {
			for (int i = 0; i < 20; i++) {
				s.swarm[s.select].moveAnimal(s, 0, 90 - dist, 90 - dist, 0);
				s.resetMoved();
			}
		}
		if (x > dangerX && y < dangerY) {
			for (int i = 0; i < 20; i++) {
				s.swarm[s.select].moveAnimal(s, 90 - dist, 0, 0, 90 - dist);
				s.resetMoved();
			}
		}
		if (x < dangerX && y < dangerY) {
			for (int i = 0; i < 20; i++) {
				s.swarm[s.select].moveAnimal(s, 0, 90 - dist, 0, 90 - dist);
				s.resetMoved();
			}
		}

		double[] center = new double[2];
		double averageX = 0, averageY = 0;
		for (int i = 0; i < s.swarm.length; i++) {
			// center & tired movement
			s.swarm[i].modifier = 1;
			averageX += s.swarm[i].xcoord;
			averageY += s.swarm[i].ycoord;
			averageX = averageX / s.swarm.length;
			averageY = averageY / s.swarm.length;
			center[0] = averageX;
			center[1] = averageY;
		}
		for (int i = 0; i < s.swarm.length; i++) {
			s.swarm[i].recenter(center);
		}
	}
}
