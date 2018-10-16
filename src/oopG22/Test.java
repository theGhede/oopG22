package oopG22;
import java.awt.*;
import javax.swing.*;

public class Test extends JPanel {

	// Vögel bestehen aus Koordinaten & einer Nachbarschaft; jeder Vogel hat für einfacheren Zugriff einen ID als index gespeichert
	private class Bird {

		double xcoord;
		double ycoord;
		Bird[] neighbors;
		int index;
		
		// Aufbau der Nachbarschaft eines Vogels mittels "Sonar" bis (5-20) Nachbarn gefunden wurden
		private Bird[] neighborhood(Bird b, int radius) {

			int j = 0;
			int amount = (int) (5 + (15 * Math.random()));
			Bird neighbors[] = new Bird[amount-1];
			for (int i = 0; i < flock.length; i++) {
				if (i != b.index && j < amount) {
					if (distance(b, flock[i]) <= radius) {
						neighbors[j] = flock[i];
						j++;
					}
				}
			}
			if (j < amount) neighborhood(b, radius + 1);
			// TODO: dieses neighbors soll außerdem als Bird.neighbors gespeichert werden
			return neighbors;
		}

		void moveUp(double k) {
			double move = this.ycoord + k;
			this.ycoord = move;
		}

		void moveDown(double k) {
			double move = this.ycoord - k;
			this.ycoord = move;
		}

		void moveLeft(double k) {
			double move = this.xcoord - k;
			this.xcoord = move;
		}

		void moveRight(double k) {
			double move = this.xcoord + k;
			this.xcoord = move;
		}

		// Nachbarschaftsbewegung, moveUp = y1, moveDown = y2, moveRight = x1, moveLeft = x2
		// Im Flug können sich die Vögel stärker annähern als die Mindestdistanz
		// TODO: Point movement
		void moveBird (Bird b, double x1,double x2, double y1, double y2) {

			if (!moved[b.index]){
				moveUp(y1);
				moveDown(y2);
				moveLeft(x2);
				moveRight(x1);
			}
			for (int i = 0; i < b.neighbors.length ; i++) {
				moveBird(b.neighbors[i],x1,x2,y1,y2);
			}

		}
	}

	// Der Schwarm wird von einem Array von Vögeln repräsentiert
	// Die Anzahl der Vögel ist willkürlich vorbestimmt
	public Bird[] flock = new Bird[199];
	public boolean[] moved = new boolean[199];

	// Mindestabstand - willkürlich definiert
	public double minDistance = 4;
	
	// Behandlung von Mindestabstandsverletzungen - die obigen move Methoden konnten nicht wiederverwertet werden
	// Die Magnitüde & Richtung der Bewegung richtet sich nach helpDistance und geschieht entlang der Geraden zwischen Punkt i & j
	// TODO: Pointmovement
	public void testDistance () {
		double[] distances = new double[flock.length];
		for(int i = 0; i < flock.length; i++) {
			for(int j = 0; j < flock.length; j++) {
				distances[i] = distance(flock[i], flock[j]);
				if(distances[i] < minDistance) {
					double helpDistance = minDistance - distances[i];
					double yDistance = flock[i].xcoord - flock[j].xcoord;
					double xDistance = flock[i].ycoord - flock[j].ycoord;

					if(flock[j].ycoord < flock[i].ycoord && flock[j].xcoord < flock[i].xcoord){
						double angle = Math.atan(xDistance/yDistance);
						double xMove = helpDistance * Math.sin(angle);
						double yMove = xMove/Math.tan(angle);

						flock[j].moveUp((yMove));
						flock[j].moveRight((xMove));

					}
					else if(flock[j].ycoord > flock[i].ycoord && flock[j].xcoord > flock[i].xcoord){
						double angle = Math.atan(yDistance/xDistance);
						double xMove = helpDistance * Math.sin(angle);
						double yMove = xMove/Math.tan(angle);

						flock[j].moveUp((yMove));
						flock[j].moveRight((xMove));
					}
					else if(flock[j].ycoord < flock[i].ycoord && flock[j].xcoord > flock[i].xcoord) {
						double angle = Math.atan(xDistance/yDistance);
						double xMove = helpDistance * Math.sin(angle);
						double yMove = xMove/Math.tan(angle);

						flock[j].moveUp((yMove));
						flock[j].moveRight((xMove));

					}
					else if(flock[j].ycoord > flock[i].ycoord && flock[j].xcoord < flock[i].xcoord) {
						double angle = Math.atan(yDistance/xDistance);
						double xMove = helpDistance * Math.sin(angle);
						double yMove = xMove/Math.tan(angle);

						flock[j].moveUp((yMove));
						flock[j].moveRight((xMove));

					}
					// TODO: CHECK Vogel wird nach links bewegt, falls er sich rechts von dem Anderen befindet, sonst nach links
					else if(flock[j].ycoord == flock[i].ycoord) {
						if(flock[j].xcoord < flock[i].xcoord) {
							flock[j].moveRight(helpDistance);
						}
						else {
							flock[j].moveLeft(helpDistance);
						}
					}
					// Vogel wird nach unten bewegt, falls er sich unter dem Anderen befindet, sonst nach oben
					else if(flock[j].xcoord == flock[i].xcoord) {
						if(flock[j].ycoord < flock[i].ycoord) {
							flock[j].moveDown(helpDistance);
						}
						else {
							flock[j].moveUp(helpDistance);
						}
					}

				}
			}
		}
	}

	// Berechnen der euklidischen Distanz
	public double distance (Bird a, Bird b) {
		double dist;
		double xdist = Math.pow((a.xcoord - b.xcoord), 2);
		double ydist = Math.pow((a.ycoord - b.ycoord), 2);
		return dist = Math.sqrt(xdist + ydist);
	}
	
	public void paint(Graphics g) {
		// TODO: use this to draw the initial flock via for loop as dots
		// Polygon as example for drawing
		int xValues[] = {25, 145, 25, 145, 25};
	    int yValues[] = {25, 25, 145, 145, 25};
	    int points = 5;	    
	    g.drawPolygon(xValues, yValues, points);
	}
	
	private static void GUI() {
		// TODO: Draw points

		JFrame frame = new JFrame("oopG22 Aufgabe 1 - Vogelschwarm");
		frame.getContentPane().add(new Test());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800, 800);
		frame.setLocationByPlatform(true);
		frame.setVisible(true);
	}
	
	public static void main(String[] args) {

		Bird[] flock = new Bird[200];
		
		// TODO: Make flock
		
		// TODO: CHECK run GUI
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				GUI();
			}
		});
	}
}
				
/* TODO:  Beschreibung wer an welchem Teil mitgearbeitet hat, entsprechend der Angabe */