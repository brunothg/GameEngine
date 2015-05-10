package game.engine.stage.scene.object;

import game.engine.stage.scene.object.LabelSceneObject;
import game.engine.stage.scene.object.Size;
import game.engine.stage.scene.object.Orientation.HorizontalOrientation;
import game.engine.stage.scene.object.Orientation.VerticalOrientation;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.image.BufferedImage;

/**
 *
 * {@link LabelSceneObject} that draws the text on a cached {@link BufferedImage}. This speeds up drawing
 * when animating text, but the quality might be less.
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

		drawComponent();
	}

	@Override
	protected void paint(Graphics2D g, long elapsedTime)
	{

		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);

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

		super.paint(g2d, 0);
		g2d.dispose();
	}

	@Override
	public void setText(String text)
	{

		boolean redraw = !getText().endsWith(text);

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
	public void setScaleStrategy(ScaleStrategy scaleStrategy)
	{
		super.setScaleStrategy(scaleStrategy);

		updateCache(getWidth(), getHeight());
	}
}
