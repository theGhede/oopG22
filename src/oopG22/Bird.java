package oopG22;

// regular Animal including new behavior for tiring and stress
public class Bird extends Animal {
	boolean stressed;
	boolean tired;
	
	// method to be periodically called while moving away from danger to update 'modifier' depending on distance to dangerzone
	public void danger(double xsource, double ysource) {
		// distance needs to be redone for this because the original method's parameters don't apply
		double xdist = Math.pow((this.xcoord - xsource), 2);
		double ydist = Math.pow((this.ycoord - ysource), 2);
		double dist = Math.sqrt(xdist + ydist);
		// make rules for modifiers if stressed and close enough to the danger
		if (dist > 300 && this.stressed) {
			this.modifier = 1;
			this.tired = false;
		}
		if (200 < dist && dist <= 300 && this.stressed) {
			this.modifier = 1.2;
			this.tired = false;
		}
		if (100 < dist && dist <= 200 && this.stressed) {
			this.modifier = 1.4;
			this.tired = true;
		}
		if (dist <= 100 && this.stressed) {
			this.modifier = 1.6;
			this.tired = true;
		}
	}
}
