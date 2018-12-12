package augabe8;

import java.util.ArrayList;
import java.util.List;

public class Swarm {

	private int id;
	private List<Fish> swarm;
	private Fish[][] matrix;
	private SynchroGroup sg;

	private static final int SIZE = 24;
	private static final int FISHES = 6;

	public Swarm(int id) {
		this.id = id;
		this.swarm = new ArrayList<>();
		this.matrix = new Fish[SIZE][SIZE];
	}

	public List<Fish> getSwarm() {
		return this.swarm;
	}

	public void setSg(SynchroGroup sg) {
		this.sg = sg;
	}

	public int useMatrixLength() {
		return this.matrix.length - 1;
	}

	public void start() {
		for (Fish fish : swarm) {
			fish.start();
		}
	}

	// creates a swarm of fish with random coordinates
	public void makeSwarm() {
		for (int i = 0; i < (SIZE * SIZE / FISHES); i++) {
			int x = (int) (Math.random() * this.matrix.length - 1);
			int y = (int) (Math.random() * this.matrix.length - 1);
			double facing = Math.random();
			int direction = 0;
			if (facing <= 0.25)
				direction = 12;
			if (facing > 0.25 && facing <= 0.50)
				direction = 6;
			if (facing > 0.50 && facing <= 0.75)
				direction = 3;
			if (facing > 0.75)
				direction = 9;
			Fish fish = new Fish(direction, x, y);
			fish.setGroup(this);
			fish.setSg(this.sg);
			swarm.add(fish);
		}
		this.newSwarmCollision();
	}

	// special collision for new swarm
	public void newSwarmCollision() {
		for (Fish fish : swarm) {
			for (Fish other : swarm) {
				// Note: when creating fish randomly it is possibly to have multiple fish having
				// the same coordinates, which we remedy here
				if (!fish.equals(other) && fish.getX() == other.getX() && fish.getY() == other.getY()) {
					other.setX((int) (Math.random() * this.matrix.length - 1));
					other.setY((int) (Math.random() * this.matrix.length - 1));
				}
			}
			// fish has a direct upper/lower neighbor and looks at them will turn left or
			// right before starting
			if ((fish.getDirection() == 12 && this.above(fish)) || (fish.getDirection() == 6 && this.below(fish)))
				fish.edgeFacing();
		}
		for (Fish fish : swarm) {
			for (Fish other : swarm) {
				if (!fish.equals(other) && fish.getX() == other.getX() && fish.getY() == other.getY()) {
					this.newSwarmCollision();
				}
			}
		}
	}

	public boolean above(Fish fish) {
		for (Fish f : swarm) {
			if (fish.getX() == f.getX() && (fish.getY() == f.getY() - 1))
				return true;
		}
		return false;
	}

	public boolean below(Fish fish) {
		for (Fish f : swarm) {
			if (fish.getX() == f.getX() && (fish.getY() == f.getY() + 1))
				return true;
		}
		return false;
	}

	// checks if fish is on a collision course with another fish
	public boolean collision(Fish fish, int turning) {
		// if fish is going straight
		if (turning == 0) {
			switch (fish.getDirection()) {
			case 12:
				return this.topNeighbors(fish);
			case 6:
				return this.bottomNeighbors(fish);
			case 3:
				return this.rightNeighbors(fish);
			case 9:
				return this.leftNeighbors(fish);
			default:
				return false;
			}
		}
		// if fish wants to turn left
		if (turning == 1) {
			switch (fish.getDirection()) {
			case 12:
				return this.leftNeighbors(fish);
			case 6:
				return this.rightNeighbors(fish);
			case 3:
				return this.topNeighbors(fish);
			case 9:
				return this.bottomNeighbors(fish);
			default:
				return false;
			}
		}
		// if fish wants to turn right
		if (turning == 2) {
			switch (fish.getDirection()) {
			case 12:
				return this.rightNeighbors(fish);
			case 6:
				return this.leftNeighbors(fish);
			case 3:
				return this.bottomNeighbors(fish);
			case 9:
				return this.topNeighbors(fish);
			default:
				return false;
			}
		}
		return false;
	}

	public boolean leftNeighbors(Fish fish) {
		for (Fish f : swarm) {
			if (fish.getY() == f.getY() && (fish.getX() == f.getX() + 1 || fish.getX() == f.getX() + 2)) {
				return true;
			}
		}
		return false;
	}

