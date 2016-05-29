package game.engine.image;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.ImageObserver;
import java.awt.image.WritableRaster;

import javax.swing.ImageIcon;

/**
 * Utility class for images.
 * 
 * @author Marvin Bruns
 *
 */
public class ImageUtils {

	public static final Color COLOR_TRANSPARENT = new Color(0, 0, 0, 0);

	/**
	 * Uses {@link BufferedImage#TYPE_INT_ARGB}
	 * 
	 * @see #BufferedImage(Image, int)
	 */
	public static BufferedImage BufferedImage(Image img) {

		return BufferedImage(img, BufferedImage.TYPE_INT_ARGB);
	}

	/**
	 * Uses {@link ImageIcon} to ensure that the image is completely loaded.
	 * 
	 * @see #BufferedImage(Image, ImageObserver, int)
	 */
	public static BufferedImage BufferedImage(Image img, int type) {

		ImageIcon imgIc = new ImageIcon(img);

		return BufferedImage(imgIc.getImage(), null, type);
	}

	/**
	 * Convert a {@link Image} in a {@link BufferedImage}
	 * 
	 * @param img
	 *            Image to convert
	 * @param observer
	 *            {@link ImageObserver}
	 * @param type
	 *            {@link BufferedImage#TYPE_INT_ARGB} etc.
	 * @return the img as BufferedImage
	 */
	public static BufferedImage BufferedImage(Image img, ImageObserver observer,
			int type) {

		if (img instanceof BufferedImage) {
			return (BufferedImage) img;
		}

		int width = img.getWidth(observer);
		int height = img.getHeight(observer);

		BufferedImage bimg = new BufferedImage(width, height, type);

		Graphics2D graphics = bimg.createGraphics();
		graphics.drawImage(img, 0, 0, width, height, 0, 0, width, height,
				observer);

		graphics.dispose();

		return bimg;
	}

	/**
	 * Uses {@link BufferedImage#TYPE_INT_ARGB}
	 * 
	 * @see #getScaledInstance(Image, int, int, int, ImageObserver)
	 */
	public static BufferedImage getScaledInstance(Image img, int width,
			int height, ImageObserver observer) {

		return getScaledInstance(img, width, height,
				BufferedImage.TYPE_INT_ARGB, observer);
	}

	/**
	 * Get a scaled version of an image
	 * 
	 * @param img
	 *            The image to scale
	 * @param width
	 *            New width
	 * @param height
	 *            New height
	 * @param type
	 *            The image type e.g. {@link BufferedImage#TYPE_INT_ARGB}
	 * @param observer
	 *            {@link ImageObserver}
	 */
	public static BufferedImage getScaledInstance(Image img, int width,
			int height, int type, ImageObserver observer) {
		BufferedImage bimg = new BufferedImage(width, height, type);

		Graphics2D graphics = bimg.createGraphics();
		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
				RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		graphics.drawImage(img, 0, 0, width, height, 0, 0,
				img.getWidth(observer), img.getHeight(observer), observer);

		graphics.dispose();

		return bimg;
	}

	/**
	 * Clear a {@link BufferedImage} with the given color.
	 * 
	 * @see #clearImage(Graphics2D, int, int, Color)
	 * 
	 * @param img
	 *            Image to be cleared
	 * @param clear
	 *            Color used for clearing this object. Only this color will be
	 *            present
	 */
	public static void clearImage(BufferedImage img, Color clear) {

		Graphics2D g2d = img.createGraphics();
		clearImage(g2d, img.getWidth(), img.getHeight(), clear);

		g2d.dispose();
	}

	/**
	 * Clears a {@link Graphics2D} object with the given Color. Alpha values
	 * will replace the current alpha. The resulting {@link Graphics2D} object
	 * will be transparent.
	 * 
	 * @param g2d
	 *            {@link Graphics2D} object to be cleared
	 * @param width
	 *            Width of the area
	 * @param height
	 *            Height of the area
	 * @param clear
	 *            Color used for clearing this object. Only this color will be
	 *            present
	 */
	public static void clearImage(Graphics2D g2d, int width, int height,
			Color clear) {

		Color beforeColor = g2d.getColor();
		Composite beforeComposite = g2d.getComposite();

		g2d.setComposite(AlphaComposite.Src);
		g2d.setColor(clear);
		g2d.fillRect(0, 0, width, height);

		g2d.setComposite(beforeComposite);
		g2d.setColor(beforeColor);
	}

	/**
	 * Converts a {@link Shape} into a {@link BufferedImage}
	 * 
	 * @param shape
	 *            Shape do be drawn
	 * @param shapeColor
	 *            Color in which the shape will be drawn
	 * @param fill
	 *            if true the shape will be filled in. Otherwise only the
	 *            outlines will be painted.
	 * @return The shape as image
	 */
	public static BufferedImage shapeToImage(Shape shape, Color shapeColor,
			boolean fill) {

		if (shape == null) {
			return null;
		}

		Rectangle bounds = shape.getBounds();
		int width = bounds.x + bounds.width;
		int height = bounds.y + bounds.height;

		if (width <= 0 || height <= 0) {
			return null;
		}

		BufferedImage img = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_ARGB_PRE);

		Graphics2D g2d = img.createGraphics();
		clearImage(g2d, width, height, COLOR_TRANSPARENT);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		g2d.setColor(shapeColor);

		if (fill) {

			g2d.fill(shape);
		} else {

			g2d.draw(shape);
		}

		g2d.dispose();

