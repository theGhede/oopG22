package oopG22;

import java.util.concurrent.TimeUnit;

// Tiere bestehen aus Koordinaten & einer Nachbarschaft; jeder Vogel hat für einfacheren Zugriff einen ID als index gespeichert
public class Animal {

	double xcoord;
	double ycoord;
	Animal[] neighbors;
	int index;
	// this lets us manipulate the length of an animals movement
	double modifier;

	boolean existing = false;
	// TODO: issue: movement currently cannot be diagonal & this is very apparent with animations in place
	// TODO: fix existing
	void moveUp(double k) throws InterruptedException {
		if(existing) {
			for (int i = 0; i < (k * this.modifier); i++) {
				TimeUnit.MICROSECONDS.sleep(500);
				double move = this.ycoord + 1;
				this.ycoord = move;
			}
		} else {
			double move = this.ycoord + k;
			this.ycoord = move;
			}
	}

	void moveDown(double k) throws InterruptedException {
		if(existing) {
			for (int i = 0; i < (k * this.modifier); i++) {
				TimeUnit.MICROSECONDS.sleep(500);
				double move = this.ycoord - 1;
				this.ycoord = move;
			}
		} else {
			double move = this.ycoord - k;
			this.ycoord = move;
		}
	}

	void moveLeft(double k) throws InterruptedException {
		if(existing) {
			for (int i = 0; i < (k * this.modifier); i++) {
				TimeUnit.MICROSECONDS.sleep(500);
				double move = this.xcoord - 1;
				this.xcoord = move;
			}
		} else {
			double move = this.xcoord - k;
			this.xcoord = move;
		}
	}

	void moveRight(double k) throws InterruptedException {
		if(existing) {
			for (int i = 0; i < (k * this.modifier); i++) {
				TimeUnit.MICROSECONDS.sleep(500);
				double move = this.xcoord + 1;
				this.xcoord = move;
			}
		} else {
			double move = this.xcoord + k;
			this.xcoord = move;
		}
	}
}
