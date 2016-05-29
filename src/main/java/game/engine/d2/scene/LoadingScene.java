package game.engine.d2.scene;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.RadialGradientPaint;
import java.awt.Stroke;
import java.awt.geom.Arc2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.EventListener;

import game.engine.d2.commons.RenderingOptions;
import game.engine.time.TimeUtils;

/**
 * A simple animated loading screen. On Linux systems the high quality drawing
 * result in slow rendering or blocking the whole jvm. So by default the shadow
 * drawing is turned off, but should be turned on supported systems for a great
 * loading screen.
 * 
 * @author Marvin Bruns
 *
 */
public class LoadingScene implements Scene {

	private static final int GRID_OFFSET = 20;
	private static final int GRID_SIZE = 1;

	private static final float CIRCLE_SIZE_INNER = 0.6f;
	private static final float CIRCLE_THICKNESS_INNER = 7.5f;
	private static final float CIRCLE_LENGTH_INNER = 0.7f;
	private static final int CIRCLE_SPEED_INNER = 20;

	private static final float CIRCLE_THICKNESS_OUTER = 5.0f;
	private static final int CIRCLE_SPEED_OUTER = 35;
	private static final float CIRCLE_LENGTH_OUTER = 0.6f;
	private static final float CIRCLE_SIZE_OUTER = 0.8f;

	private static final int TEXT_SPEED = 2;
	private static final int TEXT_DISTANCE = 10;
	private static final Color TEXT_COLOR = Color.WHITE;
	private static final String TEXT = "Loading";

	private static final float SHADOW_FRACTION_INNER = 0.2f;
	private static final float SHADOW_FRACTION_OUTER = 0.7f;

	private static final Color COLOR_BG = new Color(25, 109, 159);
	private static final Color COLOR_GRID = new Color(2, 71, 127);
	private static final Color COLOR_RADIAL_INNER = new Color(0, 0, 0, 0);
	private static final Color COLOR_RADIAL_OUTER = new Color(0, 0, 0, 150);
	private static final Color COLOR_CIRCLE_OUTER = new Color(74, 226, 251);
	private static final Color COLOR_CIRCLE_INNER = new Color(172, 211, 239);

	private static final RenderingOptions renderingOptions = new RenderingOptions()
			.setAntiAliasing(true).setEnhancedAlphaInterpolation(true)
			.setEnhancedRednering(true).setStrokeControl(false);

	private double innerCirclePosition = 0;
	private double outerCirclePosition = 0;
	private double textPhase = 0;

	private BufferedImage shadow;
	private boolean drawShadow;

	/**
	 * {@link LoadingScene} without shadow painting
	 */
	public LoadingScene() {
		this(false);
	}

	public LoadingScene(boolean drawShadow) {
		this.drawShadow = drawShadow;
	}

	@Override
	public void paintScene(Graphics2D g, int width, int height,
			long elapsedTime) {
		renderingOptions.apply(g);

		drawBackground(g, width, height);
		drawGrid(g, width, height);

		if (drawShadow) {
			drawShadow(g, width, height);
		}

		drawOuterCircle(g, width, height, elapsedTime);
		drawInnerCircle(g, width, height, elapsedTime);
		drawText(g, width, height, elapsedTime);
	}

