package game.engine.image;

import java.awt.image.BufferedImage;

/**
 * 
 * Empty(alpha) BufferedImage(1x1)
 * 
 * @author Marvin Bruns
 *
 */
public class EmptyImage extends BufferedImage
{

	public EmptyImage()
	{
		super(1, 1, BufferedImage.TYPE_INT_ARGB);
		setRGB(0, 0, 0);
	}

}
