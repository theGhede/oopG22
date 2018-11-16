package aufgabe3;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.util.concurrent.TimeUnit;
import javax.swing.*;
// florian
/* TODO:
 * 
 * neighborhood fix for small swarms
 * 	- Swarm +
 *  - Flock +
 * 
 * data hiding
 *  - Test
 *  - Animal
 *  - Bird
 *  - Insect
 *  - Swarm
 *  - Flock
 *  - Colony
 * 
 * dynamic binding email
 * 
 * ERRORs
 * 
 * Preconditions & Postconditions ... Invarianten
 *  - Test
 *  - Animal
 *  - Bird
 *  - Insect
 *  - Swarm
 *  - Flock
 *  - Colony */ 

/* Concerning cohesion & coupling
 * To us having high cohesion is much more intuitive than loose coupling and while cohesion may vary
 * from class to class it's never low. Coupling on the other hand is tight in a few spots where it
 * doesn't have to be - at times we favored simplicity over looser coupling, which generally we think
 * can be agreeable depending on the project in question but we acknowledge that for this assignment
 * the added complexity should've been favored. */

/* NOTE: Since the entire program has no input of any kind we were not quite on top of things
 * when it comes to shielding our program from null exceptions. But through assertions and the
 * predetermined nature of our coordinates, array sizes, etc. this has not been an issue.
 * (Even if some numbers are randomly generated within a specific range)
 * The larger downside is that we have really need to be sure makeswarm is called when it should be
 * and we rely on swarm[] being accessible and filled with animals of the correct type that after
 * that's done. While we would not give this an ERROR or BAD tag, we certainly don't think it's GOOD. */

public class Test extends JPanel implements ActionListener {
	Timer t = new Timer(4, this);

	public static String typeToDraw;
	public static Swarm regular = new Swarm();
	public static Flock birds = new Flock();
	public static Colony insects = new Colony();
	// assertion: all of these exist and can be used; especially important for paintComponent

	// NOTE: these coordinates could be made final
	public static double dangerX = (double) Math.round((100 + Math.random() * 80) * 100) / 100;
	public static double dangerY = (double) Math.round((400 + Math.random() * 150) * 100) / 100;
	// logical assertion { dangerX, dangerY are a number between 0 and 800 }
	// while the program could handle any double some are pointless to use since the
	// JFrame is 800x800 (same for the other two types)

