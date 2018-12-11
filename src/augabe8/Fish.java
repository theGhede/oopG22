package augabe8;

public class Fish extends Thread {

	private int direction;
	private int x;
	private int y;
	private boolean waiting;
	private int waitCount;
	private Swarm group;
	private Thread thread;
	private int threadNum = 0;

	public Fish(int dir, int x, int y) {
		this.x = x;
		this.y = y;
		this.direction = dir;
		this.waitCount = 0;
		this.waiting = false;
	}

	public int getDirection() {
		return this.direction;
	}

	public int getX() {
		return this.x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return this.y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public boolean isWaiting() {
		return waiting;
	}

	public void setWaiting(boolean waiting) {
		this.waiting = waiting;
	}

	public int getWaitCount() {
		return waitCount;
	}

	public void setWaitCount(int waitCount) {
		this.waitCount = waitCount;
	}

	public void setGroup(Swarm swarm) {
		this.group = swarm;
	}

	public synchronized int threadNumber() {
		return threadNum++;
	}

	public void turnLeft() {
		if (!this.waiting) {
			if (this.direction == 3) {
				this.direction = 12;
			} else
				this.direction -= 3;
		}
		try {
			while (this.waiting && this.repeat()) {
				this.waitCount++;
				int n = (int) (Math.random() * 45 + 5);
				Thread.sleep(n);
				this.waiting = this.group.collision(this);
				if (!this.waiting)
					this.turnLeft();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
			Thread.currentThread().interrupt();
		}
	}

	public void turnRight() {
		if (!this.waiting) {
			if (this.direction == 12) {
				this.direction = 3;
			} else
				this.direction += 3;
		}
		try {
			while (this.waiting && this.repeat()) {
				this.waitCount++;
				int n = (int) (Math.random() * 45 + 5);
				Thread.sleep(n);
				this.waiting = this.group.collision(this);
				if (!this.waiting)
					this.turnRight();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
			Thread.currentThread().interrupt();
		}
	}

	public void move() {
		if (!this.waiting) {
			this.changeFacing();
			switch (this.direction) {
			case 3:
				if (this.x < 22)
					this.x += 2;
				if (this.x == 22)
					this.x++;
				break;
			case 6:
				if (this.y < 22)
					this.y += 2;
				if (this.y == 22)
					this.y++;
				break;
			case 9:
				if (this.x >= 2)
					this.x -= 2;
				if (this.x == 1)
					this.x--;
				break;
			case 12:
				if (this.y >= 2)
					this.y -= 2;
				if (this.y == 1)
					this.y--;
				break;
			default:
				break;
			}
		}
		try {
			while (this.waiting && this.repeat()) {
				this.waitCount++;
				int n = (int) (Math.random() * 45 + 5);
				Thread.sleep(n);
				this.waiting = this.group.collision(this);
				if (!this.waiting)
					this.move();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
			Thread.currentThread().interrupt();
		}
	}

	public boolean repeat() {
		for (Fish fish : this.group.getSwarm()) {
			if (fish.waitCount >= 32)
				return false;
		}
		return true;
	}

	public void changeFacing() {
		double facing = Math.random();
		if (facing < 0.33) {
			if (this.direction == 3)
				this.group.topNeighbors(this);
			if (this.direction == 9)
				this.group.bottomNeighbors(this);
			this.turnLeft();
		}
		if (facing > 0.66) {
			if (this.direction == 9)
				this.group.topNeighbors(this);
			if (this.direction == 3)
				this.group.bottomNeighbors(this);
			this.turnRight();
		}
	}

	public synchronized void edgeFacing() {
		if ((this.y == 0 && this.direction == 12) || (this.y == 23 && this.direction == 6)) {
			double facing = Math.random();
			if (facing <= 0.5) {
				this.turnLeft();
			}
			if (facing > 0.5) {
				this.turnRight();
			}
		}
	}

	@Override
	public void start() {
		if (this.thread == null) {
			this.thread = new Thread(null, this, "Thread-" + this.threadNumber(), 1 << 14);
			this.thread.start();
		}
	}

	@Override
	public void run() {
		this.move();
	}

	@Override
	public String toString() {
		String s = "";
		switch (this.direction) {
		case 3:
			s += "X> ";
			break;
		case 9:
			s += "<X ";
			break;
		case 6:
		case 12:
			s += " X ";
			break;
		default:
			break;
		}
		return s;
	}
}
