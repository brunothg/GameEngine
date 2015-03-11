package game.engine.image.sprite;

import game.engine.image.ImageUtils;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

/**
 * A sprite that uses an other sprite, but transforms it using the provided {@link AffineTransform}.
 * 
 * @author Marvin Bruns
 *
 */
public class AffineTransformedSprite extends Sprite
{

	private Sprite sprite;
	private AffineTransform transform;

	public AffineTransformedSprite(Sprite sprite, AffineTransform transform)
	{

		this.sprite = sprite;
		this.transform = transform;
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
	public void drawTile(Graphics2D g, int x, int y, int width, int height)
	{

		AffineTransform transBefore = g.getTransform();

		AffineTransform tr = new AffineTransform();

		tr.concatenate(transBefore);
		tr.concatenate(transform);
		g.setTransform(tr);

		sprite.drawTile(g, x, y, width, height);
		g.setTransform(transBefore);
	}

	/**
	 * Get a tile of this sprite
	 * 
	 * @param x X-Coordinate of the tile
	 * @param y Y-Coordinate of the tile
	 * @return A {@link BufferedImage} containing the tile
	 */
	public BufferedImage getTile(int x, int y)
	{

		return ImageUtils.transform(sprite.getTile(x, y), transform);
	}

	/**
	 * Get the number of tiles in this sprite.
	 * 
	 */
	public int getTileCount()
	{

		return sprite.getTileCount();
	}

	/**
	 * Get the number of rows.
	 * 
	 */
	public int getRows()
	{

		return sprite.getRows();
	}

	/**
	 * Get the number of columns.
	 * 
	 */
	public int getColumns()
	{

		return sprite.getColumns();
	}

	/**
	 * Get the width of one tile
	 * 
	 */
	public int getTileWidth()
	{

		return sprite.getTileWidth();
	}

	/**
	 * Get the height of one tile
	 * 
	 */
	public int getTileHeight()
	{

		return sprite.getTileHeight();
	}
}
