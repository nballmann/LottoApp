package org.nic.lotto.util.engine;

public class Vector {

	private double x;
	private double y;
	private double length;
	private boolean isNormalized = false;
	
	public Vector(double posA_x, double posA_y, double posB_x, double posB_y) {
		
		x = posA_x + -posB_x;
		y = posA_x + -posB_x;
		
		setLength(Math.sqrt(x*x+y*y));	
	}
	
	public Vector(double x, double y) {
		
		this.x = x;
		this.y = y;
	}
	
	public Vector(double x, double y, double length) {
		
		this.x = x;
		this.y = y;
		this.length = length;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}
	
	public void setX(double x) {
		this.x = x;
	}
	
	public void setY(double y) {
		this.y = y;
	}

	public double getLength() {
		return length;
	}

	public void setLength(double length) {
		
		if (length != 1) {
			isNormalized = false;
		}
		this.length = length;
	}
	
	public boolean isNormalized() {
		return isNormalized;
	}
	
	public void normalize() {
		setLength(Math.sqrt(x*x+y*y));
		
		x /= length;
		y /= length;
		
		isNormalized = true;
		setLength(1);
	}
	
	public double getDotProduct(Vector b) {
		
		return x*b.getX() + y*b.getY();
	}
	
	public Vector getProjVector(Vector b) {
		
		double proj_x = (getDotProduct(b) / 
						(b.getX()*b.getX() + b.getY()*b.getY()) * b.getX());
		double proj_y = (getDotProduct(b) / 
						(b.getX()*b.getX() + b.getY()*b.getY()) * b.getY());
		
		return new Vector(proj_x, proj_y);
	}
	
//	gibt die "rechte" Senkrechte auf den Vector zurueck
	public Vector getNormal() {
		
		double n_x = -y;
		double n_y =  x;
		
		return new Vector(n_x, n_y);
	}
}
