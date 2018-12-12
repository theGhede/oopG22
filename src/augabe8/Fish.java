package augabe8;

public class Fish extends Thread {

	private int direction;
	private int x;
	private int y;
	private int moveCount;

	private boolean waiting;
	private int waitCount;

	private Swarm group;
	private SynchroGroup sg;

	private Thread thread;
	private int threadNum = 0;

	// NOTE:
	// makeSwarm takes care of calling this with direction = (3,6,9,12) and x/y
	// within the appropriate limits (0, matrix.length-1)
	public Fish(int dir, int x, int y) {
		this.direction = dir;
		this.x = x;
		this.y = y;
		this.moveCount = 0;
		this.waiting = false;
		this.waitCount = 0;
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

	public void setGroup(Swarm swarm) {
		this.group = swarm;
	}

	public void setSg(SynchroGroup sg) {
		this.sg = sg;
	}

	public synchronized int threadNumber() {
		return threadNum++;
	}

	public void turnLeft() throws InterruptedException {
		this.waiting = this.group.collision(this, 1);
		if (!this.waiting) {
			if (this.direction == 3) {
				this.direction = 12;
			} else
				this.direction -= 3;
		}
		while (this.waiting && this.repeat()) {
			this.waitCount++;
			int n = (int) (Math.random() * 45 + 5);
			Thread.sleep(n);
			this.turnLeft();
		}
	}

	public void turnRight() throws InterruptedException {
		this.waiting = this.group.collision(this, 2);
		if (!this.waiting) {
			if (this.direction == 12) {
				this.direction = 3;
			} else
				this.direction += 3;
		}
		while (this.waiting && this.repeat()) {
			this.waitCount++;
			int n = (int) (Math.random() * 45 + 5);
			Thread.sleep(n);
			this.turnRight();
		}
	}

	public void move() throws InterruptedException {
		this.changeFacing();
		/*
		 * NOTE: fish at the edges facing the outside were trying to move there hundreds
		 * of times, this automatically sets waiting to true and changes their facing
		 * away from the edge if a fish tries to leave the swarm in order to counteract
		 * StackOverflowExceptions
		 */
		switch (this.direction) {
		case 3:
			if (this.x == this.group.useMatrixLength()) {
				this.waiting = true;
				if (this.y == 0)
					this.direction = 12;
				else if (this.y == this.group.useMatrixLength())
					this.direction = 6;
				else
					this.edgeFacing();
			}
			break;
		case 6:
			if (this.y == 0) {
				this.waiting = true;
				if (this.x == 0)
					this.direction = 3;
				else if (this.x == this.group.useMatrixLength())
					this.direction = 9;
				else
					this.edgeFacing();
			}
			break;
		case 9:
			if (this.x == 0) {
				this.waiting = true;
				if (this.y == 0)
					this.direction = 12;
				else if (this.y == this.group.useMatrixLength())
					this.direction = 6;
				else
					this.edgeFacing();
			}
			break;
		case 12:
			if (this.y == this.group.useMatrixLength()) {
				this.waiting = true;
				if (this.x == 0)
					this.direction = 3;
				else if (this.x == this.group.useMatrixLength())
					this.direction = 9;
				else
					this.edgeFacing();
			}
			break;

		default:
			break;
		}
		if (!this.waiting) {
			switch (this.direction) {
			case 3:
				if (this.x < this.group.useMatrixLength() - 1)
					this.x += 2;
				break;
			case 12:
				if (this.y < this.group.useMatrixLength() - 1)
					this.y += 2;
				break;
			case 9:
				if (this.x >= 2)
					this.x -= 2;
				break;
			case 6:
				if (this.y >= 2)
					this.y -= 2;
				break;
			default:
				break;
			}
			this.moveCount++;
			this.move();
		}
		while (this.waiting && this.repeat()) {
			this.waitCount++;
			int n = (int) (Math.random() * 45 + 5);
			Thread.sleep(n);
			this.move();
		}
	}

	public boolean repeat() {
		for (Fish fish : this.group.getSwarm()) {
			if (fish.waitCount >= 32)
				return false;
		}
		return true;
	}

	public void changeFacing() throws InterruptedException {
		double facing = Math.random();
		if (facing < 0.33) {
			this.turnLeft();
		} else if (facing > 0.66) {
			this.turnRight();
		} else {
			this.waiting = this.group.collision(this, 0);
		}
	}

	// simple method to correct direction for better display after movement is done,
	// right before output is generated & before movement start
	public synchronized void edgeFacing() {
		double facing = Math.random();
		if (facing <= 0.5) {
			this.direction = 9;
		}
		if (facing > 0.5) {
			this.direction = 3;
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
		try {
			this.move();
		} catch (InterruptedException e) {
			e.printStackTrace();
			this.details();
			Thread.currentThread().interrupt();
		}
		/*
		 * the thread hitting waitCount of 32 (= 32 collision checks returning true
		 * after the 5-50 ms timer completes) calls print
		 */
		if (this.waitCount == 32) {
			synchronized (this.sg) {
				this.group.print();
			}
		}
	}

	public void details() {
		if (this.isChosen())
			System.out.println("        [" + this.getName() + " can be found at coordinates (" + this.x + "/" + this.y
					+ ")," + " waited " + this.waitCount + " times, moved " + this.moveCount
					+ " times and this thread has been chosen]");
		else
			System.out.println("        [" + this.getName() + " can be found at coordinates (" + this.x + "/" + this.y
					+ ")," + " waited " + this.waitCount + " times, moved " + this.moveCount + " times] ");

	}

	/*
	 * NOTE:
	 * 
	 * Method to choose 1 thread per swarm to become the chosen thread as stated by
	 * the assignment. Printing the entirety (String.length = SIZE * 3) every time
	 * it waits (up to 32 times) for all swarms will generate a massive amount of
	 * println in the console. It would take little effort to call print every time
	 * the chosen threads waitCount increments just the way it is called at the end
	 * of the run (but for the chosen threads swarm only of course)
	 */
	private int chosen;

	public void setChosen(int chosen) {
		this.chosen = chosen;
	}

	public boolean isChosen() {
		String c = "Thread-" + this.chosen;
		if (this.getName().equals(c))
			return true;
		return false;
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
