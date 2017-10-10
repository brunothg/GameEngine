package com.github.brunothg.game.engine.d2.commons;

/**
 * Represents an object's size
 * 
 * @author Marvin Bruns
 *
 */
public class Size {

	private double width;
	private double height;

	public Size() {

		this(0, 0);
	}

	public Size(double width, double height) {

		this.width = width;
		this.height = height;
	}

	public int getWidth() {
		return (int) Math.round(width);
	}

	public double getPreciseWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public int getHeight() {
		return (int) Math.round(height);
	}

	public double getPreciseHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public double calculateSurface() {
		return getPreciseWidth() * getPreciseHeight();
	}

}
