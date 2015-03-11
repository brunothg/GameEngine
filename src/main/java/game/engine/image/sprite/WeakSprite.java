package game.engine.image.sprite;

import game.engine.image.ImageUtils;

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
public class WeakSprite extends Sprite
{

	WeakReference<?>[][] tiles;

	/**
	 * Create a new sprite.
	 * 
	 * @param image The image that contains the sub-images
	 * @param width Width of one sub-image
	 * @param height Height of one sub-image
	 */
	public WeakSprite(BufferedImage image, int width, int height)
	{

		super(ImageUtils.copy(image), width, height);

		tiles = new WeakReference<?>[getColumns()][getRows()];
	}

	@Override
	public void drawTile(Graphics2D g, int x, int y, int width, int height)
	{

		WeakReference<?> weakTile = tiles[x][y];
		Object tile = null;

		if (weakTile != null && (tile = weakTile.get()) != null)
		{

			BufferedImage btile = (BufferedImage) tile;
			g.drawImage(btile, 0, 0, width, height, 0, 0, btile.getWidth(), btile.getHeight(), null);

			return;
		}

		super.drawTile(g, x, y, width, height);
	}

	@Override
	public BufferedImage getTile(int x, int y)
	{

		WeakReference<?> weakTile = tiles[x][y];
		Object tile = null;

		if (weakTile != null && (tile = weakTile.get()) != null)
		{

			return (BufferedImage) tile;
		}

		BufferedImage btile = super.getTile(x, y);
		tiles[x][y] = new WeakReference<BufferedImage>(btile);

		return btile;
	}
}
