package game.engine.stage.scene.object;

import game.engine.stage.scene.object.Orientation.HorizontalOrientation;
import game.engine.stage.scene.object.Orientation.VerticalOrientation;
import game.engine.utils.Null;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.font.GlyphVector;
import java.awt.font.LineMetrics;

/**
 * 
 * A {@link SceneObject} that represents a label for displaying text.
 * 
 * @author Marvin Bruns
 *
 */
public class LabelObject extends SceneObject {

	private Font font;
	private int fontFlags = Font.LAYOUT_LEFT_TO_RIGHT;
	private String text = "";

	private VerticalOrientation verticalTextOrientation = VerticalOrientation.Center;
	private HorizontalOrientation horizontalTextOrientation = HorizontalOrientation.Center;

	private Paint paint = Color.BLACK;
	private Paint outlinePaint = Color.BLACK;
	private Stroke stroke;

	private ScaleStrategy scaleStrategy = ScaleStrategy.FitParent;

	public LabelObject() {

		this("");
	}

	public LabelObject(String text) {

		setText(text);
	}

	@Override
	protected void paint(Graphics2D g, long elapsedTime) {
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		if (paint != null) {
			g.setPaint(paint);
		}

		if (stroke != null) {
			g.setStroke(stroke);
		}

		if (font != null) {
			g.setFont(font);
		}

		Font font = g.getFont();
		String text = getText();
		int flags = getFontFlags();

		GlyphVector vector;
		Shape outline;

		FontMetrics fontMetrics = g.getFontMetrics(font);
		LineMetrics lineMetrics = fontMetrics.getLineMetrics(text, g);

		Integer deltaSize = null;
		loop: while (true) {

			double stringHeight = lineMetrics.getAscent()
					+ lineMetrics.getDescent();
			double stringWidth = fontMetrics.stringWidth(text)
					+ fontMetrics.charWidth(' ');

			switch (scaleStrategy) {
			case Auto:
			case FitParent:
				// Size to object bounds, if text smaller -> increase font size,
				// if text bigger -> use smaller font size
				if ((deltaSize == null || deltaSize > 0)
						&& (stringWidth < getWidth() && stringHeight < getHeight())) {
					// to small
					deltaSize = +1;
				} else if ((deltaSize == null || deltaSize < 0)
						&& (stringWidth > getWidth() || stringHeight > getHeight())) {
					// to big
					deltaSize = -1;
				} else {
					break loop;
				}
				break;
			case FitParentHeight:
				// Size to object bounds, if text height smaller -> increase
				// font size, if text height bigger -> use smaller font size
				if ((deltaSize == null || deltaSize > 0)
						&& (stringHeight < getHeight())) {
					// to small
					deltaSize = +1;
				} else if ((deltaSize == null || deltaSize < 0)
						&& (stringHeight > getHeight())) {
					// to big
					deltaSize = -1;
				} else {
					break loop;
				}
				break;
			case FitSize:
				// Size to text bounds, if text smaller than container -> /, if
				// text bigger -> use smaller font size
				if (stringWidth > getWidth() || stringHeight > getHeight()) {
					// to big
					deltaSize = -1;
				} else {
					break loop;
				}
				break;
			case FitHeight:
				// Size to text bounds, if text smaller than container -> /, if
				// text height bigger -> use smaller font size
				if (stringHeight > getHeight()) {
					// to big
					deltaSize = -1;
				} else {
					break loop;
				}
				break;
			case NoScale:
			default:
				break loop;
			}
			font = font.deriveFont((float) (font.getSize2D() + deltaSize));
			g.setFont(font);

			fontMetrics = g.getFontMetrics(font);
			lineMetrics = fontMetrics.getLineMetrics(text, g);
		}
		vector = font.layoutGlyphVector(g.getFontRenderContext(),
				text.toCharArray(), 0, text.length(), flags);
		outline = vector.getOutline();

		layout(g, fontMetrics, text);
		draw(g, outline);
	}

	private void draw(Graphics2D g, Shape outline) {
		g.fill(outline);
		if (outlinePaint != null) {
			g.setPaint(outlinePaint);
		}
		g.draw(outline);
	}

	private void layout(Graphics2D g, FontMetrics metrics, String text) {

		LineMetrics lineMetrics = metrics.getLineMetrics(text, g);

		double posX;
		double posY;

		switch (horizontalTextOrientation) {
		case East:
			posX = getWidth() - metrics.stringWidth(text);
			break;
		case West:
			posX = 0;
			break;
		case Center:
		default:
			posX = getWidth() * 0.5 - metrics.stringWidth(text) * 0.5;
			break;
		}

		switch (verticalTextOrientation) {
		case North:
			posY = lineMetrics.getAscent();
			break;
		case South:
			posY = getHeight() - lineMetrics.getDescent();
			break;
		case Center:
		default:
			posY = lineMetrics.getAscent()
					+ (getHeight() - (lineMetrics.getAscent() + lineMetrics
							.getDescent())) * 0.5;
			break;
		}

		g.translate(posX, posY);
	}

