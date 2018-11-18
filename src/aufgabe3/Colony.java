package aufgabe3;

public class Colony extends Swarm {

	private Insect[] swarm;
	
	public Insect[] getSwarm() {
		return swarm;
	}

	public void setSwarm(Insect[] swarm) {
		this.swarm = swarm;
	}

	@Override
	public void makeswarm(String type, int size, int minDistance) {
		this.setType(type);
		this.setSwarmsize(size);
		this.setMinDistance(minDistance);
		this.setSwarm(new Insect[size]);
		double[] xvalues = new double[this.getSwarm().length];
		double[] yvalues = new double[this.getSwarm().length];
		for (int i = 0; i < this.getSwarm().length; i++) {
			xvalues[i] = (double) Math.round(((Math.random() * 400) + 200) * 100) / 100;
			yvalues[i] = (double) Math.round(((Math.random() * 400) + 200) * 100) / 100;
		}

		for (int i = 0; i < this.getSwarm().length; i++) {
			Insect b = new Insect();
			b.setXcoord(xvalues[i]);
			b.setYcoord(yvalues[i]);
			b.setIndex(i);
			this.getSwarm()[i] = b;
			b.setModifier(1);
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
		for (int i = 0; i < this.getSwarm().length; i++) {
			this.getSwarm()[i].setFollower(true);
			xs = this.getSwarm()[i].getXcoord();
			if (xs > max) {
				max = xs;
				leader = i;
			}
		}
		this.getSwarm()[leader].setFollower(false);
		for (int j = 0; j < 38; j++) {
			for (int i = 0; i < this.getSwarm().length; i++) {
				if (!this.getSwarm()[i].isFollower())
					this.getSwarm()[i].lane(19, (double)Math.round(Math.random() * 6) - 3);
				if (this.getSwarm()[i].isFollower() && this.getSwarm()[i].getYcoord() - this.getSwarm()[leader].getYcoord() > 0)
					this.getSwarm()[i].lane(16, (double)Math.min(16, this.getSwarm()[i].getYcoord() - this.getSwarm()[leader].getYcoord()));
				if (this.getSwarm()[i].isFollower() && this.getSwarm()[i].getYcoord() - this.getSwarm()[leader].getYcoord() < 0)
					this.getSwarm()[i].lane(16, (double)Math.max(-16, this.getSwarm()[i].getYcoord() - this.getSwarm()[leader].getYcoord()));
			}
		}
	}
}
