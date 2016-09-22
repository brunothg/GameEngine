package com.github.brunothg.game.engine.image;

import static org.junit.Assert.*;

import java.awt.Insets;

import org.junit.Before;
import org.junit.Test;

import com.github.brunothg.game.engine.d2.commons.Size;

public class NinePatchImageTest
{

	private NinePatchImage ninePatch;

	@Before
	public void setUp() throws Exception
	{

		InternalImage.setRootFolder("/com/github/brunothg/game/engine/images/");

		ninePatch = new NinePatchImage(ImageUtils.BufferedImage(InternalImage.load("9patch.9.png")));
	}

	@Test
	public void insetsTest() throws Exception
	{

		Insets insets = ninePatch.getInsets();

		assertEquals(36, insets.right);
		assertEquals(36, insets.top);
		assertEquals(37, insets.left);
		assertEquals(35, insets.bottom);
	}

	@Test
	public void naturalSizeTest() throws Exception
	{

		Size size = ninePatch.getNaturalSize();

		assertEquals(115, size.getWidth());
		assertEquals(119, size.getHeight());
	}
}
