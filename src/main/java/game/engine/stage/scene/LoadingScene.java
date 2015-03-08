package game.engine.stage.scene;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.RadialGradientPaint;
import java.awt.RenderingHints;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.EventListener;

public class LoadingScene implements Scene {

	private static final int GRID_OFFSET = 20;
	private static final int GRID_SIZE = 1;

	private static final float SHADOW_FRACTION_INNER = 0.2f;
	private static final float SHADOW_FRACTION_OUTER = 0.7f;

	private static final Color COLOR_BG = new Color(25, 109, 159);
	private static final Color COLOR_GRID = new Color(2, 71, 127);
	private static final Color COLOR_RADIAL_INNER = new Color(0, 0, 0, 0);
	private static final Color COLOR_RADIAL_OUTER = new Color(0, 0, 0, 150);

	@Override
	public void paintScene(Graphics2D g, int width, int height, long elapsedTime) {

		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		drawBackground(g, width, height);
		drawGrid(g, width, height);
		drawShadow(g, width, height);
	}

	private void drawShadow(Graphics2D g, int width, int height) {

		Point2D center = new Point2D.Double(width * 0.5, height * 0.5);

		Color[] colors = new Color[] { COLOR_RADIAL_INNER, COLOR_RADIAL_OUTER };
		float[] fractions = new float[] { SHADOW_FRACTION_INNER,
				SHADOW_FRACTION_OUTER };

		RadialGradientPaint shadowGradient = new RadialGradientPaint(center,
				(float) Math.min(width, height), fractions, colors);

		Paint paintB = g.getPaint();
		g.setPaint(shadowGradient);

		g.fill(new Rectangle2D.Double(0, 0, width, height));

		g.setPaint(paintB);
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
