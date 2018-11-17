package aufgabe3;

import java.util.concurrent.TimeUnit;
/* GUT: Unterklassen führen oft zu stärkerem Klassenzusammenhalt und Übersichtlichkeit. Mit Unterklassen unterscheiden
 * wir verschiede Sorten der Super Klasse, eine Insect Colony unterscheidet sich von ein Bird Flock. Das könnte einfacher
 * gemacht werden, ohne Unterklassen, aber dann würde der Klassenzusammenhang würde darunter leiden.
*/
public class Insect extends Animal {
	
	private boolean follower;

	public boolean isFollower() {
		return follower;
	}

	public void setFollower(boolean follower) {
		this.follower = follower;
	}

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
