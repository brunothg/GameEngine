package com.github.brunothg.game.engine.d2.commons;

/**
 * Represents an object's size
 * 
 * @author Marvin Bruns
 *
 */
public class Size
{

	private int width;
	private int height;

	public Size()
	{

		this(0, 0);
	}

	public Size(int width, int height)
	{

		this.width = width;
		this.height = height;
	}

	public int getWidth()
	{
		return width;
	}

	public void setWidth(int width)
	{
		this.width = width;
	}

	public int getHeight()
	{
		return height;
	}

	public void setHeight(int height)
	{
		this.height = height;
	}

}