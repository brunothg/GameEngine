package com.github.brunothg.game.engine.d2.commons;

/**
 * 
 * Represents a 2D coordinate.
 * 
 * @author Marvin Bruns
 *
 */
public class Point
{

	private int x;
	private int y;

	public Point()
	{

		this(0, 0);
	}

	public Point(int x, int y)
	{
		this.setX(x);
		this.setY(y);
	}

	public int getX()
	{
		return x;
	}

	public void setX(int x)
	{
		this.x = x;
	}

	public int getY()
	{
		return y;
	}

	public void setY(int y)
	{
		this.y = y;
	}

	public double getVerticalDistanceTo(Point p)
	{
		double vDistance = 0;

		vDistance = Math.abs(getY() - p.getY());

		return vDistance;
	}

	public double getHorizontalDistanceTo(Point p)
	{
		double hDistance = 0;

		hDistance = Math.abs(getX() - p.getX());

		return hDistance;
	}

	public double getDistanceTo(Point p)
	{
		double distance = 0;

		distance = Math.sqrt(Math.pow(getHorizontalDistanceTo(p), 2) + Math.pow(getVerticalDistanceTo(p), 2));

		return distance;
	}
}
