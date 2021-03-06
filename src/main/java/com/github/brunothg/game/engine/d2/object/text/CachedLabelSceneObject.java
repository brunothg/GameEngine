package com.github.brunothg.game.engine.d2.object.text;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Stroke;
import java.awt.image.BufferedImage;

import com.github.brunothg.game.engine.d2.commons.FontScaleStrategy;
import com.github.brunothg.game.engine.d2.commons.Orientation.HorizontalOrientation;
import com.github.brunothg.game.engine.d2.commons.Orientation.VerticalOrientation;
import com.github.brunothg.game.engine.d2.commons.Size;
import com.github.brunothg.game.engine.image.ImageUtils;

/**
 *
 * {@link LabelSceneObject} that draws the text on a cached {@link BufferedImage}. This speeds up
 * drawing when animating text, but the quality might be less.
 * 
 * @see LabelSceneObject
 * @author Marvin Bruns
 *
 */
public class CachedLabelSceneObject extends LabelSceneObject
{

	BufferedImage cache;

	public CachedLabelSceneObject(String text)
	{

		super(text);
	}

	public CachedLabelSceneObject()
	{

		super();
	}

	private void updateCache(int width, int height)
	{

		if (width <= 0 || height <= 0)
		{
			return;
		}

		if (cache == null || cache.getWidth() != width || cache.getHeight() != height)
		{

			cache = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB_PRE);
		}
		else
		{

			ImageUtils.clearImage(cache, ImageUtils.COLOR_TRANSPARENT);
		}

		drawComponent();
	}

	@Override
	protected void paint(Graphics2D g, long elapsedTime)
	{

		if (getWidth() <= 0 || getHeight() <= 0)
		{
			return;
		}

		if (cache == null)
		{
			updateCache(getWidth(), getHeight());
		}

		g.drawImage(cache, 0, 0, getWidth(), getHeight(), 0, 0, cache.getWidth(), cache.getHeight(), null);
	}

	private void drawComponent()
	{

		Graphics2D g2d = cache.createGraphics();
		getRenderingOptions().apply(g2d);

		super.paint(g2d, 0);
		g2d.dispose();
	}

	@Override
	public void setText(String text)
	{

		boolean redraw = !getText().equals(text);

		super.setText(text);

		if (redraw)
		{
			updateCache(getWidth(), getHeight());
		}
	}

	@Override
	public void setSize(int width, int height)
	{
		boolean redraw = width != getWidth() || height != getHeight();

		super.setSize(width, height);

		if (redraw)
		{
			updateCache(getWidth(), getHeight());
		}
	}

	@Override
	public void setSize(Size size)
	{
		boolean redraw = size.getWidth() != getWidth() || size.getHeight() != getHeight();

		super.setSize(size);

		if (redraw)
		{
			updateCache(getWidth(), getHeight());
		}
	}

	@Override
	public void setColor(Color color)
	{
		super.setColor(color);

		updateCache(getWidth(), getHeight());
	}

	@Override
	public void setFont(Font font)
	{
		super.setFont(font);

		updateCache(getWidth(), getHeight());
	}

	@Override
	public void setFontFlags(int fontFlags)
	{
		super.setFontFlags(fontFlags);

		updateCache(getWidth(), getHeight());
	}

	@Override
	public void setDrawBoundingBox(boolean drawBoundingBox)
	{
		boolean redraw = isDrawBoundingBox() != drawBoundingBox;

		super.setDrawBoundingBox(drawBoundingBox);

		if (redraw)
		{
			updateCache(getWidth(), getHeight());
		}
	}

	@Override
	public void setHorizontalTextOrientation(HorizontalOrientation horizontalTextOrientation)
	{
		super.setHorizontalTextOrientation(horizontalTextOrientation);

		updateCache(getWidth(), getHeight());
	}

	@Override
	public void setOutlineColor(Color outlineColor)
	{
		super.setOutlineColor(outlineColor);

		updateCache(getWidth(), getHeight());
	}

	@Override
	public void setOutlinePaint(Paint outlinePaint)
	{
		super.setOutlinePaint(outlinePaint);

		updateCache(getWidth(), getHeight());
	}

	@Override
	public void setPaint(Paint paint)
	{
		super.setPaint(paint);

		updateCache(getWidth(), getHeight());
	}

	@Override
	public void setStroke(Stroke stroke)
	{
		super.setStroke(stroke);

		updateCache(getWidth(), getHeight());
	}

	@Override
	public void setVerticalTextOrientation(VerticalOrientation verticalTextOrientation)
	{
		super.setVerticalTextOrientation(verticalTextOrientation);

		updateCache(getWidth(), getHeight());
	}

	@Override
	public void setScaleStrategy(FontScaleStrategy scaleStrategy)
	{
		super.setScaleStrategy(scaleStrategy);

		updateCache(getWidth(), getHeight());
	}
}
