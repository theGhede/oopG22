package oopG22;

import java.util.concurrent.TimeUnit;
/* GOOD: Unterklassen darstellen eine hohe Klassenzusammenhalt. Mit Unterklassen unterscheiden wir verschiede Sorten
 Super Klassen, eine Insekt Colony unterscheidet sich von ein Bird Flock. Das könnte einfacher gemacht werden, ohne
 Unterklassen, aber dann würde das Program weniger Details haben & der Klassenzusammenhang würde stark darunter leiden.
*/
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
