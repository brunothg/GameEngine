package game.engine.image;

import static org.junit.Assert.*;
import game.engine.stage.scene.object.Size;

import java.awt.Insets;

import org.junit.Before;
import org.junit.Test;

public class NinePatchImageTest {

	private NinePatchImage ninePatch;

	@Before
	public void setUp() throws Exception {

		InternalImage.setRootFolder("/game/engine/images/");

		ninePatch = new NinePatchImage(ImageUtils.BufferedImage(InternalImage
				.load("9patch.png")));
	}

	@Test
	public void insetsTest() throws Exception {

		Insets insets = ninePatch.getInsets();

		assertEquals(1, insets.right);
		assertEquals(2, insets.top);
		assertEquals(4, insets.left);
		assertEquals(3, insets.bottom);
	}

	@Test
	public void naturalSizeTest() throws Exception {

		Size size = ninePatch.getNaturalSize();

		assertEquals(95, size.getWidth());
		assertEquals(98, size.getHeight());
	}
}
