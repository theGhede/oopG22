package oopG22;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.util.concurrent.TimeUnit;
import javax.swing.*;

public class Test extends JPanel implements ActionListener {

	Timer t = new Timer(4, this);

	public static String typeToDraw;

	public static Swarm regular = new Swarm();
	public static Flock birds = new Flock();
	public static Colony insects = new Colony();

	public static double dangerX = (double) Math.round((100 + Math.random() * 80) * 100) / 100;
	public static double dangerY = (double) Math.round((400 + Math.random() * 150) * 100) / 100;

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
