package aufgabe3;

public class Colony extends Swarm {

	Insect[] swarm;
/* GUT:  Dynamishes Binden.Eine Colony ist ein Swarm von insekten. Man könnte weniger Code schreiben und diese Methoden
	vererben, aber so könnte man verschiede Sorten von Swarm nicht darstellen.
	*/
	@Override
	public void makeswarm(String type, int size, int minDistance) {
		this.type = type;
		this.swarmsize = size;
		this.minDistance = minDistance;
		this.swarm = new Insect[size];
		double[] xvalues = new double[this.swarm.length];
		double[] yvalues = new double[this.swarm.length];
		for (int i = 0; i < this.swarm.length; i++) {
			xvalues[i] = (double) Math.round(((Math.random() * 400) + 200) * 100) / 100;
			yvalues[i] = (double) Math.round(((Math.random() * 400) + 200) * 100) / 100;
		}

		for (int i = 0; i < this.swarm.length; i++) {
			Insect b = new Insect();
			b.xcoord = xvalues[i];
			b.ycoord = yvalues[i];
			b.index = i;
			this.swarm[i] = b;
			b.modifier = 1;
		}
	}

	@Override
	public void start() throws InterruptedException {
		/*
		 * NOTE: leader is used in order to save the one non-follower in the swarm
		 * instead of having to look for it each time follower itself is a property that
		 * could be called leader and inverted
		 */
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
		for (int j = 0; j < 38; j++) {
			for (int i = 0; i < this.swarm.length; i++) {
				if (!this.swarm[i].follower)
					this.swarm[i].lane(19, (double)Math.round(Math.random() * 6) - 3);
				if (this.swarm[i].follower && this.swarm[i].ycoord - this.swarm[leader].ycoord > 0)
					this.swarm[i].lane(16, (double)Math.min(16, this.swarm[i].ycoord - this.swarm[leader].ycoord));
				if (this.swarm[i].follower && this.swarm[i].ycoord - this.swarm[leader].ycoord < 0)
					this.swarm[i].lane(16, (double)Math.max(-16, this.swarm[i].ycoord - this.swarm[leader].ycoord));
			}
		}
	}
}
