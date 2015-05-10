package game.engine.stage.scene.object;

import game.engine.image.NinePatchImage;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

/**
 * 
 * A cahced version of the {@link NinePatchImageSceneObject}. Only if the
 * {@link SceneObject} is resized the backed {@link NinePatchImage} will be
 * repainted.<br>
 * Meanwhile the all drawing will be done with an image cache. Results in
 * improved speed but more memory usage.
 * 
 * @author Marvin Bruns
 *
 */
public class CachedNinePatchImageSceneObject extends NinePatchImageSceneObject {

	BufferedImage cache;

	public CachedNinePatchImageSceneObject(BufferedImage image) {

		super(image);
	}

	public CachedNinePatchImageSceneObject(NinePatchImage image) {

		super(image);
	}

	public CachedNinePatchImageSceneObject(Image image) {

		super(image);
	}

	@Override
	protected void paint(Graphics2D g, long elapsedTime) {

		if (cache == null) {
			updateCache(getWidth(), getHeight());
		}

		g.drawImage(cache, 0, 0, getWidth(), getHeight(), 0, 0,
				cache.getWidth(), cache.getHeight(), null);
	}

	private void updateCache(int width, int height) {

		if (cache == null || cache.getWidth() != width
				|| cache.getHeight() != height) {

			cache = new BufferedImage(width, height,
					BufferedImage.TYPE_INT_ARGB_PRE);
		}

		Graphics2D g = cache.createGraphics();
		super.paint(g, 0);
		g.dispose();
	}

	@Override
	public void setSize(int width, int height) {

		boolean update = false;
		if (getWidth() != width || getHeight() != height) {
			update = true;
		}

		super.setSize(width, height);

		if (update) {
			updateCache(width, height);
		}
	}

	@Override
	public void setSize(Size size) {

		boolean update = false;
		if (getWidth() != size.getWidth() || getHeight() != size.getHeight()) {
			update = true;
		}

		super.setSize(size);

		if (update) {
			updateCache(size.getWidth(), size.getHeight());
		}
	}
}
