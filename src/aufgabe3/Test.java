package aufgabe3;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.util.concurrent.TimeUnit;
import javax.swing.*;

/* TODO:
 * 
 * data hiding
 *  - Test +
 *  - Animal +
 *  - Bird +
 *  - Insect +
 *  - Swarm +
 *  - Flock +
 *  - Colony +
 */ 

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
	public Timer t = new Timer(4, this);

	private static String typeToDraw;
	private static Swarm regular = new Swarm();
	private static Flock birds = new Flock();
	private static Colony insects = new Colony();
	// Postcondition: assertion: all of these exist and can be used; especially important for paintComponent

	private static final double dangerX = (double) Math.round((100 + Math.random() * 80) * 100) / 100;
	private static final double dangerY = (double) Math.round((400 + Math.random() * 150) * 100) / 100;
	// Postcondition: assertion { dangerX, dangerY are a number between 0 and 800 }
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
			for (int i = 0; i < regular.getSwarm().length; i++) {
				double x = regular.getSwarm()[i].getXcoord();
				double y = regular.getSwarm()[i].getYcoord();
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
			for (int i = 0; i < birds.getSwarm().length; i++) {
				double x = birds.getSwarm()[i].getXcoord();
				double y = birds.getSwarm()[i].getYcoord();
				Shape b = new Ellipse2D.Double(x, y, 5, 5);

				if (i % 4 == 0) {
					g2d.setPaint(Color.GRAY);
				} else {
					g2d.setPaint(Color.BLACK);
				}
				if (i == birds.getSelect()) {
					g2d.setPaint(Color.RED);
				}
				g2d.fill(b);
			}
		}
		if (typeToDraw == "Insect") {
			for (int i = 0; i < insects.getSwarm().length; i++) {
				double x = insects.getSwarm()[i].getXcoord();
				double y = insects.getSwarm()[i].getYcoord();
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
		JFrame frame = new JFrame("oopG22 Aufgabe 3");
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
		 * NOTE: said parameters of course need to fulfill specific criteria:
		 * 1. the string is basically only used to confirm which type of swarm we're making and
		 * 	  must be specific, it's just sugar for ourselves
		 * 2. swarmsize generally only make sense if > 0
		 * 3. negative minimal distance is pointless but the program actually could handle it,
		 * 	  even if it made no sense to do that
		 */
		regular.makeswarm("Animal", 320, 8);
		/* Invariante (Server & Client): assertion { regular.swarmsize >= 0 } & { regular.minDistance >= 0 }
		 * as soon as typeToDraw is initialized the program will want to use
		 * regular.swarm.length to be >= 0 */
		typeToDraw = regular.getType();
		regular.start();
		TimeUnit.SECONDS.sleep(4);

		birds.makeswarm("Bird", 240, 14);
		/* Invariante (Server & Client): assertion { birds.swarmsize > 0 } & { birds.minDistance >= 0 }
		 * as soon as typeToDraw is initialized the program will want to use
		 * birds.swarm.length >= 0 */
		typeToDraw = birds.getType();
		birds.start(dangerX, dangerY);
		TimeUnit.SECONDS.sleep(4);

		insects.makeswarm("Insect", 10000, 0);
		/* Invariante (Server & Client): assertion { regular.swarmsize >= 0 }
		 * as soon as typeToDraw is initialized the program will want to use
		 * insects.swarm.length
		 * minimal distance is a property inherited from Swarm & helps understanding,
		 * however Colony and Insect work fine if any number would be chosen;
		 * the fact that it's there and 0 is mainly for transparency */
		typeToDraw = insects.getType();
		insects.start();
	}
}

/* TODO:  Beschreibung wer an welchem Teil gearbeitet hat, entsprechend der Angabe
 * Elias Nachbaur (01634010): Comments for version from last Wednesday, made testDistance and it's calculations
 * Florian Fusstetter (00709759): Comments for Test, Animal & Swarm, Swing, start & makeswarm, distance (keep & moving),
 * 								  testDistance debugging, restructuring into Test + 3 class pairs
 * Ignjat Karanovic (01529940): Comments for Bird, Flock, Insect & Colony, neighborhood, center for Flock, moveBird,
 * 							    testDistance debugging */