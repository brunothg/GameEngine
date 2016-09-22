package com.github.brunothg.game.engine.image.sprite;

import com.github.brunothg.game.engine.image.ImageUtils;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.lang.ref.WeakReference;

/**
 * This implementation of {@link Sprite} caches tiles with {@link WeakReference} in order to find a
 * balance between time and memory
 * 
 * @author Marvin Bruns
 *
 */
public class WeakSprite extends DefaultSprite
{

	WeakReference<?>[][] tiles;

	/**
	 * Create a new sprite. The given {@link BufferedImage} will be used as is. So consider passing
	 * a copy of your original image. Calls to {@link #getTile(int, int)} will be buffered. So
	 * changes maybe ignored or delayed. Calls to {@link #drawTile(Graphics2D, int, int, int, int)}
	 * won't be buffered.
	 * 
	 * @param image The image that contains the sub-images
	 * @param width Width of one sub-image
	 * @param height Height of one sub-image
	 */
	public WeakSprite(BufferedImage image, int width, int height)
	{

		super(image, width, height);

		tiles = new WeakReference<?>[getColumns()][getRows()];
	}

	@Override
	public BufferedImage getTile(int x, int y)
	{

		WeakReference<?> weakTile = tiles[x][y];
		Object tile = null;

		if (weakTile != null && (tile = weakTile.get()) != null)
		{

			return ImageUtils.copy((BufferedImage) tile);
		}

		BufferedImage btile = super.getTile(x, y);
		tiles[x][y] = new WeakReference<BufferedImage>(btile);

		return ImageUtils.copy(btile);
	}

	@Override
	public Sprite getSubSprite(int x, int y, int width, int height)
	{

		return new DerivedSprite(this, x, y, width, height);
	}
}
