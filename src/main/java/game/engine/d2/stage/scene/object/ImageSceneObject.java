package game.engine.d2.stage.scene.object;

import game.engine.image.ImageUtils;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

/**
 * A {@link SceneObject} that consists of an image. The default origin is set to
 * Point(0,0). The size is set to the image's size. The image will be scaled to
 * fit the size of this object.
 * 
 * @author Marvin Bruns
 *
 */
public class ImageSceneObject extends SceneObject {

	private BufferedImage image;

	/**
	 * Create a new {@link SceneObject} that displays an image. The origin is
	 * set to P(0,0). The size is set to the image's size.
	 * 
	 * @param image
	 *            Shown image
	 */
	public ImageSceneObject(BufferedImage image) {

		setImage(image);
	}

	/**
	 * @see #ImageSceneObject(BufferedImage)
	 */
	public ImageSceneObject(Image image) {

		this(ImageUtils.BufferedImage(image));
	}

	@Override
	protected void paint(Graphics2D g, long elapsedTime) {

		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
				RenderingHints.VALUE_INTERPOLATION_BICUBIC);

		BufferedImage image = getImage();

		int width = image.getWidth();
		int height = image.getHeight();

		g.drawImage(image, 0, 0, getWidth(), getHeight(), 0, 0, width, height,
				null);
	}

	@Override
	public Point getOrigin() {

		return ORIGIN_TOP_LEFT;
	}

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {

		if (image == null) {
			throw new IllegalArgumentException("Null value not allowed");
		}

		this.image = image;
		setSize(this.image.getWidth(), this.image.getHeight());
	}

}
