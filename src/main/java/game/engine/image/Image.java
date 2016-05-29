package game.engine.image;

import java.awt.Graphics2D;

import game.engine.d2.commons.Size;

public interface Image {

	/**
	 * Draws the image on a given {@link Graphics2D} object
	 * 
	 * @param g
	 *            The graphics object to draw the image on
	 * @param width
	 *            The width to use for drawing
	 * @param height
	 *            The height to use for drawing
	 */
	public void draw(Graphics2D g, int width, int height);

	/**
	 * Get the original size of the image
	 * 
	 * @return The original size of the image
	 */
	public Size getSize();
}
