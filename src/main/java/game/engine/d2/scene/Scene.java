package game.engine.d2.scene;

import java.awt.Graphics2D;
import java.util.EventListener;

import javax.swing.JPanel;

public interface Scene {

	/**
	 * Draw actual scene state
	 * 
	 * @param g
	 *            {@link Graphics2D} object to draw on
	 * @param width
	 *            Width of painting area
	 * @param height
	 *            Height of painting area
	 * @param elapsedTime
	 *            Time since the last painting
	 */
	public void paintScene(Graphics2D g, int width, int height,
			long elapsedTime);

	/**
	 * Should return all {@link EventListener}s that this scene want to
	 * register.<br>
	 * Some EventListeners maybe unsupported. See {@link JPanel} for additional
	 * information.<br>
	 * This method will be called once when this scene is added to a stage.
	 */
	public EventListener[] getEventListeners();

}
