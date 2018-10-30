package oopG22;

import java.util.concurrent.TimeUnit;

// insects which can crawl over eachother and are tireless but react to danger if we want them to
public class Insect extends Animal {
	
	boolean follower;

	public void lane(double x, double y) throws InterruptedException {
		TimeUnit.MICROSECONDS.sleep(20);
		this.quickRight(x);
		if (y > 0) {
			this.quickDown(y);
		}
		if (y < 0) {
			this.quickUp(Math.abs(y));
		}
	}
}
