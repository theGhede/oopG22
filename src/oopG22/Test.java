package oopG22;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.concurrent.TimeUnit;

import javax.swing.*;

public class Test extends JPanel {

	// Vögel bestehen aus Koordinaten & einer Nachbarschaft; jeder Vogel hat für einfacheren Zugriff einen ID als index gespeichert
	private static class Bird {

		double xcoord;
		double ycoord;
		Bird[] neighbors;
		int index;

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
	}

	// Aufbau der Nachbarschaft eines Vogels mittels "Sonar" bis (5-20) Nachbarn gefunden wurden
	public static void neighborhood(Bird b, int radius) {
		int j = 0;
		int amount = (int) (5 + (15 * Math.random()));
		Bird[] neighbors = new Bird[amount];
		for (int i = 0; i < flock.length; i++) {
			if (i != b.index && j < amount) {
				if (distance(b, flock[i]) <= radius) {
					neighbors[j] = flock[i];
					j++;
				}
			}
		}
		if (j < amount) {
			neighborhood(b, radius + 1);
		} else {
			b.neighbors = neighbors;
		}
	}


	// Nachbarschaftsbewegung, moveUp = y1, moveDown = y2, moveRight = x1, moveLeft = x2
	// Im Flug können sich die Vögel stärker annähern als die Mindestdistanz
	// TODO: Point movement
	public static void moveBird (Bird b, double x1,double x2, double y1, double y2) {
		if (!moved[b.index]){
			b.moveUp(y1);
			b.moveDown(y2);
			b.moveLeft(x2);
			b.moveRight(x1);
			moved[b.index] = true;
		}
		for (int i = 0; i < b.neighbors.length ; i++) {
			if (!moved[b.neighbors[i].index]) {
				moveBird(b.neighbors[i],x1,x2,y1,y2);}
		}
	}

	// Der Schwarm wird von einem Array von Vögeln repräsentiert
	// Die Anzahl der Vögel ist willkürlich vorbestimmt
	public static int flocksize = 200;
	public static Bird[] flock = new Bird[flocksize];
	public static boolean[] moved = new boolean[flock.length];

	// Mindestabstand - willkürlich definiert
	public static double minDistance = 60;

	// Behandlung von Mindestabstandsverletzungen
	// Die Magnitüde & Richtung der Bewegung richtet sich nach helpDistance und geschieht entlang der Geraden zwischen Punkt i & j
	// TODO: Point movement
	public static void testDistance () {
		for(int i = 0; i < flock.length; i++) {
			for(int j = 0; j < flock.length; j++) {
				if(distance(flock[i], flock[j]) < minDistance) {
					double helpDistance = minDistance - distance(flock[i], flock[j]);
					double yDistance = flock[i].xcoord - flock[j].xcoord;
					double xDistance = flock[i].ycoord - flock[j].ycoord;

					if(flock[j].ycoord < flock[i].ycoord && flock[j].xcoord < flock[i].xcoord){
						double angle = Math.atan(xDistance/yDistance);
						double xMove = helpDistance * Math.sin(angle);
						double yMove = xMove/Math.tan(angle);

						flock[j].moveDown((yMove));
						flock[j].moveLeft((xMove));

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

						flock[j].moveDown((yMove));
						flock[j].moveRight((xMove));

					}
					else if(flock[j].ycoord > flock[i].ycoord && flock[j].xcoord < flock[i].xcoord) {
						double angle = Math.atan(yDistance/xDistance);
						double xMove = helpDistance * Math.sin(angle);
						double yMove = xMove/Math.tan(angle);

						flock[j].moveUp((yMove));
						flock[j].moveLeft((xMove));

					}
					// Vogel wird nach links bewegt, falls er sich rechts von dem Anderen befindet, sonst nach rechts
					else if(flock[j].ycoord == flock[i].ycoord) {
						if(flock[j].xcoord < flock[i].xcoord) {
							flock[j].moveLeft(helpDistance);
						}
						else {
							flock[j].moveRight(helpDistance);
						}
					}
					// Vogel wird nach unten bewegt, falls er sich unter dem Anderen befindet, sonst nach oben
					else if(flock[j].xcoord == flock[i].xcoord) {
						if(flock[j].ycoord < flock[i].ycoord) {
							flock[j].moveDown(helpDistance);
						} else {
							flock[j].moveUp(helpDistance);
						}
					}
				}
			}
		}
	}

