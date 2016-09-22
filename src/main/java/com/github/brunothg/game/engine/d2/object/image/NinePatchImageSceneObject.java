package com.github.brunothg.game.engine.d2.object.image;

import com.github.brunothg.game.engine.d2.object.SceneObject;
import com.github.brunothg.game.engine.image.ImageUtils;
import com.github.brunothg.game.engine.image.NinePatchImage;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.image.BufferedImage;

/**
 * 
 * This class fetches a {@link NinePatchImage} and puts it in the {@link SceneObject} context.
 * 
 * @author Marvin Bruns
 *
 */
public class NinePatchImageSceneObject extends SceneObject
{

	NinePatchImage image;

	public NinePatchImageSceneObject(BufferedImage image)
	{

		this.image = new NinePatchImage(image);
	}

	public NinePatchImageSceneObject(NinePatchImage image)
	{

		this.image = image;
	}

	public NinePatchImageSceneObject(Image image)
	{

		this(ImageUtils.BufferedImage(image));
	}

	@Override
	protected void paint(Graphics2D g, long elapsedTime)
	{

		image.draw(g, getWidth(), getHeight());
	}

	/**
	 * @see NinePatchImage#setQuality(int)
	 * @param quality The interpolation quality
	 */
	public void setQuality(int quality)
	{

		image.setQuality(quality);
	}

	public Insets getInsets()
	{

		return image.getInsets();
	}
}
