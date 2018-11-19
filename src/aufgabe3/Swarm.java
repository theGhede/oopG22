package aufgabe3;

/* GOOD: High class cohesion - With one exception all methods here are concerned with
 * the swarm itself and the relation between multiple animals: making it, filling it with animals,
 * finding each animals neighborhood (see note), ensuring the swarm respects the rules imposed by
 * minDistance (after being generated and during movement) and resetting swarm movement (which resets
 * all the animals movement, instead of setting it one by one).
 * 
 * While movingDistance logically could be part of Animal it requires swarm[].length to be accessible - so it's here.
 * Possibly worth mentioning is that each swarm knows the animals it consists of but no animal knows the swarm it's part of.
 * 
 * The mentioned exception is the start method. This could be part of it's own class, but since each
 * type of swarm has its own distinct start method we would require one additional class (and Swarm as a parameter)
 * and we don't think having it here hurts cohesion enough to pay that price. */
public class Swarm {
	
	private String type;
	private int swarmsize;
	private int minDistance;
	private int select;
	private Animal[] swarm;

	public Animal[] getSwarm() {
		return swarm;
	}

	public void setSwarm(Animal[] swarm) {
		this.swarm = swarm;
	}

	public int getSelect() {
		return select;
	}

	public void setSelect(int select) {
		this.select = select;
	}

	public int getMinDistance() {
		return minDistance;
	}

	public void setMinDistance(int minDistance) {
		this.minDistance = minDistance;
	}

	public int getSwarmsize() {
		return swarmsize;
	}