	// Berechnen der euklidischen Distanz
	public static double distance (Bird a, Bird b) {
		double xdist = Math.pow((a.xcoord - b.xcoord), 2);
		double ydist = Math.pow((a.ycoord - b.ycoord), 2);
		double dist = Math.sqrt(xdist + ydist);
		//System.out.println(dist);
		return dist;
	}

	// Make flock within the center 500x500 of the JFrame; reminder: top-right = (0,0)
	public static void makeFlock() {
		double[] xvalues = new double[flock.length];
		double[] yvalues = new double[flock.length];
		for(int i = 0; i < flock.length; i++) {
			xvalues[i] = (Math.random() * 500) + 150;
			yvalues[i] = (Math.random() * 500) + 150;
		}
		for(int i = 0; i < flock.length; i++) {
			// make new Bird Objects here
			Bird b = new Bird();
			b.xcoord = xvalues[i];
			b.ycoord = yvalues[i];
			b.index = i;
			flock[i] = b;
		}
		// Find neighbors for each bird
		for (int i = 0; i < flock.length; i++) {
			neighborhood(flock[i], 1);
		}

		// check and repair minimal distance infringments
		for (int i = 0; i < 1; i++) {
		testDistance();
		}
	}

	// draw graphics using paint(g) with Graphics2D for double usage
	public void paintComponent(Graphics g) {
		// use this to draw the initial flock via for loop as dots
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		for (int i = 0; i < flock.length; i++) {
			double x = flock[i].xcoord;
			double y = flock[i].ycoord;
			Shape s = new Ellipse2D.Double(x, y, 4, 4);
			g2d.draw(s);
			g2d.fill(s);
		}
	}

	private static JFrame GUI() {

		JFrame frame = new JFrame("oopG22 Aufgabe 1 - Vogelschwarm 1");
		frame.getContentPane().add(new Test());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800, 800);
		frame.setLocationByPlatform(true);
		frame.setVisible(true);
		return frame;
	}	
	
	public static void main(String[] args) {

		makeFlock();

		// moved array
		for (int i = 0; i < flock.length; i++) {
			moved[i] = false;
		}

		// Run GUI
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				GUI();
			}
		});

		// TODO: Simulations - each step starts after predetermined time (in seconds)
		// Nachbarschaftsbewegung, moveUp = y1, moveDown = y2, moveRight = x1, moveLeft = x2
		int select = (int) (Math.random() * flock.length-1);
		TimeUnit.SECONDS.sleep(20);
		moveBird(flock[select], (40 + Math.random() * 80), 0, 0, (60 + Math.random() * 60));
		TimeUnit.SECONDS.sleep(10);
		testDistance();
		
		JFrame frame = GUI();
		frame.repaint();
		
		// reset moved
		for (int i = 0; i < flock.length; i++) {
			moved[i] = false;
		}
		
		TimeUnit.SECONDS.sleep(20);
		minDistance = 80;
		flocksize = 260;
		
		makeFlock();
		
		select = (int) (Math.random() * flock.length-1);
		TimeUnit.SECONDS.sleep(20);
		moveBird(flock[select], (80 + Math.random() * 40), 0, (100 + Math.random() * 20), 0);
		TimeUnit.SECONDS.sleep(10);
		testDistance();
		
		// reset moved
		for (int i = 0; i < flock.length; i++) {
			moved[i] = false;
		}

		TimeUnit.SECONDS.sleep(20);
		minDistance = 40;
		flocksize = 400;
		
		makeFlock();
		
		select = (int) (Math.random() * flock.length-1);
		TimeUnit.SECONDS.sleep(20);
		moveBird(flock[select], 0, (60 + Math.random() * 80), 0, 0);
		TimeUnit.SECONDS.sleep(10);
		testDistance();
	}
}

/* TODO:  Beschreibung wer an welchem Teil mitgearbeitet hat, entsprechend der Angabe */