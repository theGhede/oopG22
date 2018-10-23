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

/* TODO:
 * Die Simulation soll in einem dreidimensionalen Raum ablaufen.
 * Zur einfachen Darstellung der dritten Dimension am Bildschirm eignen sich Helligkeitswerte und Farben.
 *  ===========
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
 *  ===========
	Detaillierte Simulationen sind aufwendig. Große Schwärme werden in der nötigen Genauigkeit kaum in Echtzeit simulierbar sein.
	Daher müssen Simulationsergebnisse aufgezeichnet und später, wenn gewünscht wiederholt dargestellt werden. Dennoch sollten, zumindest
	für kleine Schwärme, Simulationen in Echtzeit angestrebt werden.

	Doable - erstellen & speichern Daten von x/y Koordinaten und nutzen dies um unseren Schwarm zu erstellen - spart Zeit bei Programmstart
	gegenüber unserer quasi-zufälligen Methode. Frage ist, ob dies notwendig ist. DIes ist auch ohnehin unsere erste Alternative zu zufällig 
	erstellten Schwärmen.
 *  ===========
	Simulationen von Schwärmen zahlreicher Tierarten sind nötig. Für jede Tierart sind die physischen Parameter und individuellen 
	Verhaltensmuster separat festzulegen. Dennoch brauchen wir ein einheitliches Framework, das alle Teile der Simulation übernimmt, 
	die nicht von diesen individuellen Mustern abhängen. Die vielen Tierarten und die Verschiedenartigkeit der Verhaltensmuster verursachen 
	einen gewaltigen Implementierungsaufwand. Daher sollte das Framework so viel Arbeit wie möglich übernehmen und die Implementierung der 
	Verhaltensmuster vereinfachen.

	Hauptaufgabe. → Untertypen von Schwärmen.
	Frage: Benötigen wir Untertypen für Tiere? Nur falls nicht alle Arten stressed[] & 
	fitness[] brauchen.
	Wichtige schwarmspezifische Parameter sind minDistance & swarmSize - von uns 
	gesetzte Größen & nicht Teil des Schwarmobjekts. Diese Größen werden zur Simulation von uns gesetzt → Steuergrößen.*/

public class Test extends JPanel implements ActionListener {
	
	// TODO: CHECK: rewrite vocabulary to "swarm" and "animal" since we're doing insects or whatever now 
	
	// TODO: split off classes
	// Tiere bestehen aus Koordinaten & einer Nachbarschaft; jeder Vogel hat für einfacheren Zugriff einen ID als index gespeichert
	private static class Animal {


		double xcoord;
		double ycoord;
		Animal[] neighbors;
		int index;
		// this lets us manipulate the length of an animals movement
		double modifier;
		boolean tired = false;
		if (modifier <= 0.5){
			tired = true;
		}  else if (modifier >= 1) {
			tired = false;
		}
		// TODO: issue: movement currently cannot be diagonal & this is very apparent with animations in place
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
			if (tired = false){
				modifier -= 0.25;
			}else {
				modifier += 0.25;
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
			modifier -= 0.25;
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
			modifier -= 0.25;
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
			modifier -= 0.25;
		}
	}
	
	// TODO: subclasses of Animal (Ants, large Animals in stricter formation, regular Animals with modifiers)
	// regular Animal including new behavior for tiring and stress
	private static class Bird extends Animal{
		// stressed
		// tired/stamina
	}
	
	// large Animal type of which only a few fly in formation
	private static class LargeAnimal extends Animal {
		/* TODO: decide if this is necessary or whether the "flight in formation" without stress or
		 * stamina is enough for this type */
	}
	
	// insects which can crawl over eachother and are tireless but react to danger if we want them to
	private static class Insect extends Animal {
		
	}

