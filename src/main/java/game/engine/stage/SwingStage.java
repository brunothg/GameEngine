package game.engine.stage;

import game.engine.image.ImageUtils;
import game.engine.stage.scene.Scene;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
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
public class SwingStage extends JPanel implements Stage {

	private static final long serialVersionUID = 1L;

	private Scene scene;
	private Object sceneLock = new Object();

	private EventListener[] sceneListener;

	private BufferedImage offScreen;
	private Object offScreenLock = new Object();

	/**
	 * Used if the off screen could'n be drawn (e.g. size is zero). Will be
	 * added to elapsedTime next not skipped drawing.
	 */
	private long skippedTime = 0;

	public SwingStage() {

		setOpaque(true);
		setBackground(Color.BLACK);
		setFocusable(true);
		setRequestFocusEnabled(true);
		setIgnoreRepaint(false);
		setDoubleBuffered(false);
	}

	@Override
	public void setScene(Scene scene) {

		synchronized (sceneLock) {
			recycleScene();

			this.scene = scene;
			registerEventListeners();
		}
	}

	@Override
	public Scene getScene() {

		synchronized (sceneLock) {
			return this.scene;
		}
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

		if (sceneListener == null) {
			return;
		}

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

			// Recreate if check dimensions
			if (recreateOffScreen(getWidth(), getHeight())) {
				drawOffScreen(0);
			}

			g.drawImage(offScreen, 0, 0, getWidth(), getHeight(), 0, 0,
					offScreen.getWidth(), offScreen.getHeight(), null);
		}

		Toolkit.getDefaultToolkit().sync();
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

		if (width <= 0 || height <= 0) {

			skippedTime += elapedTime;
			return;
		}

		elapedTime += skippedTime;
		skippedTime = 0;

		// Drawing not possible -> no scene to display
		if (getScene() == null) {
			return;
		}

		synchronized (offScreenLock) {

			// Create if null or check dimensions
			if (recreateOffScreen(width, height)) {

				offScreen = new BufferedImage(width, height,
						BufferedImage.TYPE_INT_ARGB_PRE);
			}

			// Clear stage and paint scene
			Graphics2D offGraphics = offScreen.createGraphics();
			ImageUtils.clearImage(offGraphics, width, height,
					ImageUtils.COLOR_TRANSPARENT);

			synchronized (sceneLock) {
				getScene().paintScene(offGraphics, width, height, elapedTime);
			}

			offGraphics.dispose();
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
