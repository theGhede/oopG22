package oopG22;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.util.concurrent.TimeUnit;
import javax.swing.*;

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

	
	// test variables
	public static int up = 0, down = 0, right = 0, left = 0;
	
	// Vögel bestehen aus Koordinaten & einer Nachbarschaft; jeder Vogel hat für einfacheren Zugriff einen ID als index gespeichert
	private static class Bird {

		double xcoord;
		double ycoord;
		Bird[] neighbors;
		int index;

		void moveUp(double k) throws InterruptedException {
			if(existing) {
				for (int i = 0; i < k; i++) {
					TimeUnit.MICROSECONDS.sleep(500);
					double move = this.ycoord + 1;
					this.ycoord = move;
				}
			} else {
				double move = this.ycoord + k;
				this.ycoord = move;
				up++;
			}
		}

		void moveDown(double k) throws InterruptedException {
			if(existing) {
				for (int i = 0; i < k; i++) {
					TimeUnit.MICROSECONDS.sleep(500);
					double move = this.ycoord - 1;
					this.ycoord = move;
				}
			} else {
				double move = this.ycoord - k;
				this.ycoord = move;
				down++;
			}
		}

		void moveLeft(double k) throws InterruptedException {
			if(existing) {
				for (int i = 0; i < k; i++) {
					TimeUnit.MICROSECONDS.sleep(500);
					double move = this.xcoord - 1;
					this.ycoord = move;
				}
			} else {
				double move = this.xcoord - k;
				this.ycoord = move;
				left++;
			}
		}

		void moveRight(double k) throws InterruptedException {
			if(existing) {
				for (int i = 0; i < k; i++) {
					TimeUnit.MICROSECONDS.sleep(500);
					double move = this.xcoord + 1;
					this.ycoord = move;
				}
			} else {
				double move = this.xcoord + k;
				this.ycoord = move;
				right++;
			}
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


	/* Nachbarschaftsbewegung, moveUp = y1, moveDown = y2, moveRight = x1, moveLeft = x2
	 * Im Flug wird überprüft ob der sich gerade fortbewegende Vogel zu nah an andere annähert
	 * Rekursiv fliegen alle Vögel in alle Nachbarschaften mit gleicher Entfernung in die gleiche Richtung
	 * Angestoßen wird die Bewegung durch einen einzelnen zufällig gewählten Vogel*/
	public static void moveBird (Bird b, double x1,double x2, double y1, double y2) throws InterruptedException {
		if (!moved[b.index]){
			b.moveUp(y1);
			b.moveDown(y2);
			b.moveLeft(x2);
			b.moveRight(x1);
			moved[b.index] = true;
			movingDistance(b);
		}
		for (int i = 0; i < b.neighbors.length ; i++) {
			if (!moved[b.neighbors[i].index]) {
				moveBird(b.neighbors[i],x1,x2,y1,y2);}
		}
	}
	
	public static void movingDistance(Bird b) throws InterruptedException {
		for (int i = 0; i < flock.length; i++) {
			if (distance(flock[b.index], flock[i]) < minDistance) {
				testDistance();
			}
		}
	}

	// Der Schwarm wird von einem Array von Vögeln repräsentiert
	// Die Anzahl der Vögel ist willkürlich vorbestimmt
	public static int flocksize = 200;
	public static Bird[] flock = new Bird[flocksize];
	public static boolean[] moved = new boolean[flock.length];
	// Mindestabstand - willkürlich definiert
	public static double minDistance = 14;

	// Behandlung von Mindestabstandsverletzungen
	// Die Magnitüde & Richtung der Bewegung richtet sich nach helpDistance und geschieht entlang der Geraden zwischen Punkt i & j
	public static void testDistance () throws InterruptedException {
		for(int i = 0; i < flock.length; i++) {
			for(int j = 0; j < flock.length; j++) {
				if(distance(flock[i], flock[j]) < minDistance) {
					double helpDistance = minDistance - distance(flock[i], flock[j]);
					double yDistance = (flock[i].xcoord - flock[j].xcoord);
					double xDistance = (flock[i].ycoord - flock[j].ycoord);

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
						else if(flock[j].xcoord > flock[i].xcoord){
							flock[j].moveRight(helpDistance);
						}
					}
					// Vogel wird nach unten bewegt, falls er sich unter dem Anderen befindet, sonst nach oben
					else if(flock[j].xcoord == flock[i].xcoord) {
						if(flock[j].ycoord < flock[i].ycoord) {
							flock[j].moveDown(helpDistance);
						} else if(flock[j].ycoord < flock[i].ycoord){
							flock[j].moveUp(helpDistance);
						}
					}
				}
			}
		}
	}
	
	// checks if testDistance & moves defined there are needed - this takes a couple of seconds while the randomized flock is being made
	public static void keepDistance () throws InterruptedException {
		for (int i = 0; i < flock.length; i++) {
			for (int j = 0; j < flock.length; j++) {
				if (distance(flock[i], flock[j]) > minDistance) {
					testDistance();
				}
			}
		}
	}

	// Berechnen der euklidischen Distanz
	public static double distance (Bird a, Bird b) {
		double xdist = Math.pow((a.xcoord - b.xcoord), 2);
		double ydist = Math.pow((a.ycoord - b.ycoord), 2);
		double dist = Math.sqrt(xdist + ydist);
		return dist;
	}

	public static boolean existing;
	// Make flock within the center 400x400 of the JFrame; reminder: top-right = (0,0)
	public static void makeFlock() throws InterruptedException {
		existing = false;
		double[] xvalues = new double[flock.length];
		double[] yvalues = new double[flock.length];
		for(int i = 0; i < flock.length; i++) {
			xvalues[i] = (Math.random() * 400) + 200;
			yvalues[i] = (Math.random() * 400) + 200;
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
		keepDistance();
		existing = true;
	}

	// draw graphics using paint(g) with Graphics2D for double usage
	public void paintComponent(Graphics g) {
		// use this to draw the initial flock via for loop as dots
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		// start Timer t
		t.start();
		for (int i = 0; i < flock.length; i++) {
			double x = flock[i].xcoord;
			double y = flock[i].ycoord;
			Shape s = new Ellipse2D.Double(x, y, 4, 4);
			g2d.draw(s);
			g2d.fill(s);
		}
	}

	private static JFrame GUI() {

		JFrame frame = new JFrame("oopG22 Aufgabe 1 - Vogelschwarm");
		frame.getContentPane().add(new Test());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800, 800);
		frame.setLocationByPlatform(true);
		frame.setVisible(true);
		return frame;
	}
	
	// update by repaint every 4 milliseconds (==> after the intervals found in main)
	Timer t = new Timer(4, this);
	public void actionPerformed(ActionEvent e) {
		repaint();
	}

	public static void main(String[] args) throws InterruptedException {

		makeFlock();
		// moved array
		for (int i = 0; i < flock.length; i++) {
			moved[i] = false;
		}
		System.out.println("up: " + up + "   down: "+ down + "   left: " + left + "   right: " + right);

		// Run GUI
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				GUI();
			}
		});

		/* Simulations - each step starts after predetermined time (in seconds) - it takes runtime + 108 seconds of waiting to complete
		 * Parameters are: flocksize, minDistance and the movement (incl. a random distance component) of the flock;
		 * Birds themselves are quasi-randomly generated
		 * Direction of movement is predetermined, magnitude partially random (to fit the style of makeFlock)*/
		
		// moveRight = x1, moveLeft = x2,moveUp = y1, moveDown = y2
		int select = (int) (Math.random() * flock.length-1);
		TimeUnit.SECONDS.sleep(12);
		// ==> Nord-Ost Flug
		moveBird(flock[select], (40 + Math.random() * 140), 0, 0, (80 + Math.random() * 80));
		TimeUnit.SECONDS.sleep(4);
		keepDistance();

		// reset moved
		for (int i = 0; i < flock.length; i++) {
			moved[i] = false;
		}

		// slightly more birds who want to keep more distance
		TimeUnit.SECONDS.sleep(20);
		minDistance = 20;
		flocksize = 260;

		makeFlock();

		select = (int) (Math.random() * flock.length-1);
		TimeUnit.SECONDS.sleep(12);
		// ==> Süd-Ost Flug
		moveBird(flock[select], (120 + Math.random() * 60), 0, (80 + Math.random() * 80), 0);
		TimeUnit.SECONDS.sleep(4);
		keepDistance();

		// reset moved
		for (int i = 0; i < flock.length; i++) {
			moved[i] = false;
		}

		// a lot more birds who accept flying more closely 
		TimeUnit.SECONDS.sleep(20);
		minDistance = 8;
		flocksize = 400;

		makeFlock();

		select = (int) (Math.random() * flock.length-1);
		TimeUnit.SECONDS.sleep(12);
		// ==> West Flug
		moveBird(flock[select], 0, (60 + Math.random() * 120), 0, 0);
		TimeUnit.SECONDS.sleep(4);
		keepDistance();
	}
}

/* TODO:  Beschreibung wer an welchem Teil gearbeitet hat, entsprechend der Angabe
 * Elias Nachbaur (01634010): made testDistance and it's calculations
 * Florian Fusstetter (00709759): JFrame, paint & repaint, Simulation, Bird & makeFlock, distance (keep & moving), testDistance debugging
 * Ignjat Karanovic (01529940): neighborhood, moveBird, testDistance debugging*/

/* Kommentar zu git:
 * Aufgrund dessen, dass sich unsere Gruppe erst spät gefunden hat & an unserem ersten vis-a-vis Treffen im CL-Lab g0.complang nicht zur
 * Verfügung stand sind wir bis auf die Abgabe auf eine eigenen public gitlab repo ausgewichen */ 