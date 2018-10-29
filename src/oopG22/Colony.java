package oopG22;

public class Colony extends Swarm {

	Insect[] swarm;

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
	public void start(Colony s, int size, int minD, String type) throws InterruptedException {
		/* NOTE: leader is used in order to save the one non-follower in the swarm instead of having to look for it each time
		 * 		 follower itself is a property that could be called leader and inverted
		 */
		s.makeswarm(type, size, minD);
		int leader = 0;
		double xs = 0, max = 0;
		for (int i = 0; i < s.swarm.length; i++) {
			s.swarm[i].follower = true;
			xs = s.swarm[i].xcoord;
			if (xs > max) {
				max = xs;
				leader = i;
			}
		}
		s.swarm[leader].follower = false;
		for (int i = 0; i < s.swarm.length; i++) {
			for (int j = 0; j < 70; j++) {
				if (!s.swarm[i].follower) s.swarm[i].lane(11, 0);
				if (s.swarm[i].follower) s.swarm[i].lane(10, s.swarm[i].ycoord - s.swarm[leader].ycoord);
			}
		}
	}
}
