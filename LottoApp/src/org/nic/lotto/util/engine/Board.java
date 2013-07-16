package org.nic.lotto.util.engine;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Board extends JPanel implements ActionListener{

	
	private static final long serialVersionUID = 1L;
	
	Timer timer;
	JFrame frame;
	Vector ul;
	Vector ur;
	Vector ll;
	Vector lr;
	
	ArrayList<Particle> particles = new ArrayList<Particle>();
	
	
	
	public Board() {
		frame = new JFrame("WTF");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(500, 522);
		frame.setVisible(true);
		frame.setResizable(false);
		frame.add(BorderLayout.CENTER, this);
		
		setSize(500, 500);
		
		timer = new Timer(5, this);
		timer.start();
		
//		ul = new Vector(this.getHeight()*0.1, this.getWidth()*0.1);
//		ur = new Vector(this.getHeight()*0.9, this.getWidth()*0.1);
//		ll = new Vector(this.getHeight()*0.1, this.getWidth()*0.9);
//		lr = new Vector(this.getHeight()*0.9, this.getWidth()*0.9);
//		
		
		for (int i = 0; i < 49; i++) {
			
			int px = (int) ((Math.random() * 300)+100);
			int py = (int) ((Math.random() * 300)+100);
			double vx = (Math.random()*3);
			double vy = (Math.random()*3);
			
			Particle par = new Particle(new Vector(px, py), new Vector(vx, vy));
			particles.add(par);
//			System.out.println(par.p.getX() + ":" + par.p.getY());
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
//		System.out.println(particles.get(1).v.getY() + ":" + particles.get(1).v.getX());
		for (Particle par : particles) {
			par.update();
			par.move(this.getWidth(), this.getHeight(), particles);
		}
		repaint();
		
	}
	
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.BLACK);
		g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
		
		g2d.setColor(Color.WHITE);
		g2d.setStroke(new BasicStroke(5));	
//		g2d.drawLine((int)ul.getX(), (int)ul.getY(), (int)ur.getX(), (int)ur.getY());
//		g2d.drawLine((int)ur.getX(), (int)ur.getY(), (int)lr.getX(), (int)lr.getY());
//		g2d.drawLine((int)lr.getX(), (int)lr.getY(), (int)ll.getX(), (int)ll.getY());
//		g2d.drawLine((int)ll.getX(), (int)ll.getY(), (int)ul.getX(), (int)ul.getY());
		g2d.drawLine(50, 50, 450, 50);
		g2d.drawLine(450, 50, 450, 450);
		g2d.drawLine(450, 450, 50, 450);
		g2d.drawLine(50, 450, 50, 50);
		
		for (Particle par : particles) {
			
			
//			if (par.color_b == 1) {
//				g2d.setColor(Color.RED);
//			} else {
//				g2d.setColor(Color.WHITE);
//			}
//			System.out.println(par.color_b);
			g2d.fillOval((int)par.p.getX(), (int)par.p.getY(), (int)(par.rad*2), (int)(par.rad*2));
		}
		
	}
	
	

}
