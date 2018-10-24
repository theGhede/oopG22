package oopG22;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.util.concurrent.TimeUnit;
import javax.swing.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/* TODO:
 * Der Flug ist durch die Physik und Physiologie eingeschränkt. Eine energiesparende Fortbewegung ist nur in bestimmten, 
 * engen Geschwindigkeitsbereichen möglich. Andere Geschwindigkeiten sowie Richtungsänderungen benötigen deutlich mehr Energie. 
 * Die für Anpassungen nötige Zeit hängt von der verfügbaren Kraft und Energie ab, aber auch von der Zeit für die Informationsverarbeitung 
 * im Gehirn. Nach erhöhten Anstrengungen möchte sich der Körper erholen (außer in Notfällen, etwa bei Angriffen), sodass Richtungsänderungen 
 * ohne Störung von außen nur in gewissen Zeitabständen erfolgen.

	Very doable. Feasible.
	stressed[boolean] for flight mode
	distance modifiers based on animal type &	flight mode
	make limits based on animal type & flight mode.
 *  ===========
	Unterschiedliche Vogelarten haben verschiedene weitere Bedürfnisse und Strategien. Bei Kranichen können wir z.B. annehmen, 
	dass sie gerne freie Sicht nach vorne hätten. Meist müssen vorausfliegende Vögel mehr Energie aufwenden als Vögel mitten im Schwarm. 
	Wahrscheinlich werden sich bei einigen Vogelarten ermüdete Vögel nach hinten fallen lassen und ausgeruhte Vögel die Führung übernehmen. 
	Die fittesten Stare drängen zur Mitte, während schwächere Vögel am Rand eher der Bedrohung durch Angreifer ausgesetzt sind.
	
	Weitere Eigenschaft für die Vogelobjekte: fitness[int]
	Aufgrund dessen kann bei moveSwarm das Tier zusätzlich verschoben werden.
	Dazu benötigen wir weiterhin eine Größe welche den Center, maxDistance von diesem Center des Schwarms bestimmt.
	Feasible, maybe doable (nicht in allen Belangen; aber generell als feature) & realistic.
	*/

public class Test extends JPanel implements ActionListener {
	
	// TODO: CRITICAL all methods must work with class Swarm instead of array swarm
	
