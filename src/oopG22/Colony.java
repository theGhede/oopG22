package oopG22;

public class Colony extends Swarm {

	Insect[] swarm;
	
	public void neighborhood(Insect b, int radius) {
		int j = 0;
		int amount = (int) (5 + (15 * Math.random()));
		Insect[] neighbors = new Insect[amount];
		for (int i = 0; i < this.swarm.length; i++) {
			if (i != b.index && j < amount && b.distance(this.swarm[i]) <= radius) {
				neighbors[j] = this.swarm[i];
				j++;
			}
		}
		if (j < amount) {
			this.neighborhood(b, radius + 1);
		} else {
			b.neighbors = neighbors;
		}
	}

	// Make swarm within the center 400x400 of the JFrame; reminder: top-right =
	// (0,0)
	@Override
	public void makeswarm(String type, int size, int minDistance) {
		this.type = type;
		this.swarmsize = size;
		this.minDistance = minDistance;
		this.swarm = new Insect[size];
		double[] xvalues = new double[this.swarm.length];
		double[] yvalues = new double[this.swarm.length];
		for (int i = 0; i < this.swarm.length; i++) {
			xvalues[i] = (double) Math.round(((Math.random() * 400) + 200) *100) / 100;
			yvalues[i] = (double) Math.round(((Math.random() * 400) + 200) *100) / 100;
		}

		for (int i = 0; i < this.swarm.length; i++) {
			Insect b = new Insect();
			b.xcoord = xvalues[i];
			b.ycoord = yvalues[i];
			b.index = i;
			this.swarm[i] = b;
			b.modifier = 1;
		}
		// Find neighbors for each Animal
		for (int i1 = 0; i1 < this.swarm.length; i1++) {
			this.neighborhood(this.swarm[i1], 1);
		}
		// check and repair minimal distance infringements
		this.establishDistance();
		this.resetMoved();
	}

	// method to start up prebuilt simulations
	@Override
	public void start() throws InterruptedException {
		/* NOTE: leader is used in order to save the one non-follower in the swarm instead of having to look for it each time
		 * 		 follower itself is a property that could be called leader and inverted
		 */
		this.makeswarm(type, size, minD);
		int leader = 0;
		double xs = 0, max = 0;
		for (int i = 0; i < this.swarm.length; i++) {
			this.swarm[i].follower = true;
			xs = this.swarm[i].xcoord;
			if (xs > max) {
				max = xs;
				leader = i;
			}
		}
		this.swarm[leader].follower = false;
		for (int i = 0; i < this.swarm.length; i++) {
			for (int j = 0; j < 70; j++) {
				if (!this.swarm[i].follower) this.swarm[i].lane(11, 0);
				if (this.swarm[i].follower) this.swarm[i].lane(10, this.swarm[i].ycoord - this.swarm[leader].ycoord);
			}
		}
	}
}
