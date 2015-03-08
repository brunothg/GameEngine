package game.engine.image;

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

		this.image = image;
		this.width = width;
		this.height = height;
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

		int srcX = x * (getTileWidth());
		int srcY = y * (getTileHeight());

		g.drawImage(image, 0, 0, width, height, srcX, srcY, srcX
				+ getTileWidth(), srcY + getTileHeight(), null);
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

		BufferedImage tile = new BufferedImage(getTileWidth(), getTileHeight(),
				BufferedImage.TYPE_INT_ARGB_PRE);

		Graphics2D g2d = tile.createGraphics();
		drawTile(g2d, x, y, tile.getWidth(), tile.getHeight());

		return tile;
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
}
