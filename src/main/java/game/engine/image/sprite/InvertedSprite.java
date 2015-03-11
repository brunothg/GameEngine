package game.engine.image.sprite;

import game.engine.image.ImageUtils;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

/**
 * {@link Sprite} that uses another sprite and inverts the coordinates. Now the
 * first frame is the last etc.<br>
 * Mirroring is also supported.
 * 
 * @author Marvin Bruns
 *
 */
public class InvertedSprite extends Sprite {

	private Sprite sprite;

	private boolean invertX;
	private boolean invertY;

	private boolean mirrorX;
	private boolean mirrorY;

	/**
	 * 
	 * @param sprite
	 *            {@link Sprite} used for this sprite
	 * @param X
	 *            if true X-Axis is inverted
	 * @param Y
	 *            if true Y-Axis is inverted
	 * @param mirror
	 *            if true the X, Y parameters are used for mirroring. Otherwise
	 *            coordinates will be inverted.
	 */
	public InvertedSprite(Sprite sprite, boolean X, boolean Y, boolean mirror) {

		this(sprite, (mirror) ? false : X, (mirror) ? false : Y, (mirror) ? X
				: false, (mirror) ? Y : false);
	}

	/**
	 * 
	 * @param sprite
	 *            {@link Sprite} used for this sprite
	 * @param invertX
	 *            if true X-Axis is inverted
	 * @param invertY
	 *            if true Y-Axis is inverted
	 * @param mirrorX
	 *            if true the tiles will be mirrored in X direction (left-right)
	 * @param mirrorY
	 *            if true the tiles will be mirrored in Y direction (top-bottom)
	 *
	 */
	public InvertedSprite(Sprite sprite, boolean invertX, boolean invertY,
			boolean mirrorX, boolean mirrorY) {

		this.sprite = sprite;

		this.invertX = invertX;
		this.invertY = invertY;
		this.mirrorX = mirrorX;
		this.mirrorY = mirrorY;
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

		int translatedX = translateX(x);
		int translatedY = translateY(y);

		boolean mirror = isMirrorX() || isMirrorY();
		AffineTransform beforeTransform = g.getTransform();

		if (mirror) {
			double halfWidth = (width * 0.5);
			double halfHeight = (height * 0.5);

			AffineTransform transform = new AffineTransform();
			transform.concatenate(beforeTransform);
			transform.translate(halfWidth, halfHeight);

			if (isMirrorX()) {
				transform.scale(-1, 1);
			}

			if (isMirrorY()) {
				transform.scale(1, -1);
			}

			transform.translate(-halfWidth, -halfHeight);
			g.setTransform(transform);
		}

		sprite.drawTile(g, translatedX, translatedY, width, height);

		g.setTransform(beforeTransform);
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

		int translatedX = translateX(x);
		int translatedY = translateY(y);

		BufferedImage tile = sprite.getTile(translatedX, translatedY);

		if (isMirrorX() || isMirrorY()) {
			tile = ImageUtils.mirror(tile, isMirrorX(), isMirrorY());
		}

		return tile;
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

	/**
	 * Check if tiles are mirrored left-right
	 */
	public boolean isMirrorX() {

		return mirrorX;
	}

	/**
	 * Check if tiles are mirrored top-bottom
	 */
	public boolean isMirrorY() {

		return mirrorY;
	}
}
