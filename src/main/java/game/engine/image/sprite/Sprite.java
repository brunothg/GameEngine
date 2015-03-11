package game.engine.image.sprite;

import game.engine.image.ImageUtils;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * Class for handling sprite images.
 * 
 * @author Marvin Bruns
 *
 */
public class Sprite {

	private BufferedImage image;

	private int width;
	private int height;

	/**
	 * Create a new sprite.
	 * 
	 * @param image
	 *            The image that contains the sub-images
	 * @param width
	 *            Width of one sub-image
	 * @param height
	 *            Height of one sub-image
	 */
	public Sprite(BufferedImage image, int width, int height) {

		if (image == null) {
			throw new IllegalArgumentException("Null value not allowed");
		}

		this.image = ImageUtils.copy(image);
		this.width = width;
		this.height = height;
	}

	/**
	 * When using this constructor you'll have to override all methods.
	 */
	protected Sprite() {

		width = 0;
		height = 0;
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

		int srcX = x * (getTileWidth());
		int srcY = y * (getTileHeight());

		g.drawImage(image, 0, 0, width, height, srcX, srcY, srcX
				+ getTileWidth(), srcY + getTileHeight(), null);
	}

	/**
	 * Get a tile of this sprite. Be careful with this method. Creates a copy of
	 * the underlying tile. Otherwise the original tile would be transformed.
	 * User {@link #drawTile(Graphics2D, int, int, int, int)} for faster action.
	 * 
	 * @param x
	 *            X-Coordinate of the tile
	 * @param y
	 *            Y-Coordinate of the tile
	 * @return A {@link BufferedImage} containing the tile
	 */
	public BufferedImage getTile(int x, int y) {

		BufferedImage tile = image.getSubimage(x * getTileWidth(), y
				* getTileHeight(), getTileWidth(), getTileHeight());

		return ImageUtils.copy(tile);
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

		return image.getHeight() / getTileHeight();
	}

	/**
	 * Get the number of columns.
	 * 
	 */
	public int getColumns() {

		return image.getWidth() / getTileWidth();
	}

	/**
	 * Get the width of one tile
	 * 
	 */
	public int getTileWidth() {

		return width;
	}

	/**
	 * Get the height of one tile
	 * 
	 */
	public int getTileHeight() {

		return height;
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

		return new DerivedSprite(this, x, y, width, height);
	}
}
