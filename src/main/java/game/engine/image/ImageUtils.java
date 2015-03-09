package game.engine.image;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
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

	private static final Color COLOR_TRANSPARENT = new Color(0, 0, 0, 0);

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
	public static BufferedImage BufferedImage(Image img,
			ImageObserver observer, int type) {

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
	 * @return
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
	 * Create copy of an {@link BufferedImage}. Properties will be dismissed.
	 * 
	 * @param image
	 *            Image to be copied
	 * @return Copied image
	 */
	public static BufferedImage copy(BufferedImage image) {

		ColorModel cm = image.getColorModel();
		boolean alphaPremultiplied = image.isAlphaPremultiplied();
		WritableRaster raster = image.copyData(null);

		BufferedImage copy = new BufferedImage(cm, raster, alphaPremultiplied,
				null);

		return copy;
	}
}