		return img;
	}

	/**
	 * Create a deep copy of an {@link BufferedImage}. Properties will be
	 * dismissed. May fail.
	 * 
	 * @param image
	 *            Image to be copied
	 * @return Copied image
	 */
	public static BufferedImage deepCopy(BufferedImage image) {

		BufferedImage copy;
		try {
			ColorModel cm = image.getColorModel();
			boolean alphaPremultiplied = image.isAlphaPremultiplied();
			WritableRaster raster = image.copyData(null);

			copy = new BufferedImage(cm, raster, alphaPremultiplied, null);
		} catch (Exception e) {
			return copy(image);
		}

		return copy;
	}

	/**
	 * Create a copy of an {@link BufferedImage}.
	 * 
	 * @param image
	 *            Image to be copied
	 * @return Copied image
	 */
	public static BufferedImage copy(BufferedImage image) {

		BufferedImage copy = new BufferedImage(image.getWidth(),
				image.getHeight(), BufferedImage.TYPE_INT_ARGB_PRE);

		Graphics2D g2d = copy.createGraphics();
		g2d.drawImage(image, 0, 0, copy.getWidth(), copy.getHeight(), 0, 0,
				image.getWidth(), image.getHeight(), null);
		g2d.dispose();

		return copy;
	}

	/**
	 * Create an invisible cursor
	 */
	public static Cursor createEmptyCursor(String name) {

		if (name == null || name.isEmpty()) {
			name = "transparentCursor";
		}

		return Toolkit.getDefaultToolkit().createCustomCursor(
				new EmptyImage.AlphaImage(), new java.awt.Point(0, 0), name);
	}

	/**
	 * Transform a {@link BufferedImage} using an {@link AffineTransform}. The
	 * returned image is the same as passed as Argument. All work is done direct
	 * on the image.
	 */
	public static BufferedImage transform(BufferedImage img,
			AffineTransform transformation) {

		Graphics2D g2d = img.createGraphics();
		g2d.setTransform(transformation);

		g2d.drawImage(img, 0, 0, img.getWidth(), img.getHeight(), 0, 0,
				img.getWidth(), img.getHeight(), null);

		g2d.dispose();

		return img;
	}

	/**
	 * Mirror a {@link BufferedImage}. The returned image is a new
	 * BufferedImage.
	 * 
	 */
	public static BufferedImage mirror(BufferedImage img, boolean mirrorX,
			boolean mirrorY) {

		BufferedImage mirroredImg = new BufferedImage(img.getWidth(),
				img.getHeight(), BufferedImage.TYPE_INT_ARGB_PRE);
		Graphics2D g2d = mirroredImg.createGraphics();

		double halfWidth = (mirroredImg.getWidth() * 0.5);
		double halfHeight = (mirroredImg.getHeight() * 0.5);

		AffineTransform beforeTransform = g2d.getTransform();

		AffineTransform transform = new AffineTransform();
		transform.concatenate(g2d.getTransform());
		transform.translate(halfWidth, halfHeight);

		if (mirrorX) {
			transform.scale(-1, 1);
		}

		if (mirrorY) {
			transform.scale(1, -1);
		}

		transform.translate(-halfWidth, -halfHeight);
		g2d.setTransform(transform);

		g2d.drawImage(img, 0, 0, mirroredImg.getWidth(),
				mirroredImg.getHeight(), 0, 0, mirroredImg.getWidth(),
				mirroredImg.getHeight(), null);

		g2d.setTransform(beforeTransform);
		g2d.dispose();

		return mirroredImg;
	}

	/**
	 * The given {@link BufferedImage} will be colorized with the given
	 * {@link Color}. Brightness will be used to modify the given color. <br>
	 * The operation is done direct on the provided image.
	 */
	public static BufferedImage colorize(BufferedImage img, Color c) {

		int width = img.getWidth();
		int height = img.getHeight();

		WritableRaster raster = img.getRaster();
		ColorModel model = img.getColorModel();

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {

				Object pixel = raster.getDataElements(x, y, null);

				double value = ((model.getRed(pixel) + model.getGreen(pixel)
						+ model.getBlue(pixel)) / 3.0) / 255.0;

				if (value == Double.NaN || value == Double.NEGATIVE_INFINITY
						|| value == Double.POSITIVE_INFINITY) {
					continue;
				}

				Color cN = new Color((int) (value * c.getRed()),
						(int) (value * c.getGreen()),
						(int) (value * c.getBlue()), model.getAlpha(pixel));

				raster.setDataElements(x, y,
						model.getDataElements(cN.getRGB(), null));
			}
		}

		return img;
	}

	/**
	 * Fetch single frames and connect them, so that you get a single sprite
	 * image.
	 * 
	 * @param frameWidth
	 *            Width of one single frame
	 * @param frameHeight
	 *            Height of one single frame
	 * @param frames
	 *            The frames for the sprite
	 * @return An image containing all given frames. Frames will be stretched to
	 *         match frameWidth and frameHeight
	 */
	public static BufferedImage createSprite(int frameWidth, int frameHeight,
			BufferedImage... frames) {

		if (frames == null || frames.length <= 0) {
			return null;
		}

		BufferedImage sprite = new BufferedImage(frameWidth * frames.length,
				frameHeight, BufferedImage.TYPE_INT_ARGB_PRE);

		Graphics2D g2d = sprite.createGraphics();

		for (int i = 0; i < frames.length; i++) {

			if (frames[i] == null) {
				continue;
			}

			g2d.drawImage(frames[i], frameWidth * i, 0,
					frameWidth * i + frameWidth, frameHeight, 0, 0,
					frames[i].getWidth(), frames[i].getHeight(), null);
		}

		g2d.dispose();

		return sprite;
	}

}
