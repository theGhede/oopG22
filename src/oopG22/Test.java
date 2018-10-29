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
	// TODO: find a way for this to not be necessary for paintComponent(g)  
	public static Swarm regular = new Swarm();
	public static Flock birds = new Flock();
	public static Colony insects = new Colony();

	// these coordinates for the danger to our birds needs to be set before
	// paintComponent can be called
	public static double dangerX = (450 + Math.random() * 100);
	public static double dangerY = (120 + Math.random() * 70);

	// draw graphics using paint(g) with Graphics2D for double variables
	// issue: can't pass other parameters ... like the Swarm or type etc
	public void paintComponent(Graphics g) {
		// use this to draw the initial swarm via for loop as dots
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		// TODO: fix drawing & beware of out of bounds exceptions
		// different sizes for different animals
		if (typeToDraw == "Animal") {
			//System.out.println("painting animals");
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
			//System.out.println("painting birds");
			Shape d = new Ellipse2D.Double(dangerX, dangerY, 8, 8);
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
		validate();
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

		// TODO: proper starting & testing
		regular.makeswarm("Animal", 320, 12);
		System.out.println("made animal swarm");
		typeToDraw = regular.type;
		System.out.println("type: "+typeToDraw);
		regular.start();
		System.out.println("started");
		TimeUnit.SECONDS.sleep(8);

		birds.makeswarm("Bird", 240, 12);
		System.out.println("made bird swarm");
		typeToDraw = birds.type;
		System.out.println("type: "+typeToDraw);
		birds.start(dangerX, dangerY);
		System.out.println("started");
		TimeUnit.SECONDS.sleep(8);

		insects.makeswarm("Insect", 10000, 0);
		System.out.println("made insect swarm");
		typeToDraw = insects.type;
		birds.start();
		System.out.println("started");
		System.out.println("type: "+typeToDraw);
	}
}
