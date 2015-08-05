package game.engine.image.sprite;

import game.engine.d2.stage.scene.object.Size;
import game.engine.image.Image;
import game.engine.image.ImageUtils;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * Class for handling sprite images.
 * 
 * @author Marvin Bruns
 *
 */
public class DefaultSprite implements Sprite, Image {

	private BufferedImage image;

	private int width;
	private int height;

	/**
	 * Create a new sprite. The given {@link BufferedImage} will be used as is.
	 * So consider passing a copy of your original image.
	 * 
	 * @param image
	 *            The image that contains the sub-images
	 * @param width
	 *            Width of one sub-image
	 * @param height
	 *            Height of one sub-image
	 */
	public DefaultSprite(BufferedImage image, int width, int height) {

		if (image == null) {
			throw new IllegalArgumentException("Null value not allowed");
		}

		this.image = image;
		this.width = width;
		this.height = height;
	}

	/**
	 * When using this constructor you'll have to override all methods.
	 */
	protected DefaultSprite() {

		width = 0;
		height = 0;
	}

	@Override
	public void drawTile(Graphics2D g, int x, int y, int width, int height) {

		int srcX = x * (getTileWidth());
		int srcY = y * (getTileHeight());

		g.drawImage(image, 0, 0, width, height, srcX, srcY, srcX
				+ getTileWidth(), srcY + getTileHeight(), null);
	}

	@Override
	public void draw(Graphics2D g, int width, int height) {

		g.drawImage(image, 0, 0, width, width, 0, 0, image.getWidth(),
				image.getHeight(), null);
	}

	@Override
	public BufferedImage getTile(int x, int y) {

		BufferedImage tile = image.getSubimage(x * getTileWidth(), y
				* getTileHeight(), getTileWidth(), getTileHeight());

		return ImageUtils.copy(tile);
	}

	@Override
	public Size getSize() {

		return new Size(image.getWidth(), image.getHeight());
	}

	@Override
	public int getTileCount() {

		return getRows() * getColumns();
	}

	@Override
	public int getRows() {

		return image.getHeight() / getTileHeight();
	}

	@Override
	public int getColumns() {

		return image.getWidth() / getTileWidth();
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
