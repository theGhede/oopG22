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
	
	void moveUp(double k) throws InterruptedException {
		for (int i = 0; i < (k * this.modifier); i++) {
			TimeUnit.MICROSECONDS.sleep(200);
			double move = this.ycoord + 1;
			this.ycoord = move;
		}
	}

	void moveDown(double k) throws InterruptedException {
		for (int i = 0; i < (k * this.modifier); i++) {
			TimeUnit.MICROSECONDS.sleep(200);
			double move = this.ycoord - 1;
			this.ycoord = move;
		}
	}

	void moveLeft(double k) throws InterruptedException {
		for (int i = 0; i < (k * this.modifier); i++) {
			TimeUnit.MICROSECONDS.sleep(200);
			double move = this.xcoord - 1;
			this.xcoord = move;
		}
	}

	void moveRight(double k) throws InterruptedException {
		for (int i = 0; i < (k * this.modifier); i++) {
			TimeUnit.MICROSECONDS.sleep(200);
			double move = this.xcoord + 1;
			this.xcoord = move;
		}
	}
	
	// quicker methods for keeping minimal distance while making swarms
	// options: some amount of almost duplicate code or slower swarm generation or an additional parameter
	void quickUp(double k) {
		double move = this.ycoord + k;
		this.ycoord = move;
	}

	void quickDown(double k) {
		double move = this.ycoord - k;
		this.ycoord = move;
	}

	void quickLeft(double k) {
		double move = this.xcoord - k;
		this.xcoord = move;
	}

	void quickRight(double k) {
		double move = this.xcoord + k;
		this.xcoord = move;
	}
}