	public boolean rightNeighbors(Fish fish) {
		for (Fish f : swarm) {
			if (fish.getY() == f.getY() && (fish.getX() == f.getX() - 1 || fish.getX() == f.getX() - 2)) {
				return true;
			}
		}
		return false;
	}

	public boolean topNeighbors(Fish fish) {
		for (Fish f : swarm) {
			if (fish.getX() == f.getX() && (fish.getY() == f.getY() - 1 || fish.getY() == f.getY() - 2)) {
				return true;
			}
		}
		return false;
	}

	public boolean bottomNeighbors(Fish fish) {
		for (Fish f : swarm) {
			if (fish.getX() == f.getX() && (fish.getY() == f.getY() + 1 || fish.getY() == f.getY() + 2)) {
				return true;
			}
		}
		return false;
	}

	public void convert() {
		for (Fish fish : swarm) {
			fish.getX();
			fish.getY();
			this.matrix[fish.getY()][fish.getX()] = fish;
		}
	}

	public boolean liveSwarm() {
		for (Fish fish : swarm) {
			if (fish.isAlive())
				return true;
		}
		return false;
	}

	public void print() {
		System.out.println("\n        run swarm" + this.id + ": " + "\n");
		this.print(matrix.length - 1);
		System.out.println("\n        swarm" + this.id + " is alive: " + this.liveSwarm() + "\n");
		System.out.println("        fish details: ");
		for (Fish fish : swarm) {
			fish.details();
		}
	}

	public void print(int i) {
		for (Fish fish : swarm) {
			// handling the display of fishes in first/last column looking up/down (since
			// these would simply not have their heads displayed otherwise)
			if ((fish.getY() == this.matrix.length - 1 && fish.getDirection() == 12)
					|| (fish.getY() == 0 && fish.getDirection() == 6))
				fish.edgeFacing();
			// if a fish gets boxed in and the program ends (32x waiting) before the fish
			// can turn in a way that doesn't leave it looking up/down with other fish
			// directly above/below it
			if ((above(fish) && fish.getDirection() == 12) || (below(fish) && fish.getDirection() == 6))
				fish.edgeFacing();
			for (Fish other : swarm) {
				// if a fish looks up/down and the fish two spaces away from it's position looks
				// down/up
				if (fish.getX() == other.getX() && ((fish.getY() == other.getY() - 2 && fish.getDirection() == 12
						&& other.getDirection() == 6)
						|| (fish.getY() == other.getY() + 2 && fish.getDirection() == 6 && other.getDirection() == 12)))
					fish.edgeFacing();
				// since the X marks the coordinates it can happen that the head of a fish
				// looking up/down points to a spot in the appropriate columns String which is
				// already occupied by < or > of another fish
				if ((fish.getY() == other.getY() - 1 && fish.getDirection() == 12
						&& ((fish.getX() == other.getX() + 1 && other.getDirection() == 3)
								|| (fish.getX() == other.getX() - 1 && other.getDirection() == 9)))
						|| (fish.getY() == other.getY() + 1 && fish.getDirection() == 6
								&& ((fish.getX() == other.getX() + 1 && other.getDirection() == 3)
										|| (fish.getX() == other.getX() - 1 && other.getDirection() == 9))))
					fish.edgeFacing();
			}
		}
		this.convert();
		String s = "  ";
		String empty = "   ";
		for (int j = 0; j < matrix[i].length; j++) {
			// top column
			if (i == matrix.length - 1) {
				if (matrix[i][j] != null) {
					s += matrix[i][j].toString();
				} else if (matrix[i - 1][j] != null && matrix[i - 1][j].getDirection() == 12) {
					s += " A ";
				} else {
					s += empty;
				}
			}
			// middle columns
			if (i > 0 && i < matrix.length - 1) {
				if (matrix[i][j] != null) {
					s += matrix[i][j].toString();
				} else if (matrix[i + 1][j] != null && matrix[i + 1][j].getDirection() == 6) {
					s += " V ";
				} else if (matrix[i - 1][j] != null && matrix[i - 1][j].getDirection() == 12) {
					s += " A ";
				} else {
					s += empty;
				}
			}
			// bottom column
			if (i == 0) {
				if (matrix[i][j] != null) {
					s += matrix[i][j].toString();
				} else if (matrix[i + 1][j] != null && matrix[i + 1][j].getDirection() == 6) {
					s += " V ";
				} else {
					s += empty;
				}
			}
		}
		System.out.println(s);
		// print through recursion from top to bottom
		if (i != 0) {
			print(--i);
		}
	}
}