	/**
	 * Set the font used to draw the text. May return null if default will be
	 * used.
	 * 
	 * @return The font used for drawing.
	 */
	public Font getFont() {
		return font;
	}

	/**
	 * Set the font used to draw the text. If set to null default will be used.
	 * 
	 * @param font
	 *            Used font for drawing
	 */
	public void setFont(Font font) {

		this.font = font;
	}

	public VerticalOrientation getVerticalTextOrientation() {
		return verticalTextOrientation;
	}

	/**
	 * Set the vertical orientation of the drawn text
	 * 
	 * @param verticalTextOrientation
	 *            Vertical orientation of the drawn text
	 */
	public void setVerticalTextOrientation(
			VerticalOrientation verticalTextOrientation) {
		this.verticalTextOrientation = Null.nvl(verticalTextOrientation,
				VerticalOrientation.Center);
	}

	public HorizontalOrientation getHorizontalTextOrientation() {
		return horizontalTextOrientation;
	}

	/**
	 * Set the horizontal orientation of the drawn text
	 * 
	 * @param horizontalTextOrientation
	 *            Horizontal orientation of the drawn text
	 */
	public void setHorizontalTextOrientation(
			HorizontalOrientation horizontalTextOrientation) {
		this.horizontalTextOrientation = Null.nvl(horizontalTextOrientation,
				HorizontalOrientation.Center);
	}

	/**
	 * Get the text, this label will draw
	 * 
	 * @return The text that will be drawn
	 */
	public String getText() {
		return text;
	}

	/**
	 * Set the text, this label will draw
	 * 
	 * @param text
	 *            The text that will be drawn
	 */
	public void setText(String text) {
		this.text = Null.nvl(text, "");
	}

	public Paint getPaint() {
		return paint;
	}

	/**
	 * Set the {@link Paint}, that is used for drawing
	 * 
	 * @param paint
	 *            The paint used for drawing
	 */
	public void setPaint(Paint paint) {

		this.paint = paint;
	}

	/**
	 * @see #setPaint(Paint)
	 * @param color
	 *            The color for drawing the string
	 */
	public void setColor(Color color) {

		setPaint(color);
	}

	public Paint getOutlinePaint() {
		return outlinePaint;
	}

	/**
	 * Set the {@link Paint}, that is used for drawing outlines
	 * 
	 * @param outlinePaint
	 *            The paint used for drawing outlines
	 */
	public void setOutlinePaint(Paint outlinePaint) {
		this.outlinePaint = outlinePaint;
	}

	/**
	 * Set the color for outline painting
	 * 
	 * @see #setOutlinePaint(Paint)
	 * @param outlineColor
	 *            Color for outline painting
	 */
	public void setOutlineColor(Color outlineColor) {
		setOutlinePaint(outlineColor);
	}

	public Stroke getStroke() {
		return stroke;
	}

	/**
	 * Set the {@link Stroke}, that is used for text drawing
	 * 
	 * @param stroke
	 *            The stroke used for text drawing
	 */
	public void setStroke(Stroke stroke) {
		this.stroke = stroke;
	}

	public ScaleStrategy getScaleStrategy() {
		return scaleStrategy;
	}

	/**
	 * Set the used {@link ScaleStrategy}. If the scale strategy is not
	 * {@link ScaleStrategy#NoScale} the size of the {@link Font} is ignored.
	 * 
	 * @param scaleStrategy
	 *            The strategy, that will be used fore scaling the text
	 */
	public void setScaleStrategy(ScaleStrategy scaleStrategy) {
		if (scaleStrategy == ScaleStrategy.Auto) {
			scaleStrategy = ScaleStrategy.FitParent;
		}

		this.scaleStrategy = Null.nvl(scaleStrategy, ScaleStrategy.FitParent);
	}

	public int getFontFlags() {
		return fontFlags;
	}

	/**
	 * Set the font flags as specified by
	 * {@link Font#layoutGlyphVector(java.awt.font.FontRenderContext, char[], int, int, int)}
	 * 
	 * @param fontFlags
	 *            The flags used for creating the {@link GlyphVector}
	 */
	public void setFontFlags(int fontFlags) {
		this.fontFlags = fontFlags;
	}

}