	/* NOTE: it would've been possible to move the Swing portion to its own class in order to fit the
	 * class name better and have Test contain nothing but some variables and the main method;
	 * however in regards to methods within Test all methods concern only Swing and a major part of
	 * the main method is the creation of the runnable for our GUI */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		if (typeToDraw == "Animal") {
			for (int i = 0; i < regular.swarm.length; i++) {
				double x = regular.swarm[i].xcoord;
				double y = regular.swarm[i].ycoord;
				Shape a = new Ellipse2D.Double(x, y, 4, 4);

				if (i % 4 == 0) {
					g2d.setPaint(Color.GRAY);
				} else {
					g2d.setPaint(Color.BLACK);
				}
				g2d.fill(a);
			}
		}
		if (typeToDraw == "Bird") {
			Shape d = new Ellipse2D.Double(dangerX, dangerY, 12, 12);
			g2d.setPaint(Color.RED);
			g2d.fill(d);
			for (int i = 0; i < birds.swarm.length; i++) {
				double x = birds.swarm[i].xcoord;
				double y = birds.swarm[i].ycoord;
				Shape b = new Ellipse2D.Double(x, y, 5, 5);

				if (i % 4 == 0) {
					g2d.setPaint(Color.GRAY);
				} else {
					g2d.setPaint(Color.BLACK);
				}
				if (i == birds.select) {
					g2d.setPaint(Color.RED);
				}
				g2d.fill(b);
			}
		}
		if (typeToDraw == "Insect") {
			for (int i = 0; i < insects.swarm.length; i++) {
				double x = insects.swarm[i].xcoord;
				double y = insects.swarm[i].ycoord;
				Shape s = new Ellipse2D.Double(x, y, 2, 2);

				if (i % 3 == 0) {
					g2d.setPaint(Color.GRAY);
				} else {
					g2d.setPaint(Color.BLACK);
				}
				g2d.fill(s);
			}
		}
		t.start();
	}

	public void actionPerformed(ActionEvent e) {
		repaint();
	}

	private static void GUI() {
		// ERROR: we missed adjusting the frame title
		JFrame frame = new JFrame("oopG22 Aufgabe 1 - Vogelschwarm");
		frame.getContentPane().add(new Test());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800, 800);
		frame.setLocationByPlatform(true);
		frame.setVisible(true);
		frame.setResizable(false);
	}

	public static void main(String[] args) throws InterruptedException {

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				GUI();
			}
		});


		/*
		 * NOTE: While the program has no input at runtime, testing and designing the
		 * simulations is much easier by having makeswarm use certain parameters that
		 * make this part more usable for us & in our opinion more readable in general.
		 * This is also our reasoning behind not hiding the following lines of code in
		 * some method - within the main method we find some code duplication to be a
		 * small price to pray for the benefits
		 */
		/*
		 * BAD: said parameters of course need to fulfill specific criteria:
		 * 1. the string is basically only used to confirm which type of swarm we're making and
		 * 	  must be specific, it's just sugar for ourselves
		 * 2. swarmsize generally only make sense if > 0; but because of how neighborhoods are
		 * 	  calculated it must be >= 20 (only true for the 2 types that require neighbors)
		 * 3. negative minimal distance is pointless but the program actually could handle it,
		 * 	  even if it made no sense to do that
		 */
		regular.makeswarm("Animal", 320, 12);
		/* assertion { regular.swarmsize >= 20 } & { regular.minDistance >= 0 }
		 * as soon as typeToDraw is initialized the program will want to use
		 * regular.swarm.length & creation of a neighborhood requires swarmsize to be >= 20 */
		
		/* BAD: object coupling could've been improved by implementing a getter method for type,
		 * however this would add complexity without actually reaping the benefits from this
		 * encapsulation */
		typeToDraw = regular.type;
		regular.start();
		TimeUnit.SECONDS.sleep(4);


		birds.makeswarm("Bird", 20, 14);

		birds.makeswarm("Bird", 240, 14);
		/* assertion { birds.swarmsize >= 20 } & { birds.minDistance >= 0 }
		 * as soon as typeToDraw is initialized the program will want to use
		 * birds.swarm.length & creation of a neighborhood requires swarmsize to be >= 20 */
		typeToDraw = birds.type;
		birds.start(dangerX, dangerY);
		TimeUnit.SECONDS.sleep(4);


		insects.makeswarm("Insect", 100, 0);

		insects.makeswarm("Insect", 10000, 0);
		/* assertion { regular.swarmsize >= 0 }
		 * as soon as typeToDraw is initialized the program will want to use
		 * insects.swarm.length
		 * minimal distance is a property inherited from Swarm & helps understanding,
		 * however Colony and Insect work fine if any number would be chosen;
		 * the fact that it's there and 0 is mainly for transparency */
		typeToDraw = insects.type;
		insects.start();
	}
}

/* TODO:  Beschreibung wer an welchem Teil gearbeitet hat, entsprechend der Angabe
 * Elias Nachbaur (01634010): Comments for version from last Wednesday, made testDistance and it's calculations
 * Florian Fusstetter (00709759): Comments for Test, Animal & Swarm, Swing, start & makeswarm, distance (keep & moving),
 * 								  testDistance debugging, restructuring into Test + 3 class pairs
 * Ignjat Karanovic (01529940): Comments for Bird, Flock, Insect & Colony, neighborhood, center for Flock, moveBird,
 * 							    testDistance debugging */