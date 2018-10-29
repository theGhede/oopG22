package oopG22;

public class Flock extends Swarm {

		Bird[] swarm;
		
		// Make swarm within the center 400x400 of the JFrame; reminder: top-right = (0,0)
		public void makeswarm(String type, int size, int minDistance) {
			this.type = type;
			this.swarmsize = size;
			this.minDistance = minDistance;
			double[] xvalues = new double[this.swarm.length];
			double[] yvalues = new double[this.swarm.length];
			for(int i = 0; i < this.swarm.length; i++) {
				xvalues[i] = (Math.random() * 400) + 200;
				yvalues[i] = (Math.random() * 400) + 200;
			}
			
			for(int i = 0; i < this.swarm.length; i++) {
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
		
}
