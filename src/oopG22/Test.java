package oopG22;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.util.concurrent.TimeUnit;
import javax.swing.*;
/*
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException; */

public class Test extends JPanel implements ActionListener {

	// update by repaint every 4 milliseconds (==> after the intervals found in
	// main)
	Timer t = new Timer(4, this);

	public static String typeToDraw;

	// these are public so paintComponent(g) can see them & static because the
	// swarms are required for starting the simulations in main
	public static Swarm regular = new Swarm();
	public static Flock birds = new Flock();
	public static Colony insects = new Colony();

	// these coordinates for the danger to our birds needs to be set before
	// paintComponent can be called
	//public static double dangerX = (120 + Math.random() * 70);
	//public static double dangerY = (450 + Math.random() * 100);
	public static double dangerX = (double) Math.round((100 + Math.random() * 80) * 100) / 100;
	public static double dangerY = (double) Math.round((400 + Math.random() * 150) * 100) / 100;

	// draw graphics using paint(g) with Graphics2D for double variables
	// issue: can't pass other parameters ... like the Swarm or type etc
	public void paintComponent(Graphics g) {
		// use this to draw the initial swarm via for loop as dots
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		// different sizes for different animals
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
		// start Timer t
		t.start();
	}

	public void actionPerformed(ActionEvent e) {
		revalidate();
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

	public static void main(String[] args) throws InterruptedException {

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				GUI();
			}
		});
		
		/* NOTE: we are aware that setting and using swarm.type can be replaced with hasClass(),
		 * 		 in our mind this method simply is more appearant and readable and checks itself */
		regular.makeswarm("Animal", 320, 12);
		typeToDraw = regular.type;
		regular.start();
		TimeUnit.SECONDS.sleep(4);

		birds.makeswarm("Bird", 240, 14);
		typeToDraw = birds.type;
		birds.start(dangerX, dangerY);
		TimeUnit.SECONDS.sleep(4);

		insects.makeswarm("Insect", 10000, 0);
		typeToDraw = insects.type;
		insects.start();
	}
}
