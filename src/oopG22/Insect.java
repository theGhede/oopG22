package oopG22;

// insects which can crawl over eachother and are tireless but react to danger if we want them to
public class Insect extends Animal {
	boolean follower;

	public void lane(double left, double y) throws InterruptedException {
		moveLeft(left);
		if (y > 0)
			moveDown(y);
		if (y < 0)
			moveUp(y);
	}
}
