package org.nic.lotto.util.engine;

import java.awt.Rectangle;
import java.util.ArrayList;

public class Particle {
	
	Vector p; // Koordinate fuer Zeichnung
	Vector v; // Geschwindigkeit
	Vector c; // Mittelpunkt
	
	float rad; // Radius
	float length;
//	int color_b;
	
	
	public Particle(Vector vp) {
		this(vp, null);
	}
	
	public Particle(Vector vp, Vector vv) {
		
		this.p = new Vector(vp.getX(), vp.getY());
		
		rad = 12;
		length = (float) (vp.getX()*vp.getX() + vp.getY()*vp.getY());
		c = new Vector(vp.getX() + rad, vp.getY() + rad);
		this.v = new Vector(vv.getX(), vv.getY());
//		color_b = (int) (Math.random()*2);
	}
	
	public void update() {
		c.setX(p.getX()+rad);
		c.setY(p.getY()+rad);
	}
	
	public Rectangle getBoundingRect() {
		return new Rectangle((int)p.getX(), (int)p.getY(), (int)rad*2, (int)rad*2);
	}
	
	public Rectangle getMask() { // 80% bounding box
		return new Rectangle((int)(p.getX()+2*rad*0.1), (int)(p.getY()+2*rad*0.1), (int)(rad*2*0.8), (int)(rad*2*0.8));
	}
	
	
	public void move(int width, int height, ArrayList<Particle> particles) {
//		System.out.println(v.getX() + ":" + v.getY());
	
		
		if (p.getY() < 50 && v.getY() < 0 || (p.getY()+(2*rad)) > height-50 && v.getY() > 0) {
			v.setY(v.getY()*-0.8); //Platizitaet
			v.setX(v.getX()*0.9); //Reibung
			if (p.getY()+(2*rad) > height-50) {
				p.setY(height-50-2*rad);
			}
			if(p.getY() < 50)
			{
				p.setY(50);
			}
		}
//		System.out.println(p.getY());
		
		if (p.getX() < 50 && v.getX() < 0 || (p.getX()+(2*rad)) > width-50 && v.getX() > 0) {
			v.setX(v.getX()*-0.9);
			v.setY(v.getY()*0.8); //Platizitaet
//			if (color_b == 1) {
//				color_b = 0;
//			} else {
//				color_b = 1;
//			}
			if((p.getX()+(2*rad)) > width-50)
			{
				p.setX(width-50-2*rad);
			}
			if(p.getX() < 50)
			{
				p.setX(50);
			}
		}
		
			for (Particle par : particles) {
				if (par != this) {
					
					double mg = Math.sqrt((c.getX()-par.c.getX())*(c.getX()-par.c.getX())
								+(c.getY()-par.c.getY())*(c.getY()-par.c.getY()));
//					System.out.println(mg);
					double pen = mg - (2*rad);
					
					if (pen <= 0 && mg != 0) {
//						System.out.println("kontakt");
						// Normalvektor zwischen part und par
						Vector N = new Vector((c.getX()-par.c.getX())/mg, (c.getY()-par.c.getY())/mg);
//						System.out.println(N.getX() + ":" + N.getY());
						// penetration vector 
						Vector P = new Vector(N.getX()*pen, N.getY()*pen);
						
//						Collision response:
//						a: "loesen" der Kollision durch Rueckverschiebung entlang des Penetrationsvektors P
						p.setX(p.getX() + -P.getX());
						p.setY(p.getY() + -P.getY());
						
//						b: Ausfallsvektor = (2 mal) Normal + Einfallsvektor
//						System.out.println("----------------\n" + v.getX() + ":" + v.getY());
						v.setX((v.getX() + N.getX()*0.5)*0.9); //2*N
						v.setY((v.getY() + N.getY()*0.5)*0.9); // 2*N
//						System.out.println(v.getX() + ":" + v.getY());
						
//						if (color_b == 1) {
//							color_b = 0;
//						} else {
//							color_b = 1;
//						}
//						if (par.color_b == 1) {
//							par.color_b = 0;
//						} else {
//							par.color_b = 1;
//						}
					}
				}
			
		}
			
			
			v.setY(v.getY()); //+ 9.81*60/5000);
			p.setX(p.getX() + v.getX());
			p.setY(p.getY() + (v.getY()));

		
		
	}

}
