package augabe8;

import java.util.ArrayList;
import java.util.List;

public class Swarm {

	private List<Fish> swarm;
	private Fish[][] matrix;

	public Swarm() {
		this.swarm = new ArrayList<>();
		this.matrix = new Fish[24][24];
		this.makeSwarm();
	}

	public List<Fish> getSwarm() {
		return this.swarm;
	}

	public void convert() {
		for (Fish fish : swarm) {
			fish.getX();
			fish.getY();
			this.matrix[fish.getY()][fish.getX()] = fish;
		}
	}

	public void makeSwarm() {
		for (int i = 0; i < 144; i++) {
			int x = (int) (Math.random() * 24 - 1);
			int y = (int) (Math.random() * 24 - 1);
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
			swarm.add(fish);
			fish.setGroup(this);
		}
		this.newSwarmCollision();
	}

	public void newSwarmCollision() {
		for (Fish fish : swarm) {
			for (Fish other : swarm) {
				if (!fish.equals(other) && fish.getX() == other.getX() && fish.getY() == other.getY()) {
					other.setX((int) (Math.random() * 24 - 1));
					other.setY((int) (Math.random() * 24 - 1));
				}
			}
			double turn = Math.random();
			if (fish.getDirection() == 12 && this.above(fish)) {
				if (turn <= 0.5) {
					fish.turnLeft();
				} else
					fish.turnRight();
			}
			if (fish.getDirection() == 6 && this.below(fish)) {
				if (turn <= 0.5) {
					fish.turnLeft();
				} else
					fish.turnRight();
			}
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
			if (fish.getX() == f.getX() && (fish.getY() == f.getY() - 1)) {
				return true;
			}
		}
		return false;
	}

	public boolean below(Fish fish) {
		for (Fish f : swarm) {
			if (fish.getX() == f.getX() && (fish.getY() == f.getY() + 1)) {
				return true;
			}
		}
		return false;
	}

	public void startSwarm() {
		for (Fish fish : swarm) {
			fish.start();
		}
	}

	public boolean collision(Fish fish) {
		if (fish.getDirection() == 12)
			return this.topNeighbors(fish);
		if (fish.getDirection() == 6)
			return this.bottomNeighbors(fish);
		if (fish.getDirection() == 3)
			return this.rightNeighbors(fish);
		if (fish.getDirection() == 9)
			return this.leftNeighbors(fish);

		return false;
	}

	public boolean leftNeighbors(Fish fish) {
		if (fish.getX() == 0)
			return false;
		for (Fish f : swarm) {
			if (fish.getY() == f.getY() && (fish.getX() == f.getX() - 1 || fish.getX() == f.getX() - 2)) {
				fish.setWaiting(true);
				return true;
			}
		}
		fish.setWaiting(false);
		return false;
	}

	public boolean rightNeighbors(Fish fish) {
		if (fish.getX() == 23)
			return false;
		for (Fish f : swarm) {
			if (fish.getY() == f.getY() && (fish.getX() == f.getX() + 1 || fish.getX() == f.getX() + 2)) {
				fish.setWaiting(true);
				return true;
			}
		}
		fish.setWaiting(false);
		return false;
	}

	public boolean topNeighbors(Fish fish) {
		if (fish.getY() == 23)
			return false;
		for (Fish f : swarm) {
			if ((fish.getX() == f.getX() && (fish.getY() == f.getY() + 1) || fish.getY() == f.getY() + 2)) {
				fish.setWaiting(true);
				return true;
			}
		}
		fish.setWaiting(false);
		return false;
	}

	public boolean bottomNeighbors(Fish fish) {
		if (fish.getY() == 0)
			return false;
		for (Fish f : swarm) {
			if ((fish.getX() == f.getX() && (fish.getY() == f.getY() - 1) || fish.getY() == f.getY() - 2)) {
				fish.setWaiting(true);
				return true;
			}
		}
		fish.setWaiting(false);
		return false;
	}

	public void print(int i) {
		for (Fish fish : swarm) {
			fish.edgeFacing();
		}
		this.convert();
		String s = "";
		String empty = "   ";
		for (int j = 0; j < matrix[i].length; j++) {
			if (i == 0) {
				if (matrix[i + 1][j] != null && matrix[i + 1][j].getDirection() == 12) {
					s += " A ";
				} else if ((matrix[i][j] == null && matrix[i + 1][j] == null) || (matrix[i][j] == null
						&& matrix[i + 1][j] != null && matrix[i + 1][j].getDirection() != 12)) {
					s += empty;
				} else if (matrix[i][j] != null) {
					s += matrix[i][j].toString();
				}
			}
			if (i > 0 && i < matrix.length - 1) {
				if (matrix[i - 1][j] != null && matrix[i - 1][j].getDirection() == 6) {
					s += " V ";
				} else if (matrix[i + 1][j] != null && matrix[i + 1][j].getDirection() == 12) {
					s += " A ";
				} else if ((matrix[i][j] == null && matrix[i + 1][j] == null && matrix[i - 1][j] == null)
						|| (matrix[i][j] == null && (matrix[i - 1][j] != null && matrix[i - 1][j].getDirection() != 6)
								|| (matrix[i + 1][j] != null && matrix[i + 1][j].getDirection() != 12))) {
					s += empty;
				} else if (matrix[i][j] != null) {
					s += matrix[i][j].toString();
				}
			}
			if (i == matrix.length - 1) {
				if (matrix[i - 1][j] != null && matrix[i - 1][j].getDirection() == 6) {
					s += " V ";
				} else if ((matrix[i][j] == null && matrix[i - 1][j] == null)
						|| (matrix[i][j] == null && matrix[i - 1][j] != null && matrix[i - 1][j].getDirection() != 6)) {
					s += empty;
				} else if (matrix[i][j] != null) {
					s += matrix[i][j].toString();
				}
			}
		}
		System.out.println(s);
		s = "";
		if (i < matrix.length - 1) {
			print(++i);
		}
	}
}
