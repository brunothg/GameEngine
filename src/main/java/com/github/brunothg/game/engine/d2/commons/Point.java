package com.github.brunothg.game.engine.d2.commons;

/**
 * 
 * Represents a 2D coordinate.
 * 
 * @author Marvin Bruns
 *
 */
public class Point {

	private double x;
	private double y;

	public Point() {

		this(0, 0);
	}

	public Point(double x, double y) {
		this.setX(x);
		this.setY(y);
	}

	public int getX() {
		return (int) Math.round(x);
	}

	public double getPreciseX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public int getY() {
		return (int) Math.round(y);
	}

	public double getPreciseY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getVerticalDistanceTo(Point p) {
		double vDistance = 0;

		vDistance = Math.abs(getPreciseY() - p.getPreciseY());

		return vDistance;
	}

	public double getHorizontalDistanceTo(Point p) {
		double hDistance = 0;

		hDistance = Math.abs(getPreciseX() - p.getPreciseX());

		return hDistance;
	}

	public double getDistanceTo(Point p) {
		double distance = 0;

		distance = Math.sqrt(Math.pow(getHorizontalDistanceTo(p), 2) + Math.pow(getVerticalDistanceTo(p), 2));

		return distance;
	}
}
