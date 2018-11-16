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
	String type;
	int swarmsize;
	int minDistance;
	int select;
	Animal[] swarm;

	// NOTE: we feel this is more part of the swarm and about the relation between
	// multiple animals than the individual animal itself - also it's practical to
	// have this here
	// (neighbors are not injective: a knows that b is it's neighbor but b has
	// no idea who a is)
	public void neighborhood(Animal b, int radius) {
		// ERROR: j is not the most optimal name we could've chosen for this variable,
		// as it doesn't tell us what it is or what's it for
		int j = 0;
		int amount = (int) (5 + (15 * Math.random()));
		if (amount <= swarmsize || swarmsize <= 5) amount = swarmsize;
			Animal[] neighbors = new Animal[amount];
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
			// assertion { b now has between 5 and 20 neighbors }
		}
	}

	// GOOD: despite being such a simple method having it is a boon in terms of
	// reusability and simplicity
	public void resetMoved() {
		for (int i = 0; i < this.swarm.length; i++) {
			// BAD: strong coupling - looser coupling is achievable by adding complexity in
			// form of a setter method in Animal
			this.swarm[i].moved = false;
		}
	}

	public void makeswarm(String type, int size, int minDistance) {
		this.type = type;
		this.swarmsize = size;
		this.minDistance = minDistance;
		this.swarm = new Animal[size];
		// ERROR: below we may have had the opportunity to shorten the code by assigning
		// the coordinates directly
		double[] xvalues = new double[this.swarm.length];
		double[] yvalues = new double[this.swarm.length];
		for (int i = 0; i < this.swarm.length; i++) {
			xvalues[i] = (double) Math.round(((Math.random() * 400) + 200) * 100) / 100;
			yvalues[i] = (double) Math.round(((Math.random() * 400) + 200) * 100) / 100;
			// assertion { x & y values = (200,600) }
			// so all animals start in the center of the JFrame, which can be a bit of a
			// downside for large swarms
		}
		for (int i = 0; i < this.swarm.length; i++) {
			// NOTE: considering the fact that the swarm is in it's essence a structure of
			// individual animals
			Animal b = new Animal();
			b.xcoord = xvalues[i];
			b.ycoord = yvalues[i];
			b.index = i;
			this.swarm[i] = b;
			b.modifier = 1;
		}
		for (int i = 0; i < this.swarm.length; i++) {
			this.neighborhood(this.swarm[i], 1);
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
		if (this.minDistance != 0) {
			/*
			 * NOTE: For sake of expediency & a more organic result we call testDistance
			 * only once after each Animal has been moved, well knowing that the result
			 * might temporarily not be in line with the minimal distance (because the
			 * animal infringing on it could be pushed too close to another animal that has
			 * already been looked at) - however since this is done many times for each use
			 * of moveAnimal over time minDistance will ultimately be adhered to
			 */
			for (int i = 0; i < this.swarm.length; i++) {
				if (this.swarm[b.index].distance(this.swarm[i]) < minDistance) {
					this.testDistance();
				}
			}
		}
	}

	public void establishDistance() {
		if (this.minDistance != 0) {
			/*
			 * ERROR: With the asymptotic runtime being as high as it is to keep our
			 * randomly generated Swarm from getting to cozy with eachother we concede that
			 * through introducing a cap (willfully chosen, but relative to the swarmsize)
			 * that limits how often we check for new minimal distance infringements in
			 * order to speed up Swarm generation. As such the individuals of our Swarm will
			 * only approximately
			 */
			int cap = this.swarmsize / 5;
			for (int i = 0; i < this.swarm.length; i++) {
				for (int j = 0; j < this.swarm.length; j++) {
					if (this.swarm[i].distance(swarm[j]) > this.minDistance && cap != 0) {
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
		double xMove = helpDistance * Math.sin(angle);
		double yMove = xMove / Math.tan(angle);
		res[0] = xMove;
		res[1] = yMove;
		return res;
	}

	/*
	 * NOTE: we are well aware of this methods cognitive complexity, distributing it
	 * to multiple other methods would add more complexity to the class overall
	 */
	public void testDistance() {
		for (int i = 0; i < this.swarm.length; i++) {
			for (int j = 0; j < this.swarm.length; j++) {
				if (this.swarm[i].distance(this.swarm[j]) < minDistance && i != j) {
					double helpDistance = minDistance - this.swarm[i].distance(this.swarm[j]);
					// assertion { helpDistance > 0 }
					double xDistance = (this.swarm[i].xcoord - this.swarm[j].xcoord);
					double yDistance = (this.swarm[i].ycoord - this.swarm[j].ycoord);
					double xMove = this.distanceHelper(helpDistance, xDistance, yDistance)[0];
					double yMove = this.distanceHelper(helpDistance, xDistance, yDistance)[1];
					/*
					 * ERROR: JFrame (0,0) is the upper left corner; the y movement is very
					 * inefficient since upwards and downwards movement are inverted - the actual
					 * effect of this isn't as much of a problem however, with this method being
					 * called as frequently as it is but it should be made more efficient
					 */
					if (this.swarm[j].ycoord < this.swarm[i].ycoord && this.swarm[j].xcoord < this.swarm[i].xcoord) {
						this.swarm[j].quickDown((yMove));
						this.swarm[j].quickLeft((xMove));
					} else if (this.swarm[j].ycoord > this.swarm[i].ycoord
							&& this.swarm[j].xcoord > this.swarm[i].xcoord) {
						this.swarm[j].quickUp((yMove));
						this.swarm[j].quickRight((xMove));
					} else if (this.swarm[j].ycoord < this.swarm[i].ycoord
							&& this.swarm[j].xcoord > this.swarm[i].xcoord) {
						this.swarm[j].quickDown((yMove));
						this.swarm[j].quickRight((xMove));
					} else if (this.swarm[j].ycoord > this.swarm[i].ycoord
							&& this.swarm[j].xcoord < this.swarm[i].xcoord) {
						this.swarm[j].quickUp((yMove));
						this.swarm[j].quickLeft((xMove));
					} else if (this.swarm[j].ycoord == this.swarm[i].ycoord) {
						if (this.swarm[j].xcoord < this.swarm[i].xcoord) {
							this.swarm[j].quickLeft(helpDistance);
						} else if (this.swarm[j].xcoord > this.swarm[i].xcoord) {
							this.swarm[j].quickRight(helpDistance);
						}
					} else if (this.swarm[j].xcoord == this.swarm[i].xcoord) {
						if (this.swarm[j].ycoord < this.swarm[i].ycoord) {
							this.swarm[j].quickDown(helpDistance);
						} else if (this.swarm[j].ycoord > this.swarm[i].ycoord) {
							this.swarm[j].quickUp(helpDistance);
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

		// ERROR: because of the random nature this has not been come up during testing,
		// but we are of course missing brackets around this.swarm.length - 1!
		this.select = (int) (Math.random() * this.swarm.length - 1);
		// assertion { select is not out of bounds }
		double a = 4 + (double) Math.round((Math.random() * 14) * 100) / 100;
		double b = 8 + (double) Math.round((Math.random() * 8) * 100) / 100;
		for (int i = 0; i < 10; i++) {
			this.swarm[this.select].moveAnimal(this, a, 0, 0, b);
			this.resetMoved();
		}
		this.select = (int) (Math.random() * this.swarm.length - 1);
		for (int i = 0; i < 10; i++) {
			this.swarm[this.select].moveAnimal(this, 0, 8, 8, 0);
			this.resetMoved();
		}
	}
}