	// Aufbau der Nachbarschaft eines Vogels mittels "Sonar" bis (5-20) Nachbarn gefunden wurden
	public static void neighborhood(Animal b, int radius) {
		int j = 0;
		int amount = (int) (5 + (15 * Math.random()));
		Animal[] neighbors = new Animal[amount];
		for (int i = 0; i < swarm.length; i++) {
			if (i != b.index && j < amount) {
				if (distance(b, swarm[i]) <= radius) {
					neighbors[j] = swarm[i];
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

	/* Nachbarschaftsbewegung, moveUp = y1, moveDown = y2, moveRight = x1, moveLeft = x2
	 * Im Flug wird überprüft ob der sich gerade fortbewegende Vogel zu nah an andere annähert
	 * Rekursiv fliegen alle Tiere in alle Nachbarschaften mit gleicher Entfernung in die gleiche Richtung
	 * Angestoßen wird die Bewegung durch einen einzelnes gewähltes Tier*/
	
	public static void moveAnimal (Animal b, double x1,double x2, double y1, double y2) throws InterruptedException {
		if (!moved[b.index]){
			b.moveUp(y1);
			b.moveDown(y2);
			b.moveRight(x1);
			b.moveLeft(x2);
			moved[b.index] = true;
			movingDistance(b);
			}
		if (b.neighbors != null) {
			for (int i = 0; i < b.neighbors.length ; i++) {
				if (!moved[b.neighbors[i].index]) {
					moveAnimal(b.neighbors[i],x1,x2,y1,y2);}
			}
		}
	}
	
	// Berechnen der euklidischen Distanz
	public static double distance (Animal a, Animal b) {
		double xdist = Math.pow((a.xcoord - b.xcoord), 2);
		double ydist = Math.pow((a.ycoord - b.ycoord), 2);
		double dist = Math.sqrt(xdist + ydist);
		return dist;
	}
	
	public static void movingDistance(Animal b) throws InterruptedException {
		if(minDistance != 0) {
			for (int i = 0; i < swarm.length; i++) {
				if (distance(swarm[b.index], swarm[i]) < minDistance) {
					testDistance();
				}
			}
		}
	}
	
	// checks if testDistance & moves defined there are needed - this takes a couple of seconds while the randomized swarm is being made
	public static void establishDistance () throws InterruptedException {
		if (minDistance != 0) {
			// cap improves runtime in cases where one node is cought on the line between two nodes
			int cap = swarmsize /5;
			for (int i = 0; i < swarm.length; i++) {
				for (int j = 0; j < swarm.length; j++) {
					if (distance(swarm[i], swarm[j]) > minDistance && cap != 0) {
						testDistance();
						cap--;
					}
				}
			}
		}
	}

	// angle etc in eigene Methode auslagern
	public static double[] distanceHelper (double helpDistance, double xDistance, double yDistance) {
		// [xMove, yMove]
		double[] res = new double[2];
		double angle = Math.atan(xDistance/yDistance);
		double xMove = helpDistance * Math.sin(angle);
		double yMove = xMove/Math.tan(angle);
		res[0] = xMove;
		res[1] = yMove;
		return res;
	}
	// Behandlung von Mindestabstandsverletzungen
	// Die Magnitüde & Richtung der Bewegung richtet sich nach helpDistance und geschieht entlang der Geraden zwischen Punkt i & j
	public static void testDistance () throws InterruptedException {
		for(int i = 0; i < swarm.length; i++) {
			for(int j = 0; j < swarm.length; j++) {
				if(distance(swarm[i], swarm[j]) < minDistance) {
					double helpDistance = minDistance - distance(swarm[i], swarm[j]);
					double xDistance = (swarm[i].ycoord - swarm[j].ycoord);
					double yDistance = (swarm[i].xcoord - swarm[j].xcoord);
					double xMove = distanceHelper(helpDistance, xDistance, yDistance)[0];
					double yMove = distanceHelper(helpDistance, xDistance, yDistance)[1];
					
					if(swarm[j].ycoord < swarm[i].ycoord && swarm[j].xcoord < swarm[i].xcoord){
						swarm[j].quickDown((yMove));
						swarm[j].quickLeft((xMove));
					}
					else if(swarm[j].ycoord > swarm[i].ycoord && swarm[j].xcoord > swarm[i].xcoord){
						swarm[j].quickUp((yMove));
						swarm[j].quickRight((xMove));
					}
					else if(swarm[j].ycoord < swarm[i].ycoord && swarm[j].xcoord > swarm[i].xcoord) {
						swarm[j].quickDown((yMove));
						swarm[j].quickRight((xMove));
					}
					else if(swarm[j].ycoord > swarm[i].ycoord && swarm[j].xcoord < swarm[i].xcoord) {
						swarm[j].quickUp((yMove));
						swarm[j].quickLeft((xMove));
					}
					// Vogel wird nach links bewegt, falls er sich rechts von dem Anderen befindet, sonst nach rechts
					else if(swarm[j].ycoord == swarm[i].ycoord) {
						if(swarm[j].xcoord < swarm[i].xcoord) {
							swarm[j].quickLeft(helpDistance);
						}
						else if(swarm[j].xcoord > swarm[i].xcoord){
							swarm[j].quickRight(helpDistance);
						}
					}
					// Vogel wird nach unten bewegt, falls er sich unter dem Anderen befindet, sonst nach oben
					else if(swarm[j].xcoord == swarm[i].xcoord) {
						if(swarm[j].ycoord < swarm[i].ycoord) {
							swarm[j].quickDown(helpDistance);
						} else if(swarm[j].ycoord < swarm[i].ycoord){
							swarm[j].quickUp(helpDistance);
						}
					}
				}
			}
		}
	}
	
	// TODO: transform to become a method of Swarm
	// Make swarm within the center 400x400 of the JFrame; reminder: top-right = (0,0)
	public static void makeswarm(String type) throws InterruptedException {
		double[] xvalues = new double[swarm.length];
		double[] yvalues = new double[swarm.length];
		for(int i = 0; i < swarm.length; i++) {
			xvalues[i] = (Math.random() * 400) + 200;
			yvalues[i] = (Math.random() * 400) + 200;
		}
		for(int i = 0; i < swarm.length; i++) {
			// TODO: remove this; only for testing
			Animal b = new Animal();
			b.xcoord = xvalues[i];
			b.ycoord = yvalues[i];
			b.index = i;
			swarm[i] = b;
			b.modifier = 1;
		}
		// make new Animal Objects here
		if (type == "Bird") {
			
		}
		else if (type == "Insect") {
			
		}
		else if (type == "LargeBird") {

		}
		// Find neighbors for each Animal
		for (int i = 0; i < swarm.length; i++) {
			neighborhood(swarm[i], 1);
		}
		// check and repair minimal distance infringements
		establishDistance();
		resetMoved();
	}

	// csv loader	
	public static void loadcsv() {

		String csvFilex = "path to x coords";
		String csvFiley = "path to y coords";
		String line = "";
		String line2 = "";
		String csvSplitBy = ",";
		String[] xcoords = null;
		String[] ycoords = null;


		try (BufferedReader br = new BufferedReader(new FileReader(csvFilex))) {
			while ((line = br.readLine()) != null) {
				xcoords = line.split(csvSplitBy);
				}
			}
	 	catch (IOException e) {
			e.printStackTrace();
		}

		try (BufferedReader br2 = new BufferedReader(new FileReader(csvFiley))) {
			while ((line2 = br2.readLine()) != null) {
				ycoords = line2.split(csvSplitBy);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		for(int i = 0; i < xcoords.length; i++) {
			Animal b = new Animal();
			b.xcoord = Double.valueOf(xcoords[i]);
			b.ycoord = Double.valueOf(ycoords[i]);
			b.index = i;
			swarm[i] = b;
			b.modifier = 1;
		}
		for (int i = 0; i < swarm.length; i++) {
			neighborhood(swarm[i], 1);
		}

	}

	// update by repaint every 4 milliseconds (==> after the intervals found in main)
	Timer t = new Timer(4, this);
	
	public static String typeToDraw;
	
	// these coordinates for the danger to our birds needs to be set before paintComponent can be called
	public static double dangerX = (450+Math.random()*100);
	public static double dangerY = (120+Math.random()*70);
	// draw graphics using paint(g) with Graphics2D for double variables
	// issue: can't pass other parameters ... like the Swarm or type etc
	public void paintComponent(Graphics g) {
		// use this to draw the initial swarm via for loop as dots
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		// TODO: different sizes for different animals; reminder: shape coordinates are the upper left point
		for (int i = 0; i < swarm.length; i++) {
			double x = swarm[i].xcoord;
			double y = swarm[i].ycoord;
            Shape s = new Ellipse2D.Double(x, y, 4, 4);

			if (i % 4 == 0){
                g2d.setPaint(Color.gray);
            } else {
                g2d.setPaint(Color.BLACK);
            }
            g2d.fill(s);
		}
		if (typeToDraw == "Animal") {
			for (int i = 0; i < swarm.length; i++) {
			double x = swarm[i].xcoord;
			double y = swarm[i].ycoord;
	           Shape s = new Ellipse2D.Double(x, y, 4, 4);

		 if (i % 4 == 0){
			 g2d.setPaint(Color.gray);
		 } else {
			 g2d.setPaint(Color.BLACK);
		 }
		 g2d.fill(s);
			}
		}
		if (typeToDraw == "Bird") {
			Shape d = new Ellipse2D.Double(dangerX, dangerY, 8, 8);
			g2d.setPaint(Color.red);
			g2d.fill(d);
			for (int i = 0; i < swarm.length; i++) {
				double x = swarm[i].xcoord;
				double y = swarm[i].ycoord;
	            Shape s = new Ellipse2D.Double(x, y, 4, 4);

				if (i % 4 == 0){
	                g2d.setPaint(Color.gray);
	            } else {
	                g2d.setPaint(Color.BLACK);
	            }
	            g2d.fill(s);
			}
		}
		if (typeToDraw == "Insect") {
			for (int i = 0; i < swarm.length; i++) {
				double x = swarm[i].xcoord;
				double y = swarm[i].ycoord;
	            Shape s = new Ellipse2D.Double(x, y, 2, 2);

				if (i % 3 == 0){
	                g2d.setPaint(Color.gray);
	            } else {
	                g2d.setPaint(Color.BLACK);
	            }
	            g2d.fill(s);
			}
		}
		if (typeToDraw == "LargeBird") {
			for (int i = 0; i < swarm.length; i++) {
				double x = swarm[i].xcoord;
				double y = swarm[i].ycoord;
	            Shape s = new Ellipse2D.Double(x, y, 8, 8);
                g2d.setPaint(Color.BLACK);
	            g2d.fill(s);
			}
		}
		// start Timer t
		t.start();
	}
	
	public void actionPerformed(ActionEvent e) {
		repaint();
	}

	private static void GUI() {
		JFrame frame = new JFrame("oopG22 Aufgabe 1 - Vogelschwarm");
		frame.getContentPane().add(new Test());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800, 800);
		frame.setLocationByPlatform(true);
		frame.setVisible(true);
		frame.setResizable(false);
	}
	
	public static void printCoords(String xfile, String yfile) {
		
		double[] xsaved = new double[swarmsize];
		double[] ysaved = new double[swarmsize];
		for (int i = 0; i < swarm.length; i++) {
			xsaved[i] = (double) Math.round(swarm[i].xcoord * 10000) / 10000;
			ysaved[i] = (double) Math.round(swarm[i].ycoord * 10000) / 10000;
		}       
		
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(new File(xfile+".csv"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
        StringBuilder sbx = new StringBuilder();
        for (int i = 0; i < xsaved.length; i++) {
        	sbx.append(xsaved[i]);
            sbx.append(',');
		}
        pw.write(sbx.toString());
        pw.close();
        
        try {
			pw = new PrintWriter(new File(yfile+".csv"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
        StringBuilder sby = new StringBuilder();
        for (int i = 0; i < xsaved.length; i++) {
        	sby.append(ysaved[i]);
            sby.append(',');
		}
        pw.write(sby.toString());
        pw.close();
	}
	
	// TODO: method to build simulations
	public static void start(Swarm s, int size, int minD, String type) throws InterruptedException {
		
		// TODO: general movement rules
		size = s.swarmsize;
		minD = s.minDistance;
		type = s.type;
		
		generate (s);

		// all bird swarms behave the same ... the movement is predetermined by us
		if (s.type == "Animal") {
			makeswarm(s.type);
			s.select = (int) (Math.random() * swarm.length-1);
			double a = 4 + Math.random() * 14;
			double b = 8 + Math.random() * 8;
			for (int i = 0; i < 10; i++) {
				moveAnimal(s.swarm[s.select], a, 0, 0, b);
				resetMoved();
			}
			
			resetMoved();
			s.select = (int) (Math.random() * swarm.length-1);
			// diagonal movement - works with NO, SO, SW, NO only
			for (int i = 0; i < 200/4; i++) {
				moveAnimal(s.swarm[s.select], 0, 8, 8, 0);
				resetMoved();
			}
		}
		if (s.type == "Bird") {
			// stressed & tired
			for (int i = 0; i < s.flock.length; i++) {
				s.flock[i].stressed = true;
				s.flock[i].tired = false;
				s.flock[i].danger(dangerX, dangerY);
			}
			makeswarm(s.type);
			int closest = 0;
			double max = 0, xdist, ydist, dist = 0;
			for (int i = 0; i < s.flock.length; i++) {
				xdist = Math.pow((s.flock[i].xcoord - dangerX), 2);
				ydist = Math.pow((s.flock[i].ycoord - dangerY), 2);
				dist = Math.sqrt(xdist + ydist);
				if ( dist > max) {
					closest = i;
					max = dist;
				}
			}
			
			// the closest bird to danger starts fleeing
			s.select = closest;
			double x = s.flock[closest].xcoord;
			double y = s.flock[closest].ycoord;
			// moveRight = x1, moveLeft = x2,moveUp = y1, moveDown = y2
			// danger is east & south or equal
			if (x >= dangerX && y >= dangerY) {
				for (int i = 0; i < 20; i++) {
					moveAnimal(s.flock[s.select], 90 - dist, 0, 90 - dist, 0);
					resetMoved();
				}	
			}
			// danger is 

			if (x < dangerX && y > dangerY) {
				for (int i = 0; i < 20; i++) {
					moveAnimal(s.flock[s.select], 0, 90 - dist, 90 - dist, 0);
					resetMoved();
				}	
			}
			// danger is 
			if (x > dangerX && y < dangerY) {
				for (int i = 0; i < 20; i++) {
					moveAnimal(s.flock[s.select], 90 - dist, 0, 0, 90 - dist);
					resetMoved();
				}	
			}
			// danger is 
			if (x < dangerX && y < dangerY) {
				for (int i = 0; i < 20; i++) {
					moveAnimal(s.flock[s.select], 0, 90 - dist, 0, 90 - dist);
					resetMoved();
				}	
			}
		}
		// TODO
		if (s.type == "Insect") {
			makeswarm(s.type);
			s.select = (int) (Math.random() * s.swarm.length-1);
		}
		if (s.type == "LargeBird") {
			s.select = 0;
		}
	}

	// allows variables of Swarm to be readily used with all methods
	public static void generate (Swarm s) throws InterruptedException {
		swarmsize = s.swarmsize;
		minDistance = s.minDistance;
		typeToDraw = s.type;
		if (s.type == "Animal" || s.type == "LargeBird") {
			swarm = s.swarm;
		}
		if (s.type == "Bird") {
			swarm = s.flock;
		}
		if (s.type == "Insect") {
			swarm = s.colony;
		}
	}
	
	// TODO: move variables to classes/objects if possible
	public static int swarmsize = 320;
	public static Animal[] swarm = new Animal[swarmsize];
	public static double minDistance = 12;

	// initialize moved array	
	public static boolean[] moved = new boolean[swarm.length];
	public static void resetMoved() {
		for (int i = 0; i < swarm.length; i++) {
			moved[i] = false;
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		
		// Run GUI
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				GUI();
			}
		});
				
		/* Simulations - each step starts after predetermined time (in seconds) - it takes runtime + 108 seconds of waiting to complete
		 * Parameters are: type, swarmsize, minDistance and the movement pattern (incl. a random distance component) of the swarm;
		 * Animals themselves are quasi-randomly pre-generated
		 * Direction of movement is predetermined, magnitude partially random (to fit the style of makeswarm)*/
		
		// TODO: make and simulate arrays for predetermined swarm
		makeswarm("Animal");
		
		// moveRight = x1, moveLeft = x2,moveUp = y1, moveDown = y2
		int select = (int) (Math.random() * swarm.length-1);
		TimeUnit.SECONDS.sleep(2);
		// ==> Nord-Ost Flug
		double x = 4 + Math.random() * 14;
		double y = 8 + Math.random() * 8;
		for (int i = 0; i < 10; i++) {
			moveAnimal(swarm[select], x, 0, 0, y);
			resetMoved();
		}
		
		resetMoved();
		select = (int) (Math.random() * swarm.length-1);
		// diagonal movement - works with NO, SO, SW, NO only
		for (int i = 0; i < 200/4; i++) {
			moveAnimal(swarm[select], 0, 8, 8, 0);
			resetMoved();
		}

		// ==> Süd-Ost Flug
		// moveAnimal(swarm[select], (120 + Math.random() * 60), 0, (80 + Math.random() * 80), 0);
		
		// ==> West Flug
		// moveAnimal(swarm[select], 0, (60 + Math.random() * 120), 0, 0);
		
		Swarm regular = new Swarm();
		start(regular, 260, 12, "Animal");
		
		Swarm birds = new Swarm();
		start(birds, 260, 12, "Bird");
		
		Swarm insects = new Swarm();
		start(insects, 10000, 0, "Insect");
		
		Swarm large = new Swarm();
		start(large, 9, 10, "LargeBird");
	}
}

/* TODO:  Beschreibung wer an welchem Teil gearbeitet hat, entsprechend der Angabe
 * Elias Nachbaur (01634010): 
 * Florian Fusstetter (00709759): 
 * Ignjat Karanovic (01529940): */
