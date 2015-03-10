package game.engine.image.sprite;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 * {@link Sprite} that uses another sprite and inverts the coordinates. Now the
 * first frame is the last etc.
 * 
 * @author Marvin Bruns
 *
 */
public class InvertedSprite extends Sprite {

	private Sprite sprite;

	private boolean invertX;
	private boolean invertY;

	/**
	 * 
	 * @param sprite
	 *            {@link Sprite} used for this sprite
	 * @param invertX
	 *            if true X-Axis is inverted
	 * @param invertY
	 *            if true Y-Axis is inverted
	 */
	public InvertedSprite(Sprite sprite, boolean invertX, boolean invertY) {

		this.sprite = sprite;

		this.invertX = invertX;
		this.invertY = invertY;
	}

/**
	 * 
	 * Draws a tile of this sprite. The tile is drawn with {@code Bounds(0, 0,
	 * width, height).
	 * 
	 * @param g {@link Graphics} object to draw on
	 * 
	 * @param x X-Coordinate of the tile
	 * 
	 * @param y Y-Coordinate of the tile
	 * 
	 * @param width The width of the Graphics object to draw on
	 * 
	 * @param height The height of the Graphics object to draw on
	 */
	public void drawTile(Graphics g, int x, int y, int width, int height) {

		int translatedX = translateX(x);
		int translatedY = translateY(y);

		sprite.drawTile(g, translatedX, translatedY, width, height);
	}

	/**
	 * Get a tile of this sprite
	 * 
	 * @param x
	 *            X-Coordinate of the tile
	 * @param y
	 *            Y-Coordinate of the tile
	 * @return A {@link BufferedImage} containing the tile
	 */
	public BufferedImage getTile(int x, int y) {

		int translatedX = translateX(x);
		int translatedY = translateY(y);

		return sprite.getTile(translatedX, translatedY);
	}

	/**
	 * Get the number of tiles in this sprite.
	 * 
	 */
	public int getTileCount() {

		return sprite.getRows();
	}

	/**
	 * Get the number of rows.
	 * 
	 */
	public int getRows() {

		return sprite.getRows();
	}

	/**
	 * Get the number of columns.
	 * 
	 */
	public int getColumns() {

		return sprite.getColumns();
	}

	/**
	 * Get the width of one tile
	 * 
	 */
	public int getTileWidth() {

		return sprite.getTileWidth();
	}

	/**
	 * Get the height of one tile
	 * 
	 */
	public int getTileHeight() {

		return sprite.getTileHeight();
	}

	/**
	 * Get sub-sprite with given dimension.
	 * 
	 * @param x
	 *            X-Coordinate of first tile
	 * @param y
	 *            Y-Coordinate of first tile
	 * @param width
	 *            Width of the sub-sprite
	 * @param height
	 *            Height of the sub-sprite
	 * @return The sub-sprite
	 */
	public Sprite getSubSprite(int x, int y, int width, int height) {

		int translatedX = translateX(x);
		int translatedY = translateY(y);

		if (invertX) {
			int transWidth = width - 1;
			translatedX -= transWidth;
		}

		if (invertY) {
			int transHeight = height - 1;
			translatedY -= transHeight;
		}

		return sprite.getSubSprite(translatedX, translatedY, width, height);
	}

	private int translateX(int x) {

		if (!invertX) {
			return x;
		}

		return (getColumns() - 1) - x;
	}

	private int translateY(int y) {

		if (!invertY) {
			return y;
		}

		return (getRows() - 1) - y;
	}

	/**
	 * Check if X-Coordinates are inverted
	 */
	public boolean isInvertX() {

		return invertX;
	}

	/**
	 * Check if Y-Coordinates are inverted
	 */
	public boolean isInvertY() {

		return invertY;
	}
}
