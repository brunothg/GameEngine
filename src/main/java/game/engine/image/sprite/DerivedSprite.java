package game.engine.image.sprite;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * A sprite that uses an other sprite, but limits the dimension and translate
 * the coordinates.
 * 
 * @author Marvin Bruns
 *
 */
public class DerivedSprite extends Sprite {

	private Sprite sprite;

	private int x;
	private int y;
	private int w;
	private int h;

	public DerivedSprite(Sprite s, int x, int y, int width, int height) {

		if (x < 0 || y < 0 || x + width > s.getColumns()
				|| y + height > s.getRows() || width < 0 || height < 0) {
			throw new IllegalArgumentException("Values out of bounds: "
					+ String.format("[%d,%d,%d,%d]:[0,0,%d,%d]", x, y, width,
							height, s.getColumns(), s.getRows()));
		}

		this.sprite = s;
		this.x = x;
		this.y = y;
		this.w = width;
		this.h = height;
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
	public void drawTile(Graphics2D g, int x, int y, int width, int height) {

		if (x < 0 || y < 0 || x >= getColumns() || y >= getRows()) {
			throw new ArrayIndexOutOfBoundsException(
					"Coordinates are out of bounds");
		}

		sprite.drawTile(g, x + this.x, y + this.y, width, height);
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

		if (x < 0 || y < 0 || x >= getColumns() || y >= getRows()) {
			throw new ArrayIndexOutOfBoundsException(
					"Coordinates are out of bounds");
		}

		return sprite.getTile(x + this.x, y + this.y);
	}

	/**
	 * Get the number of tiles in this sprite.
	 * 
	 */
	public int getTileCount() {

		return getRows() * getColumns();
	}

	/**
	 * Get the number of rows.
	 * 
	 */
	public int getRows() {

		return h;
	}

	/**
	 * Get the number of columns.
	 * 
	 */
	public int getColumns() {

		return w;
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
}
