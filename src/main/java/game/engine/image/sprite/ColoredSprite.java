package game.engine.image.sprite;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import game.engine.image.ImageUtils;
import game.engine.image.sprite.Sprite;

/**
 * Sprite that overrides the color of the provided {@link Sprite}.
 * 
 * @author Marvin Bruns
 *
 */
public class ColoredSprite implements Sprite {

	private Sprite sprite;
	private Color c;

	public ColoredSprite(Sprite sprite, Color c) {

		this.sprite = sprite;
		this.c = c;
	}

	@Override
	public void drawTile(Graphics2D g, int x, int y, int width, int height) {

		BufferedImage img = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_ARGB_PRE);

		Graphics2D g2d = img.createGraphics();
		sprite.drawTile(g2d, x, y, img.getWidth(), img.getHeight());
		g2d.dispose();

		img = ImageUtils.colorize(img, getColor());

		g.drawImage(img, 0, 0, width, height, 0, 0, img.getWidth(),
				img.getHeight(), null);
	}

	@Override
	public BufferedImage getTile(int x, int y) {

		return ImageUtils.colorize(sprite.getTile(x, y), getColor());
	}

	@Override
	public int getColumns() {

		return sprite.getColumns();
	}

	@Override
	public int getRows() {

		return sprite.getRows();
	}

	@Override
	public Sprite getSubSprite(int x, int y, int width, int height) {

		return new DerivedSprite(this, x, y, width, height);
	}

	@Override
	public int getTileCount() {

		return sprite.getTileCount();
	}

	@Override
	public int getTileHeight() {

		return sprite.getTileHeight();
	}

	@Override
	public int getTileWidth() {

		return sprite.getTileWidth();
	}

	public Color getColor() {

		return this.c;
	}

}
