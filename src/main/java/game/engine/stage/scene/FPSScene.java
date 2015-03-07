package game.engine.stage.scene;

import game.engine.time.TimeUtils;
import game.engine.time.Timer;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.EventListener;

/**
 * This scene is an overlay and paints the fps rate in the upper left corner.
 * 
 * @author Marvin Bruns
 */
public class FPSScene implements Scene {

	private Scene scene;
	private Timer timer;
	private Color textColor;
	private Font font;

	/**
	 * New Overlay fps scene.
	 * 
	 * @param scene
	 *            Underlying scene
	 */
	public FPSScene(Scene scene) {
		this.scene = scene;
		this.timer = new Timer();
		this.textColor = Color.BLACK;
		this.font = new Font(Font.SANS_SERIF, Font.BOLD, 12);
	}

	@Override
	public void paintScene(Graphics2D g, int width, int height, long elapsedTime) {

		timer.update();

		scene.paintScene(g, width, height, elapsedTime);
		paintFPS(g);
	}

	private void paintFPS(Graphics2D g) {

		float fps = TimeUtils.NANOSECONDS_PER_SECOND
				/ (float) timer.elapsedTime();

		if (Float.isNaN(fps) || Float.isInfinite(fps)) {
			fps = 0;
		}

		g.setColor(textColor);
		g.setFont(font);
		g.drawString(String.format("%.2f FPS", fps), 2, 2 + g.getFontMetrics()
				.getHeight());
	}

	public Color getTextColor() {
		return textColor;
	}

	public void setTextColor(Color textColor) {
		if (textColor == null) {
			return;
		}

		this.textColor = textColor;
	}

	public Font getFont() {
		return font;
	}

	public void setFont(Font font) {
		if (font == null) {
			return;
		}

		this.font = font;
	}

	@Override
	public EventListener[] getEventListeners() {

		if (scene != null) {

			return scene.getEventListeners();
		}

		return null;
	}

}