	private void drawText(Graphics2D g, int width, int height,
			long elapsedTime) {

		textPhase += (TEXT_SPEED * TimeUtils.Seconds(elapsedTime));
		textPhase = textPhase % 4;

		String txt = TEXT + " ";
		for (int i = 1; i < textPhase; i++) {
			txt += ".";
		}

		Point2D center = new Point2D.Double(width * 0.5, height * 0.5);
		float size = Math.min(width, height) * CIRCLE_SIZE_INNER;
		size -= CIRCLE_THICKNESS_INNER;
		size -= TEXT_DISTANCE * 2;
		size = (float) Math.sqrt(Math.pow(size, 2) * 0.5);

		// The free area inside the rotating circles, that can be used for text
		// drawing.
		Rectangle2D usableArea = new Rectangle2D.Double(
				center.getX() - size * 0.5, center.getY() - size * 0.5, size,
				size);

		g.setColor(TEXT_COLOR);

		int fontSize = (int) Math.min(usableArea.getWidth(),
				usableArea.getHeight()) / (TEXT.length());
		g.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, fontSize));

		FontMetrics metrics = g.getFontMetrics();
		Rectangle2D stringBounds = metrics.getStringBounds(TEXT, g);

		float textWidth = (float) stringBounds.getWidth();
		float textHeight = (float) stringBounds.getHeight();

		g.drawString(txt, (float) (usableArea.getCenterX() - textWidth * 0.5),
				(float) (usableArea.getCenterY() - textHeight * 0.5));
	}

	private void drawInnerCircle(Graphics2D g, int width, int height,
			long elapsedTime) {

		innerCirclePosition = innerCirclePosition
				+ (CIRCLE_SPEED_INNER * TimeUtils.Seconds(elapsedTime));
		innerCirclePosition = innerCirclePosition % 360;

		float size = Math.min(width, height) * CIRCLE_SIZE_INNER;

		double start = innerCirclePosition;
		double extent = 360 * CIRCLE_LENGTH_INNER;

		Arc2D circle = new Arc2D.Double(width * 0.5 - size * 0.5,
				height * 0.5 - size * 0.5, size, size, start, extent,
				Arc2D.OPEN);

		Stroke strokeB = g.getStroke();
		g.setStroke(new BasicStroke(CIRCLE_THICKNESS_INNER,
				BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
		g.setColor(COLOR_CIRCLE_INNER);

		g.draw(circle);

		g.setStroke(strokeB);
	}

	private void drawOuterCircle(Graphics2D g, int width, int height,
			long elapsedTime) {

		outerCirclePosition = outerCirclePosition
				- (CIRCLE_SPEED_OUTER * TimeUtils.Seconds(elapsedTime));
		outerCirclePosition = outerCirclePosition % 360;

		float size = Math.min(width, height) * CIRCLE_SIZE_OUTER;

		double start = outerCirclePosition;
		double extent = 360 * CIRCLE_LENGTH_OUTER;

		Arc2D circle = new Arc2D.Double(width * 0.5 - size * 0.5,
				height * 0.5 - size * 0.5, size, size, start, extent,
				Arc2D.OPEN);

		Stroke strokeB = g.getStroke();
		g.setStroke(new BasicStroke(CIRCLE_THICKNESS_OUTER,
				BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND));
		g.setColor(COLOR_CIRCLE_OUTER);

		g.draw(circle);

		g.setStroke(strokeB);
	}

	private void drawShadow(Graphics2D g2d, int width, int height) {

		/**
		 * Shadow drawing takes much time. So buffering the shadow is a huge
		 * time improvement.
		 */
		if (shadow == null || shadow.getWidth() < width
				|| shadow.getHeight() < height) {
			if (width <= 0 || height <= 0) {
				return;
			}

			shadow = new BufferedImage(width, height,
					BufferedImage.TYPE_INT_ARGB_PRE);
			Graphics2D g = shadow.createGraphics();

			Point2D center = new Point2D.Double(width * 0.5, height * 0.5);

			Color[] colors = new Color[] { COLOR_RADIAL_INNER,
					COLOR_RADIAL_OUTER };
			float[] fractions = new float[] { SHADOW_FRACTION_INNER,
					SHADOW_FRACTION_OUTER };

			RadialGradientPaint shadowGradient = new RadialGradientPaint(center,
					(float) Math.min(width, height), fractions, colors);

			Paint paintB = g.getPaint();
			g.setPaint(shadowGradient);

			g.fill(new Rectangle2D.Double(0, 0, width, height));

			g.setPaint(paintB);
		}

		g2d.drawImage(shadow, 0, 0, width, height, 0, 0, shadow.getWidth(),
				shadow.getHeight(), null);
	}

	private void drawGrid(Graphics2D g, int width, int height) {

		g.setColor(COLOR_GRID);

		for (int x = GRID_OFFSET; x < width; x += GRID_OFFSET) {

			g.fillRect(x, 0, GRID_SIZE, height);
		}
		for (int y = GRID_OFFSET; y < height; y += GRID_OFFSET) {

			g.fillRect(0, y, width, GRID_SIZE);
		}
	}

	private void drawBackground(Graphics2D g, int width, int height) {
		g.setColor(COLOR_BG);
		g.fillRect(0, 0, width, height);
	}

	@Override
	public EventListener[] getEventListeners() {
		return null;
	}

}
