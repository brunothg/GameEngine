package game.engine.image;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

/**
 * Utility class for images.
 * 
 * @author Marvin Bruns
 *
 */
public class ImageUtils
{

	/**
	 * Uses {@link BufferedImage#TYPE_INT_ARGB}
	 * 
	 * @see #BufferedImage(Image, int)
	 */
	public static BufferedImage BufferedImage(Image img)
	{

		return BufferedImage(img, BufferedImage.TYPE_INT_ARGB);
	}

	/**
	 * Uses null observer
	 * 
	 * @see #BufferedImage(Image, ImageObserver, int)
	 */
	public static BufferedImage BufferedImage(Image img, int type)
	{

		return BufferedImage(img, null, type);
	}

	/**
	 * Convert a {@link Image} in a {@link BufferedImage}
	 * 
	 * @param img Image to convert
	 * @param observer {@link ImageObserver}
	 * @param type {@link BufferedImage#TYPE_INT_ARGB} etc.
	 * @return the img as BufferedImage
	 */
	public static BufferedImage BufferedImage(Image img, ImageObserver observer, int type)
	{

		if (img instanceof BufferedImage)
		{
			return (BufferedImage) img;
		}

		int width = img.getWidth(observer);
		int height = img.getHeight(observer);

		BufferedImage bimg = new BufferedImage(width, height, type);

		Graphics2D graphics = bimg.createGraphics();
		graphics.drawImage(img, 0, 0, width, height, 0, 0, width, height, observer);

		return bimg;
	}

	/**
	 * Uses {@link BufferedImage#TYPE_INT_ARGB}
	 * 
	 * @see #getScaledInstance(Image, int, int, int, ImageObserver)
	 */
	public static BufferedImage getScaledInstance(Image img, int width, int height, ImageObserver observer)
	{

		return getScaledInstance(img, width, height, BufferedImage.TYPE_INT_ARGB, observer);
	}

	/**
	 * Get a scaled version of an image
	 * 
	 * @param img The image to scale
	 * @param width New width
	 * @param height New height
	 * @param type The image type e.g. {@link BufferedImage#TYPE_INT_ARGB}
	 * @param observer {@link ImageObserver}
	 * @return
	 */
	public static BufferedImage getScaledInstance(Image img, int width, int height, int type, ImageObserver observer)
	{
		BufferedImage bimg = new BufferedImage(width, height, type);

		Graphics2D graphics = bimg.createGraphics();
		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		graphics.drawImage(img, 0, 0, width, height, 0, 0, img.getWidth(observer), img.getHeight(observer), observer);

		return bimg;
	}
}
