package game.engine.stage;

import game.engine.time.ClockListener;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.EventListener;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * Buffered swing component. Draws scenes and is the base component for all
 * further drawings.<br>
 * Supported EventListeners:<br>
 * <ul>
 * <li>{@link KeyListener}</li>
 * <li>{@link MouseListener}</li>
 * <li>{@link MouseMotionListener}</li>
 * </ul>
 * 
 * @author Marvin Bruns
 */
public class Stage extends JPanel implements ClockListener {

	private static final long serialVersionUID = 1L;

	private Scene scene;
	private EventListener[] sceneListener;

	private BufferedImage offScreen;
	private Object offScreenLock = new Object();

	public Stage() {

		setOpaque(true);
		setBackground(Color.BLACK);
		setFocusable(true);
		setRequestFocusEnabled(true);
		setIgnoreRepaint(false);
	}

	/**
	 * Change the painted scene.
	 * 
	 * @param scene
	 *            Scene to be staged
	 */
	public void setScene(Scene scene) {

		recycleScene();

		this.scene = scene;
		registerEventListeners();
	}

	/**
	 * Get the actual painted scene.
	 * 
	 * @return Staged scene or null if none
	 */
	public Scene getScene() {

		return this.scene;
	}

	@Override
	public void tick(long frames, long coveredTime) {

		drawOffScreen(coveredTime);
		threadsafeRepaint();
	}

	private void threadsafeRepaint() {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				repaint();
			}
		});
	}

	/**
	 * Register scene's EventListeners
	 */
	private void registerEventListeners() {

		sceneListener = getScene().getEventListeners();

		for (int i = 0; i < sceneListener.length; i++) {

			EventListener evl = sceneListener[i];
			addEventListener(evl);
		}
	}

	/**
	 * Remove all registered EventListeners
	 */
	private void recycleScene() {

		if (sceneListener == null) {
			return;
		}

		for (int i = 0; i < sceneListener.length; i++) {

			EventListener evl = sceneListener[i];
			removeEventListener(evl);
		}
	}

	private void addEventListener(EventListener evl) {

		Class<?> cls = evl.getClass();

		if (KeyListener.class.isInstance(cls)) {
			addKeyListener((KeyListener) evl);
		}

		if (MouseListener.class.isInstance(cls)) {
			addMouseListener((MouseListener) evl);
		}

		if (MouseMotionListener.class.isInstance(cls)) {
			addMouseMotionListener((MouseMotionListener) evl);
		}
	}

	private void removeEventListener(EventListener evl) {

		Class<?> cls = evl.getClass();

		if (KeyListener.class.isInstance(cls)) {
			removeKeyListener((KeyListener) evl);
		}

		if (MouseListener.class.isInstance(cls)) {
			removeMouseListener((MouseListener) evl);
		}

		if (MouseMotionListener.class.isInstance(cls)) {
			removeMouseMotionListener((MouseMotionListener) evl);
		}
	}

	@Override
	protected void paintComponent(Graphics g) {

		Graphics2D g2 = (Graphics2D) g;

		// Draw background
		g2.setColor(getBackground());
		g2.fillRect(0, 0, getWidth(), getHeight());

		// Draw off screen
		if (getScene() == null || offScreen == null) {
			return;
		}
		synchronized (offScreenLock) {

			g.drawImage(offScreen, 0, 0, getWidth(), getHeight(), 0, 0,
					offScreen.getWidth(), offScreen.getHeight(), null);
		}
	}

	/**
	 * 
	 * @param elapedTime
	 *            Elapsed time since last drawing.
	 * @return
	 */
	private void drawOffScreen(long elapedTime) {

		// Get size
		int width = getWidth();
		int height = getHeight();

		synchronized (offScreenLock) {

			// Create if null or check dimensions
			if (recreateOffScreen(width, height)) {

				offScreen = new BufferedImage(width, height,
						BufferedImage.TYPE_INT_ARGB_PRE);
			}

			// Paint scene
			Graphics2D offGraphics = offScreen.createGraphics();
			getScene().paintScene(offGraphics, width, height, elapedTime);
		}
	}

	/**
	 * Tests if the off screen has to be created or recreated.
	 */
	private boolean recreateOffScreen(int width, int height) {

		synchronized (offScreenLock) {

			return offScreen == null
					|| (offScreen.getWidth() != width || offScreen.getHeight() != height);
		}
	}
}
