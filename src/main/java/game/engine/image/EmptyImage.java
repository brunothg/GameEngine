package game.engine.image;

import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 * 
 * BufferedImage(1x1) with a width and height of one. The color of the only pixel can be set.
 * 
 * @author Marvin Bruns
 *
 */
public class EmptyImage extends BufferedImage
{

	public EmptyImage(Color c)
	{
		super(1, 1, BufferedImage.TYPE_INT_ARGB);

		setRGB(0, 0, c.getRGB());
	}

	public static class AlphaImage extends EmptyImage
	{

		public AlphaImage()
		{
			super(new Color(0, 0, 0, 0));
		}

	}
}
