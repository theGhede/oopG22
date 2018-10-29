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


public class Test extends JPanel implements ActionListener {

	// update by repaint every 4 milliseconds (==> after the intervals found in main)
	Timer t = new Timer(4, this);
	
	public static String typeToDraw;
	
	// these are public so paintComponent(g) can see them & static because the swarms are required for starting the simulations in main
	public static Swarm regular = new Swarm();
	public static Flock birds = new Flock();
	public static Colony insects = new Colony();
	
	// these coordinates for the danger to our birds needs to be set before paintComponent can be called
	public static double dangerX = (450+Math.random()*100);
	public static double dangerY = (120+Math.random()*70);
	// draw graphics using paint(g) with Graphics2D for double variables
	// issue: can't pass other parameters ... like the Swarm or type etc
	public void paintComponent(Graphics g) {
		// use this to draw the initial swarm via for loop as dots
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		// TODO: fix drawing & beware of out of bounds exceptions
		// different sizes for different animals
		if (typeToDraw == "Animal") {
			for (int i = 0; i < regular.swarm.length; i++) {
			double x = regular.swarm[i].xcoord;
			double y = regular.swarm[i].ycoord;
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
			for (int i = 0; i < birds.swarm.length; i++) {
				double x = birds.swarm[i].xcoord;
				double y = birds.swarm[i].ycoord;
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
			for (int i = 0; i < insects.swarm.length; i++) {
				double x = insects.swarm[i].xcoord;
				double y = insects.swarm[i].ycoord;
	            Shape s = new Ellipse2D.Double(x, y, 2, 2);

				if (i % 3 == 0){
	                g2d.setPaint(Color.gray);
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
	
	// method to start up prebuilt simulations
	public static void start(Swarm s, int size, int minD, String type) {
		
		s.makeswarm(type, size, minD);
		
		// all bird swarms behave the same ... the rules of movement are predetermined
		if (s.type == "Animal") {
			s.makeswarm(type, size, minD);
			s.select = (int) (Math.random() * s.swarm.length-1);
			double a = 4 + Math.random() * 14;
			double b = 8 + Math.random() * 8;
			for (int i = 0; i < 10; i++) {
				s.swarm[s.select].moveAnimal(a, 0, 0, b);
				s.resetMoved();
			}
			
			s.resetMoved();
			s.select = (int) (Math.random() * s.swarm.length-1);
			// diagonal movement - works with NO, SO, SW, NO only
			for (int i = 0; i < 200/4; i++) {
				s.swarm[s.select].moveAnimal(0, 8, 8, 0);
				s.resetMoved();
			}
		}
		if (s.type == "Bird") {
			// stressed & tired
			for (int i = 0; i < s.swarm.length; i++) {
				s.swarm[i].stressed = true;
				s.swarm[i].danger(dangerX, dangerY);
			}
			int closest = 0;
			double max = 0, xdist, ydist, dist = 0;
			for (int i = 0; i < s.swarm.length; i++) {
				xdist = Math.pow((s.swarm[i].xcoord - dangerX), 2);
				ydist = Math.pow((s.swarm[i].ycoord - dangerY), 2);
				dist = Math.sqrt(xdist + ydist);
				if (dist > max) {
					closest = i;
					max = dist;
				}
			}
			// the closest bird to danger starts fleeing
			s.select = closest;
			double x = s.swarm[closest].xcoord;
			double y = s.swarm[closest].ycoord;
			// moveRight = x1, moveLeft = x2, moveUp = y1, moveDown = y2
			// danger is east & south or equal
			if (x >= dangerX && y >= dangerY) {
				for (int i = 0; i < 20; i++) {
					s.swarm[s.select].moveAnimal(90 - dist, 0, 90 - dist, 0);
					s.resetMoved();
				}	
			}
			if (x < dangerX && y > dangerY) {
				for (int i = 0; i < 20; i++) {
					s.swarm[s.select].moveAnimal(0, 90 - dist, 90 - dist, 0);
					s.resetMoved();
				}	
			}
			if (x > dangerX && y < dangerY) {
				for (int i = 0; i < 20; i++) {
					s.swarm[s.select].moveAnimal(90 - dist, 0, 0, 90 - dist);
					s.resetMoved();
				}	
			}
			if (x < dangerX && y < dangerY) {
				for (int i = 0; i < 20; i++) {
					s.swarm[s.select].moveAnimal(0, 90 - dist, 0, 90 - dist);
					s.resetMoved();
				}	
			}
			
			double[] center = new double [2];
			double averageX = 0, averageY = 0;
			for (int i = 0; i < s.swarm.length; i++) {
				// center & tired movement
				s.swarm[i].modifier = 1;
				averageX += s.swarm[i].xcoord;
				averageY += s.swarm[i].ycoord;
				averageX = averageX / s.swarm.length;
				averageY = averageY / s.swarm.length;
				center[0] = averageX;
				center[1] = averageY;
			}
			for (int i = 0; i < s.swarm.length; i++) {
				((Bird) s.swarm[i]).recenter(center);
			}
		}
		// instead of neighbors these follow a leader and home in on his path
		if (s.type == "Insect") {

			int leader = 0;
			double xs = 0, max = 0;
			for (int i = 0; i < s.swarm.length; i++) {
				s.swarm[i].follower = true;
				xs = s.swarm[i].xcoord;
				if (xs > max) {
					max = xs;
					leader = i;
				}
			}
			s.swarm[leader].follower = false;
			for (int i = 0; i < s.swarm.length; i++) {
				for (int j = 0; j < 70; j++) {
					if(s.swarm[i].follower == false) {
						s.swarm[i].lane(11, 0);
					}
					if(s.swarm[i].follower == true) {
						s.swarm[i].lane(10, s.swarm[i].ycoord-s.swarm[leader].ycoord);
					}
				}	
			}
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		
		// Run GUI
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				GUI();
			}
		});
	
		start(regular, 260, 12, "Animal");
		typeToDraw = regular.type;
		TimeUnit.SECONDS.sleep(8);
		
		start(birds, 260, 12, "Bird");
		typeToDraw = birds.type;
		TimeUnit.SECONDS.sleep(8);
		
		start(insects, 10000, 0, "Insect");
		typeToDraw = insects.type;
	}
}
