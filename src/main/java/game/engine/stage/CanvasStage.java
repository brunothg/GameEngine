package game.engine.stage;

import game.engine.image.NullGraphics;
import game.engine.stage.scene.Scene;

import java.awt.Canvas;
import java.awt.Graphics2D;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferStrategy;
import java.util.EventListener;

public class CanvasStage extends Canvas implements Stage {

	private static final long serialVersionUID = 1L;

	private volatile Scene scene;
	private Object sceneLock = new Object();

	private BufferStrategy bufferStrategy;

	private EventListener[] sceneListener;

	public CanvasStage() {

		setIgnoreRepaint(true);
	}

	@Override
	public void tick(long frames, long coveredTime) {

		Graphics2D g2d;

		if (!isReadyForDrawing()) {

			g2d = new NullGraphics();
		} else {

			g2d = (Graphics2D) bufferStrategy.getDrawGraphics();
		}

		int width = getWidth();
		int height = getHeight();

		synchronized (sceneLock) {

			Scene scene = getScene();
			scene.paintScene(g2d, width, height, coveredTime);
		}

		g2d.dispose();
		bufferStrategy.show();
	}

	private boolean isReadyForDrawing() {

		if (bufferStrategy == null && isDisplayable()) {

			createBufferStrategy(2);
			bufferStrategy = getBufferStrategy();
		} else if (bufferStrategy == null && !isDisplayable()) {

			return false;
		}

		return true;
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
}
