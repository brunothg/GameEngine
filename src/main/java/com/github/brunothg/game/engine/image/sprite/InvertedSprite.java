package com.github.brunothg.game.engine.image.sprite;

import com.github.brunothg.game.engine.image.ImageUtils;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

/**
 * {@link Sprite} that uses another sprite and inverts the coordinates. Now the first frame is the
 * last etc.<br>
 * Mirroring is also supported.
 * 
 * @author Marvin Bruns
 *
 */
public class InvertedSprite implements Sprite
{

	private DefaultSprite sprite;

	private boolean invertX;
	private boolean invertY;

	private boolean mirrorX;
	private boolean mirrorY;

	/**
	 * 
	 * @param sprite {@link DefaultSprite} used for this sprite
	 * @param X if true X-Axis is inverted
	 * @param Y if true Y-Axis is inverted
	 * @param mirror if true the X, Y parameters are used for mirroring. Otherwise coordinates will
	 *        be inverted.
	 */
	public InvertedSprite(DefaultSprite sprite, boolean X, boolean Y, boolean mirror)
	{

		this(sprite, (mirror) ? false : X, (mirror) ? false : Y, (mirror) ? X : false, (mirror) ? Y : false);
	}

	/**
	 * 
	 * @param sprite {@link DefaultSprite} used for this sprite
	 * @param invertX if true X-Axis is inverted
	 * @param invertY if true Y-Axis is inverted
	 * @param mirrorX if true the tiles will be mirrored in X direction (left-right)
	 * @param mirrorY if true the tiles will be mirrored in Y direction (top-bottom)
	 *
	 */
	public InvertedSprite(DefaultSprite sprite, boolean invertX, boolean invertY, boolean mirrorX, boolean mirrorY)
	{

		this.sprite = sprite;

		this.invertX = invertX;
		this.invertY = invertY;
		this.mirrorX = mirrorX;
		this.mirrorY = mirrorY;
	}

	@Override
	public void drawTile(Graphics2D g, int x, int y, int width, int height)
	{

		int translatedX = translateX(x);
		int translatedY = translateY(y);

		boolean mirror = isMirrorX() || isMirrorY();
		AffineTransform beforeTransform = g.getTransform();

		if (mirror)
		{
			double halfWidth = (width * 0.5);
			double halfHeight = (height * 0.5);

			AffineTransform transform = new AffineTransform();
			transform.concatenate(beforeTransform);
			transform.translate(halfWidth, halfHeight);

			if (isMirrorX())
			{
				transform.scale(-1, 1);
			}

			if (isMirrorY())
			{
				transform.scale(1, -1);
			}

			transform.translate(-halfWidth, -halfHeight);
			g.setTransform(transform);
		}

		sprite.drawTile(g, translatedX, translatedY, width, height);

		g.setTransform(beforeTransform);
	}

	@Override
	public BufferedImage getTile(int x, int y)
	{

		int translatedX = translateX(x);
		int translatedY = translateY(y);

		BufferedImage tile = sprite.getTile(translatedX, translatedY);

		if (isMirrorX() || isMirrorY())
		{
			tile = ImageUtils.mirror(tile, isMirrorX(), isMirrorY());
		}

		return tile;
	}

	@Override
	public int getTileCount()
	{

		return sprite.getRows();
	}

	@Override
	public int getRows()
	{

		return sprite.getRows();
	}

	@Override
	public int getColumns()
	{

		return sprite.getColumns();
	}

	@Override
	public int getTileWidth()
	{

		return sprite.getTileWidth();
	}

	@Override
	public int getTileHeight()
	{

		return sprite.getTileHeight();
	}

	/**
	 * Don't need this overhead.
	 */
	// public Sprite getSubSprite(int x, int y, int width, int height) {
	//
	// int translatedX = translateX(x);
	// int translatedY = translateY(y);
	//
	// if (invertX) {
	// int transWidth = width - 1;
	// translatedX -= transWidth;
	// }
	//
	// if (invertY) {
	// int transHeight = height - 1;
	// translatedY -= transHeight;
	// }
	//
	// return new InvertedSprite(sprite.getSubSprite(translatedX, translatedY,
	// width, height), isInvertX(), isInvertY(), isMirrorX(),
	// isMirrorY());
	// }

	private int translateX(int x)
	{

		if (!invertX)
		{
			return x;
		}

		return (getColumns() - 1) - x;
	}

	private int translateY(int y)
	{

		if (!invertY)
		{
			return y;
		}

		return (getRows() - 1) - y;
	}

	/**
	 * Check if X-Coordinates are inverted
	 */
	public boolean isInvertX()
	{

		return invertX;
	}

	/**
	 * Check if Y-Coordinates are inverted
	 */
	public boolean isInvertY()
	{

		return invertY;
	}

	/**
	 * Check if tiles are mirrored left-right
	 */
	public boolean isMirrorX()
	{

		return mirrorX;
	}

	/**
	 * Check if tiles are mirrored top-bottom
	 */
	public boolean isMirrorY()
	{

		return mirrorY;
	}

	@Override
	public Sprite getSubSprite(int x, int y, int width, int height)
	{

		return new DerivedSprite(this, x, y, width, height);
	}
}
