package game.engine.stage.scene.object;

import game.engine.image.ImageUtils;
import game.engine.image.NinePatchImage;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

/**
 * 
 * This class fetches a {@link NinePatchImage} and puts it in the
 * {@link SceneObject} context.
 * 
 * @author Marvin Bruns
 *
 */
public class NinePatchImageSceneObject extends SceneObject {

	NinePatchImage image;

	public NinePatchImageSceneObject(BufferedImage image) {

		this.image = new NinePatchImage(image);
	}

	public NinePatchImageSceneObject(Image image) {

		this(ImageUtils.BufferedImage(image));
	}

	@Override
	protected void paint(Graphics2D g, long elapsedTime) {

		image.draw(g, getWidth(), getHeight());
	}

}
