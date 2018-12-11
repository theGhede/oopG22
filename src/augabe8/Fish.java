package augabe8;

public class Fish {

	// TODO: fish as threads (16k max.)

	private int direction;
	private int x;
	private int y;
	private boolean waiting;
	private int waitCount;

	public Fish(int dir, int x, int y) {
		this.x = x;
		this.y = y;
		this.direction = dir;
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

	public void turnLeft() {
		if (!waiting) {
			if (this.direction == 3) {
				this.direction = 12;
			} else
				this.direction -= 3;
		}
		if (waiting) {
			this.waitCount++;
			// waiting
			// TODO: collision check
			this.waiting = false;
			this.turnLeft();
		}
	}

	public void turnRight() {
		if (!waiting) {
			if (this.direction == 12) {
				this.direction = 3;
			} else
				this.direction += 3;
		}
		if (waiting) {
			this.waitCount++;
			// waiting
			// TODO: collision check
			this.waiting = false;
			this.turnRight();
		}
	}

	public void move() {
		if (!waiting) {
			switch (this.direction) {
			case 3:
				if (this.x < 22)
					this.x += 2;
				if (this.x == 22)
					this.x++;
				break;
			case 6:
				if (this.y >= 2)
					this.y -= 2;
				if (this.y == 1)
					this.y--;
				break;
			case 9:
				if (this.x >= 2)
					this.x -= 2;
				if (this.x == 1)
					this.x--;
				break;
			case 12:
				if (this.y < 22)
					this.y += 2;
				if (this.y == 22)
					this.y++;
				break;
			default:
				break;
			}
		}
		if (waiting) {
			this.waitCount++;
			// waiting
			// TODO: collision check
			this.waiting = false;
			this.move();
		}
	}

	public String toString() {
		String s = "";
		switch (this.direction) {
		case 3:
			s += "X>";
			break;
		case 9:
			s += "<X";
			break;
		case 6:
		case 12:
			s += " X";
			break;
		default:
			break;
		}
		return s += " ";
	}
}