	// TODO: choose k neighbors to be rendered in grey
	// Aufbau der Nachbarschaft eines Vogels mittels "Sonar" bis (5-20) Nachbarn gefunden wurden
	public static void neighborhood(Animal b, int radius) {
		int j = 0;
		int amount = (int) (5 + (15 * Math.random()));
		Animal[] neighbors = new Animal[amount];
		if (b.neighbors != null) {
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
	}

	/* Nachbarschaftsbewegung, moveUp = y1, moveDown = y2, moveRight = x1, moveLeft = x2
	 * Im Flug wird überprüft ob der sich gerade fortbewegende Vogel zu nah an andere annähert
	 * Rekursiv fliegen alle Tiere in alle Nachbarschaften mit gleicher Entfernung in die gleiche Richtung
	 * Angestoßen wird die Bewegung durch einen einzelnes gewähltes Tier*/
	
	// TODO: figure out whether and how to do diagonal movement
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
			for (int i = 0; i < swarm.length; i++) {
				for (int j = 0; j < swarm.length; j++) {
					if (distance(swarm[i], swarm[j]) > minDistance) {
						testDistance();
					}
				}
			}	
		}
	}

	// angle etc in eigene Methode auslagern
	public static double[] distanceHelper (double helpDistance, double xDistance, double yDistance) {
		// [xMove, y Move]
		double[] res = new double[1];
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
						swarm[j].moveDown((yMove));
						swarm[j].moveLeft((xMove));
					}
					else if(swarm[j].ycoord > swarm[i].ycoord && swarm[j].xcoord > swarm[i].xcoord){
						swarm[j].moveUp((yMove));
						swarm[j].moveRight((xMove));
					}
					else if(swarm[j].ycoord < swarm[i].ycoord && swarm[j].xcoord > swarm[i].xcoord) {
						swarm[j].moveDown((yMove));
						swarm[j].moveRight((xMove));
					}
					else if(swarm[j].ycoord > swarm[i].ycoord && swarm[j].xcoord < swarm[i].xcoord) {
						swarm[j].moveUp((yMove));
						swarm[j].moveLeft((xMove));
					}
					// Vogel wird nach links bewegt, falls er sich rechts von dem Anderen befindet, sonst nach rechts
					else if(swarm[j].ycoord == swarm[i].ycoord) {
						if(swarm[j].xcoord < swarm[i].xcoord) {
							swarm[j].moveLeft(helpDistance);
						}
						else if(swarm[j].xcoord > swarm[i].xcoord){
							swarm[j].moveRight(helpDistance);
						}
					}
					// Vogel wird nach unten bewegt, falls er sich unter dem Anderen befindet, sonst nach oben
					else if(swarm[j].xcoord == swarm[i].xcoord) {
						if(swarm[j].ycoord < swarm[i].ycoord) {
							swarm[j].moveDown(helpDistance);
						} else if(swarm[j].ycoord < swarm[i].ycoord){
							swarm[j].moveUp(helpDistance);
						}
					}
				}
			}
		}
	}

	
	// TODO: move variables to classes/objects if possible
	// Die Anzahl der Tiere ist willkürlich vorbestimmt
	// TODO: error! swarms aren't yet manipulated for simulations
	public static int swarmsize = 200;
	public static Animal[] swarm = new Animal[swarmsize];
	public static boolean[] moved = new boolean[swarm.length];
	// Mindestabstand - willkürlich definiert
	public static double minDistance = 14;
	
	// TODO: make super class for Swarm
	private static class Swarm {
		
	}
	
	
	
	// TODO: make subclasses of Swarm for our different animals
	private static class Flock extends Swarm{
		
	}
	
	private static class FlightInFormation extends Swarm {
		
	}
	
	private static class Colony extends Swarm {
		
	}
	
	// this is used to cut out time delays when first making and correcting a swarm
	public static boolean existing;
	// TODO: transform to become a method of Swarm
	// Make swarm within the center 400x400 of the JFrame; reminder: top-right = (0,0)
	public static void makeswarm() throws InterruptedException {
		existing = false;
		double[] xvalues = new double[swarm.length];
		double[] yvalues = new double[swarm.length];
		for(int i = 0; i < swarm.length; i++) {
			xvalues[i] = (Math.random() * 400) + 200;
			yvalues[i] = (Math.random() * 400) + 200;
		}
		for(int i = 0; i < swarm.length; i++) {
			// make new Animal Objects here
			Animal b = new Animal();
			b.xcoord = xvalues[i];
			b.ycoord = yvalues[i];
			b.index = i;
			swarm[i] = b;
			b.modifier = 1
			;
		}
		// Find neighbors for each Animal
		for (int i = 0; i < swarm.length; i++) {
			neighborhood(swarm[i], 1);
		}
		// check and repair minimal distance infringements
		establishDistance();
		existing = true;
	}

	// TODO: update by repaint every 4 milliseconds (==> after the intervals found in main)
	Timer t = new Timer(4, this);
	
	// draw graphics using paint(g) with Graphics2D for double variables
	public void paintComponent(Graphics g) {
		// use this to draw the initial swarm via for loop as dots
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		// TODO: different sizes for different animals; reminder: shape coordinates are the upper left point
		// TODO: grey animals
		for (int i = 0; i < swarm.length; i++) {
			double x = swarm[i].xcoord;
			double y = swarm[i].ycoord;
			Shape s = new Ellipse2D.Double(x, y, 4, 4);
			g2d.draw(s);
			g2d.fill(s);
			// maybe useful Swing methods: validate() & revalidate()
		}
		// start Timer t
		t.start();
	}
	
	public void actionPerformed(ActionEvent e) {
		repaint();
	}
	// initialize moved array	
	public static void resetMoved() {
		for (int i = 0; i < swarm.length; i++) {
			moved[i] = false;
		}
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
	public void sstart(Swarm1 s, int size, int minD, double x, double y) {
		// TODO: select an animal based on swarm type OR use overrides
		size = s.swarmsize;
		minD = s.minDistance;
		double x1 = 0, x2 = 0, y1 = 0, y2 = 0;
		if (x < 0) {
			x2 = x;
		}
		if (x >= 0) {
			x1 = x;
		}
		if (y < 0) {
			y2 = y;
		}
		if (y >= 0) {
			y1 = y;
		}
		// moveAnimal(s.swarm[s.select], x1, x2, y1, y2);
	}

	public static void main(String[] args) throws InterruptedException {

		// TODO: make and simulate arrays for predetermined swarm
		resetMoved();
		makeswarm();
		
		// Run GUI
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				GUI();
			}
		});
		
		// TODO: make new simulations for new animal types and move minDistance and swarmsize to classes
		
		/* Simulations - each step starts after predetermined time (in seconds) - it takes runtime + 108 seconds of waiting to complete
		 * Parameters are: swarmsize, minDistance and the movement (incl. a random distance component) of the swarm;
		 * Animals themselves are quasi-randomly generated
		 * Direction of movement is predetermined, magnitude partially random (to fit the style of makeswarm)*/
		
		// moveRight = x1, moveLeft = x2,moveUp = y1, moveDown = y2
		int select = (int) (Math.random() * swarm.length-1);
		TimeUnit.SECONDS.sleep(12);
		// ==> Nord-Ost Flug
		moveAnimal(swarm[select], (40 + Math.random() * 140), 0, 0, (80 + Math.random() * 80));
		TimeUnit.SECONDS.sleep(2);
		establishDistance();

		// slightly more Animals who want to keep more distance
		TimeUnit.SECONDS.sleep(20);
		minDistance = 20;
		swarmsize = 260;
		
		resetMoved();
		makeswarm();

		select = (int) (Math.random() * swarm.length-1);
		TimeUnit.SECONDS.sleep(12);
		// ==> Süd-Ost Flug
		moveAnimal(swarm[select], (120 + Math.random() * 60), 0, (80 + Math.random() * 80), 0);
		TimeUnit.SECONDS.sleep(4);
		establishDistance();

		// a lot more Animals who accept flying more closely 
		TimeUnit.SECONDS.sleep(20);
		minDistance = 8;
		swarmsize = 400;

		resetMoved();
		makeswarm();

		select = (int) (Math.random() * swarm.length-1);
		TimeUnit.SECONDS.sleep(12);
		// ==> West Flug
		moveAnimal(swarm[select], 0, (60 + Math.random() * 120), 0, 0);
		TimeUnit.SECONDS.sleep(4);
		establishDistance();
	}
}

/* TODO:  Beschreibung wer an welchem Teil gearbeitet hat, entsprechend der Angabe
 * Elias Nachbaur (01634010): 
 * Florian Fusstetter (00709759): 
 * Ignjat Karanovic (01529940): */