	public void setSwarmsize(int swarmsize) {
		this.swarmsize = swarmsize;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	// NOTE: we feel this is more part of the swarm and about the relation between
	// multiple animals than the individual animal itself - also it's practical to
	// have this here
	// (neighbors are not injective: a knows that b is it's neighbor but b has
	// no idea who a is)
	public void neighborhood(Animal b, int radius) {
		int current = 0;
		int amount = (int) (5 + (15 * Math.random()));
		if (amount > getSwarmsize() || getSwarmsize() <= 5) {
			amount = getSwarmsize();
		}
		Animal[] neighbors = new Animal[amount];
		for (int i = 0; i < this.getSwarm().length; i++) {
			if (i != b.getIndex() && current < amount && b.distance(this.getSwarm()[i]) <= radius) {
				neighbors[current] = this.getSwarm()[i];
				current++;
			}
		}
		if (current < amount) {
			this.neighborhood(b, radius + 1);
		} else {
			b.setNeighbors(neighbors);
			// Postcondition: assertion { b now has (between 5 and 20 neighbors) or (min(amount, swarmsize) neighbors) }
		}
	}

	// GOOD: despite being such a simple method having it is a boon in terms of
	// reusability and simplicity
	public void resetMoved() {
		for (int i = 0; i < this.getSwarm().length; i++) {
			// BAD: strong coupling - looser coupling is achievable by adding complexity in
			// form of a setter method in Animal
			this.getSwarm()[i].setMoved(false);
		}
	}

	public void makeswarm(String type, int size, int minDistance) {
		this.setType(type);
		this.setSwarmsize(size);
		this.setMinDistance(minDistance);
		this.setSwarm(new Animal[size]);
		// ERROR: below we may have had the opportunity to shorten the code by assigning
		// the coordinates directly
		double[] xvalues = new double[this.getSwarm().length];
		double[] yvalues = new double[this.getSwarm().length];
		for (int i = 0; i < this.getSwarm().length; i++) {
			xvalues[i] = (double) Math.round(((Math.random() * 400) + 200) * 100) / 100;
			yvalues[i] = (double) Math.round(((Math.random() * 400) + 200) * 100) / 100;
			// Postcondtion: assertion { x & y values = (200,600) }
			// so all animals start in the center of the JFrame, which can be a bit of a
			// downside for large swarms
		}
		for (int i = 0; i < this.getSwarm().length; i++) {
			// NOTE: considering the fact that the swarm is in it's essence a structure of
			// individual animals
			Animal b = new Animal();
			b.setXcoord(xvalues[i]);
			b.setYcoord(yvalues[i]);
			b.setIndex(i);
			this.getSwarm()[i] = b;
			b.setModifier(1);
		}
		for (int i = 0; i < this.getSwarm().length; i++) {
			this.neighborhood(this.getSwarm()[i], 1);
		}
		// ERROR: we might as well check if it's worth calling establishDistance before
		// doing so (pointless for minDistance <= 0); we do however check for exactly
		// that right at the start of the method, which just is slightly less efficient
		// (same can be said for moving distance)
		this.establishDistance();
		this.resetMoved();
		/*
		 * after makeswarm is executed we assert that there is a usable swarm consisting
		 * of Animals with all their required parameters to be set in a way that makes
		 * it ready to be displayed, the minimal distance to be approximately adhered
		 * and its members to be ready to be moved { swarm[i] != null && swarm[i] == a
		 * fully functioning animal object }
		 */
	}

	public void movingDistance(Animal b) {
		if (this.getMinDistance() != 0) {
			/*
			 * NOTE: For sake of expediency & a more organic result we call testDistance
			 * only once after each Animal has been moved, well knowing that the result
			 * might temporarily not be in line with the minimal distance (because the
			 * animal infringing on it could be pushed too close to another animal that has
			 * already been looked at) - however since this is done many times for each use
			 * of moveAnimal over time minDistance will ultimately be adhered to
			 */
			for (int i = 0; i < this.getSwarm().length; i++) {
				if (this.getSwarm()[b.getIndex()].distance(this.getSwarm()[i]) < getMinDistance()) {
					this.testDistance();
				}
			}
		}
	}

	public void establishDistance() {
		if (this.getMinDistance() != 0) {
			/*
			 * ERROR: With the asymptotic runtime being as high as it is to keep our
			 * randomly generated Swarm from getting to cozy with eachother we concede that
			 * through introducing a cap (willfully chosen, but relative to the swarmsize)
			 * that limits how often we check for new minimal distance infringements in
			 * order to speed up Swarm generation. As such the individuals of our Swarm will
			 * only approximately
			 */
			int cap = this.getSwarmsize() / 4;
			for (int i = 0; i < this.getSwarm().length; i++) {
				for (int j = 0; j < this.getSwarm().length; j++) {
					if (this.getSwarm()[i].distance(getSwarm()[j]) > this.getMinDistance() && cap != 0) {
						this.testDistance();
						cap--;
					}
				}
			}
		}
	}

	// NOTE: this might as well be part of another class and doesn't add to class
	// cohesion, but since it belongs to the method below we find having it here
	// feels more intuitive
	public double[] distanceHelper(double helpDistance, double xDistance, double yDistance) {
		double[] res = new double[2];
		double angle = Math.atan(xDistance / yDistance);
		double xMove = helpDistance * Math.cos(angle);
		double yMove = helpDistance * Math.sin(angle);
		res[0] = xMove;
		res[1] = yMove;
		return res;
	}

	/*
	 * NOTE: we are well aware of this methods cognitive complexity, distributing it
	 * to multiple other methods would add more complexity to the class overall
	 */
	public void testDistance() {
		for (int i = 0; i < this.getSwarm().length; i++) {
			for (int j = 0; j < this.getSwarm().length; j++) {
				if (this.getSwarm()[i].distance(this.getSwarm()[j]) < getMinDistance() && i != j) {
					double helpDistance = getMinDistance() - this.getSwarm()[i].distance(this.getSwarm()[j]);
					double xDistance = (this.getSwarm()[j].getXcoord() - this.getSwarm()[i].getXcoord());
					double yDistance = (this.getSwarm()[j].getYcoord() - this.getSwarm()[i].getYcoord());
					double xMove = this.distanceHelper(helpDistance, xDistance, yDistance)[0];
					double yMove = this.distanceHelper(helpDistance, xDistance, yDistance)[1];
					if (this.getSwarm()[j].getYcoord() < this.getSwarm()[i].getYcoord() && this.getSwarm()[j].getXcoord() < this.getSwarm()[i].getXcoord()) {
						this.getSwarm()[j].quickUp(yMove);
						this.getSwarm()[j].quickLeft(xMove);
					} else if (this.getSwarm()[j].getYcoord() > this.getSwarm()[i].getYcoord()
							&& this.getSwarm()[j].getXcoord() > this.getSwarm()[i].getXcoord()) {
						this.getSwarm()[j].quickDown(yMove);
						this.getSwarm()[j].quickRight(xMove);
					} else if (this.getSwarm()[j].getYcoord() < this.getSwarm()[i].getYcoord()
							&& this.getSwarm()[j].getXcoord() > this.getSwarm()[i].getXcoord()) {
						this.getSwarm()[j].quickUp(yMove);
						this.getSwarm()[j].quickRight(xMove);
					} else if (this.getSwarm()[j].getYcoord() > this.getSwarm()[i].getYcoord()
							&& this.getSwarm()[j].getXcoord() < this.getSwarm()[i].getXcoord()) {
						this.getSwarm()[j].quickDown(yMove);
						this.getSwarm()[j].quickLeft(xMove);
					} else if (this.getSwarm()[j].getYcoord() == this.getSwarm()[i].getYcoord()) {
						if (this.getSwarm()[j].getXcoord() < this.getSwarm()[i].getXcoord()) {
							this.getSwarm()[j].quickLeft(helpDistance);
						} else if (this.getSwarm()[j].getXcoord() > this.getSwarm()[i].getXcoord()) {
							this.getSwarm()[j].quickRight(helpDistance);
						}
					} else if (this.getSwarm()[j].getXcoord() == this.getSwarm()[i].getXcoord()) {
						if (this.getSwarm()[j].getYcoord() < this.getSwarm()[i].getYcoord()) {
							this.getSwarm()[j].quickUp(helpDistance);
						} else if (this.getSwarm()[j].getYcoord() > this.getSwarm()[i].getYcoord()) {
							this.getSwarm()[j].quickDown(helpDistance);
						}
					}
				}
			}
		}
	}

	// NOTE: this is basically our 'pre-run input', here we made up the rules used
	// for moving the swarm and then use incremental movement for the simulation

	// GOOD: having this here (and explicitly not scattered throughout Test.main) is
	// good object coupling, we wanted this to be compact and accessible in order to
	// communicate what is being done to the swarm after it has been generated
	public void start() throws InterruptedException {
		/*
		 * BAD: strong object coupling (although Animal and Swarm logically and
		 * otherwise belong together anyway) - a *simple* getter method however wouldn't
		 * help us here, since we want select to be the random nth animal and be a
		 * different one for both movements
		 * 
		 * so the price in added complexity for looser object coupling would be higher
		 * than for typeToDraw - depending on how willing one is to pay that price this
		 * might be a NOTE instead of BAD & the lack of encapsulation makes it more
		 * usable for us, since this is a much easier way to manipulate all the
		 * variables that determine the rules of the swarm movement
		 */

		this.setSelect((int) (Math.random() * (this.getSwarm().length - 1)));
		double a = 4 + (double) Math.round((Math.random() * 14) * 100) / 100;
		double b = 8 + (double) Math.round((Math.random() * 8) * 100) / 100;
		for (int i = 0; i < 10; i++) {
			this.getSwarm()[this.getSelect()].moveAnimal(this, a, 0, 0, b);
			this.resetMoved();
		}
		this.setSelect((int) (Math.random() * this.getSwarm().length - 1));
		for (int i = 0; i < 10; i++) {
			this.getSwarm()[this.getSelect()].moveAnimal(this, 0, 8, 8, 0);
			this.resetMoved();
		}
	}
}
