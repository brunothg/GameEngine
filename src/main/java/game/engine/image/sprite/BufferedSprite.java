package game.engine.image.sprite;

import game.engine.image.ImageUtils;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * A {@link Sprite} that initially divides the provided image into sub-images.
 * First creation is slower and consumes more memory than the unbuffered Sprite,
 * but calls to get/drawTile are much faster.
 * 
 * @author Marvin Bruns
 *
 */
public class BufferedSprite implements Sprite {

	private BufferedImage[][] tiles;

	private int width;
	private int height;

	/**
	 * 
	 * Creates a new {@link BufferedSprite}. The given {@link BufferedImage} is
	 * copied.
	 */
	public BufferedSprite(BufferedImage image, int width, int height) {
		super();

		this.width = width;
		this.height = height;

		tiles = new BufferedImage[image.getWidth() / width][image.getHeight()
				/ height];

		for (int x = 0; x < tiles.length; x++) {
			for (int y = 0; y < tiles[x].length; y++) {

				tiles[x][y] = ImageUtils.copy(image.getSubimage(x * width,
						y * height, width, height));
			}
		}
	}

	/**
	 * 
	 * Draws a tile of this sprite. The tile is drawn with {@code Bounds(0, 0,
	 * width, height)}.
	 * 
	 * @param g
	 *            {@link Graphics} object to draw on
	 * 
	 * @param x
	 *            X-Coordinate of the tile
	 * 
	 * @param y
	 *            Y-Coordinate of the tile
	 * 
	 * @param width
	 *            The width of the Graphics object to draw on
	 * 
	 * @param height
	 *            The height of the Graphics object to draw on
	 */
	public void drawTile(Graphics2D g, int x, int y, int width, int height) {

		BufferedImage tile = getTile(x, y);

		g.drawImage(tile, 0, 0, width, height, 0, 0, tile.getWidth(),
				tile.getHeight(), null);
	}

	/**
	 * Get a tile of this sprite. Creates a copy of the underlying tile.
	 * Otherwise the original tile would be transformed. User
	 * {@link #drawTile(Graphics2D, int, int, int, int)} for faster action.
	 * 
	 * @param x
	 *            X-Coordinate of the tile
	 * @param y
	 *            Y-Coordinate of the tile
	 * @return A {@link BufferedImage} containing the tile
	 */
	public BufferedImage getTile(int x, int y) {

		return ImageUtils.copy(tiles[x][y]);
	}

	@Override
	public int getTileCount() {

		return getRows() * getColumns();
	}

	@Override
	public int getRows() {

		return tiles.length;
	}

	@Override
	public int getColumns() {

		return tiles[0].length;
	}

	@Override
	public int getTileWidth() {

		return width;
	}

	@Override
	public int getTileHeight() {

		return height;
	}

	@Override
	public Sprite getSubSprite(int x, int y, int width, int height) {

		return new DerivedSprite(this, x, y, width, height);
	}
}
