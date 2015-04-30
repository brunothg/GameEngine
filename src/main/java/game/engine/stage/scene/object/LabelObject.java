package game.engine.stage.scene.object;

import game.engine.stage.scene.object.Orientation.HorizontalOrientation;
import game.engine.stage.scene.object.Orientation.VerticalOrientation;
import game.engine.utils.Null;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Stroke;

/**
 * 
 * A {@link SceneObject} that represents a label for displaying text.
 * 
 * @author Marvin Bruns
 *
 */
public class LabelObject extends SceneObject
{

	private Font font;
	private String text;

	private VerticalOrientation verticalTextOrientation = VerticalOrientation.Center;
	private HorizontalOrientation horizontalTextOrientation = HorizontalOrientation.Center;

	private Paint paint;
	private Stroke stroke;

	private ScaleStrategy scaleStrategy = ScaleStrategy.FitParent;

	@Override
	protected void paint(Graphics2D g, long elapsedTime)
	{
		//TODO paint label
		if (paint != null)
		{
			g.setPaint(paint);
		}

		if (stroke != null)
		{
			g.setStroke(stroke);
		}

	}

	/**
	 * Set the font used to draw the text. May return null if default will be used.
	 * 
	 * @return The font used for drawing.
	 */
	public Font getFont()
	{
		return font;
	}

	/**
	 * Set the font used to draw the text. If set to null default will be used.
	 * 
	 * @param font Used font for drawing
	 */
	public void setFont(Font font)
	{

		this.font = font;
	}

	public VerticalOrientation getVerticalTextOrientation()
	{
		return verticalTextOrientation;
	}

	/**
	 * Set the vertical orientation of the drawn text
	 * 
	 * @param verticalTextOrientation Vertical orientation of the drawn text
	 */
	public void setVerticalTextOrientation(VerticalOrientation verticalTextOrientation)
	{
		this.verticalTextOrientation = Null.nvl(verticalTextOrientation, VerticalOrientation.Center);
	}

	public HorizontalOrientation getHorizontalTextOrientation()
	{
		return horizontalTextOrientation;
	}

	/**
	 * Set the horizontal orientation of the drawn text
	 * 
	 * @param horizontalTextOrientation Horizontal orientation of the drawn text
	 */
	public void setHorizontalTextOrientation(HorizontalOrientation horizontalTextOrientation)
	{
		this.horizontalTextOrientation = Null.nvl(horizontalTextOrientation, HorizontalOrientation.Center);
	}

	/**
	 * Get the text, this label will draw
	 * 
	 * @return The text that will be drawn
	 */
	public String getText()
	{
		return text;
	}

	/**
	 * Set the text, this label will draw
	 * 
	 * @param text The text that will be drawn
	 */
	public void setText(String text)
	{
		this.text = Null.nvl(text, "");
	}

	public Paint getPaint()
	{
		return paint;
	}

	/**
	 * Set the {@link Paint}, that is used for drawing
	 * 
	 * @param paint The paint used for drawing
	 */
	public void setPaint(Paint paint)
	{

		this.paint = paint;
	}

	/**
	 * @see #setPaint(Paint)
	 * @param color The color for drawing the string
	 */
	public void setColor(Color color)
	{

		setPaint(color);
	}

	public Stroke getStroke()
	{
		return stroke;
	}

	/**
	 * Set the {@link Stroke}, that is used for text drawing
	 * 
	 * @param stroke The stroke used for text drawing
	 */
	public void setStroke(Stroke stroke)
	{
		this.stroke = stroke;
	}

	public ScaleStrategy getScaleStrategy()
	{
		return scaleStrategy;
	}

	/**
	 * Set the used {@link ScaleStrategy}
	 * 
	 * @param scaleStrategy The strategy, that will be used fore scaling the text
	 */
	public void setScaleStrategy(ScaleStrategy scaleStrategy)
	{
		if (scaleStrategy == ScaleStrategy.Auto)
		{
			scaleStrategy = ScaleStrategy.FitParent;
		}

		this.scaleStrategy = Null.nvl(scaleStrategy, ScaleStrategy.FitParent);
	}

}
