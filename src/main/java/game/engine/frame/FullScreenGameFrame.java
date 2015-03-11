package game.engine.frame;

import game.engine.image.EmptyImage;
import game.engine.stage.CanvasStage;
import game.engine.stage.SwingStage;
import game.engine.stage.scene.LoadingScene;
import game.engine.stage.scene.Scene;
import game.engine.time.Clock;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.DisplayMode;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Window;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.imageio.ImageIO;

/**
 * 
 * Fullscreen GameFrame using {@link CanvasStage} for drawing.<br>
 * Before using this you should test it. With some JVM implementations this will
 * fail.<br>
 * Don't forget to build some kind of exit and dispose this frame.
 * 
 * 
 * @author Marvin Bruns
 *
 */
public class FullScreenGameFrame {

	private Frame window;

	private GraphicsDevice gd;
	private DisplayMode dm;

	private AtomicBoolean isInFullScreenExclusiveMode = new AtomicBoolean(false);

	private DisplayMode previousDisplayMode;

	private CanvasStage stage;
	private Clock clock;

	/**
	 * Uses default title
	 * 
	 * @see #FullScreenGameFrame(String)
	 */
	public FullScreenGameFrame() {

		this("GameFrame");
	}

	/**
	 * Uses default GraphicsDevice
	 * 
	 * @param title
	 *            Frame title
	 * @see #FullScreenGameFrame(GraphicsDevice, String)
	 */
	public FullScreenGameFrame(String title) {

		this(GraphicsEnvironment.getLocalGraphicsEnvironment()
				.getDefaultScreenDevice(), title);
	}

	/**
	 * Uses default {@link DisplayMode}
	 * 
	 * @param title
	 *            Frame title
	 * 
	 * @see #FullScreenGameFrame(GraphicsDevice, DisplayMode, String)
	 */
	public FullScreenGameFrame(GraphicsDevice gd, String title) {

		this(gd, gd.getDisplayMode(), title);
	}

	/**
	 * Uses default {@link GraphicsConfiguration}
	 * 
	 * @param title
	 *            Frame title
	 * 
	 * @see #FullScreenGameFrame(GraphicsDevice, GraphicsConfiguration,
	 *      DisplayMode, String)
	 */
	public FullScreenGameFrame(GraphicsDevice gd, DisplayMode dm, String title) {

		this(gd, gd.getDefaultConfiguration(), dm, title);
	}

	/**
	 * 
	 * @param gd
	 *            {@link GraphicsDevice} used for rendering
	 * @param gc
	 *            {@link GraphicsConfiguration} used for {@link Window}
	 * @param dm
	 *            {@link DisplayMode} used for rendering
	 * @param title
	 *            Frame title
	 */
	public FullScreenGameFrame(GraphicsDevice gd, GraphicsConfiguration gc,
			DisplayMode dm, String title) {

		this.window = new Frame(title, gc);

		this.gd = gd;
		this.dm = dm;

		initialize();
	}

	private void initialize() {

		window.setLayout(new BorderLayout());
		window.setIgnoreRepaint(true);
		window.setUndecorated(true);
		window.setResizable(false);
		setDefaultIcon();

		stage = new CanvasStage();
		stage.setScene(new LoadingScene());
		window.add(stage, BorderLayout.CENTER);

		clock = new Clock();
		clock.addClockListener(stage);
		clock.start();

	}

	private void setDefaultIcon() {
		try {
			window.setIconImage(ImageIO.read(FullScreenGameFrame.class
					.getResource("/game/engine/media/icon.png")));
		} catch (Exception e) {
			window.setIconImage(new EmptyImage.AlphaImage());
		}
	}

	/**
	 * @see Frame#setTitle(String)
	 */
	public void setTitle(String title) {

		window.setTitle(title);
	}

	public void setIcon(Image image) {

		window.setIconImage(image);
	}

	/**
	 * Change DisplayMode when in full screen exclusive mode
	 * 
	 * @param dm
	 *            {@link DisplayMode}
	 * @return true if it was successful
	 */
	public boolean setDisplayMode(DisplayMode dm) {

		this.dm = dm;

		if (isInFullScreenExclusiveMode.get() && gd.isDisplayChangeSupported()) {

			gd.setDisplayMode(dm);

			return true;
		}

		try {
			gd.setDisplayMode(dm);

			return true;
		} catch (Throwable e) {
		}

		return false;
	}

	/**
	 * Check if this Window is in fullscreen exclusive mode
	 * 
	 * @return true if in fullscreen exclusive mode
	 */
	public boolean isInExclusiveFullscreenMode() {

		return isInFullScreenExclusiveMode.get();
	}

	/**
	 * Set invisible and dispose window
	 * 
	 * @see Frame#dispose()
	 */
	public void dispose() {

		try {
			setVisible(false);
			window.dispose();
		} catch (Exception e) {
		} finally {

			clock.destroy();
		}
	}

	@Override
	protected void finalize() throws Throwable {

		try {
			clock.destroy();
		} catch (Exception e) {
		} finally {

			super.finalize();
		}
	}

	public void setVisible(boolean visible) {
		if (visible) {

			show();
		} else {

			hide();
		}
	}

	private void show() {

		boolean fullScreenSupported = gd.isFullScreenSupported();

		if (fullScreenSupported && !isInFullScreenExclusiveMode.get()) {

			previousDisplayMode = gd.getDisplayMode();

			isInFullScreenExclusiveMode.set(true);
		}

		gd.setFullScreenWindow(window);
		setDisplayMode(dm);
	}

	private void hide() {

		setDisplayMode(previousDisplayMode);

		if (isInFullScreenExclusiveMode.get()) {

			previousDisplayMode = null;

			isInFullScreenExclusiveMode.set(false);
		}

		gd.setFullScreenWindow(null);
	}

	/**
	 * Get the stage of this game frame.
	 * 
	 * @return Stage of this frame
	 */
	public CanvasStage getStage() {

		return stage;
	}

	/**
	 * Change the {@link SwingStage} that is used by this {@link SwingGameFrame}
	 * . Normally there's no reason to change the default stage.
	 * 
	 * @param stage
	 */
	public void setStage(CanvasStage stage) {

		if (stage == null) {
			throw new IllegalArgumentException("Null value not allowed");
		}
		clock.removeClockListener(this.stage);
		window.remove(this.stage);

		this.stage = stage;
		window.add(this.stage, BorderLayout.CENTER);
		clock.addClockListener(stage);
	}

	/**
	 * @see CanvasStage#setScene(Scene)
	 */
	public void setScene(Scene scene) {

		getStage().setScene(scene);
	}

	/**
	 * @see CanvasStage#getScene()
	 */
	public Scene getScene() {

		return getStage().getScene();
	}

	/**
	 * @see Clock#setFramesPerSecond(int)
	 */
	public void setFramesPerSecond(int framesPerSecond) {

		clock.setFramesPerSecond(framesPerSecond);
	}

	/**
	 * @see Clock#getFramesPerSecond()
	 */
	public double getFramesPerSecond() {

		return clock.getFramesPerSecond();
	}

	/**
	 * @see Frame#setCursor(Cursor)
	 */
	public void setCursor(Cursor cursor) {

		window.setCursor(cursor);
	}

	/**
	 * @see Frame#addKeyListener(KeyListener)
	 */
	public void addKeyListener(KeyListener l) {

		window.addKeyListener(l);
	}

	/**
	 * @see Frame#addMouseListener(MouseListener)
	 */
	public void addMouseListener(MouseListener l) {

		window.addMouseListener(l);
	}

	/**
	 * @see Frame#addMouseMotionListener(MouseMotionListener)
	 */
	public void addMouseListener(MouseMotionListener l) {

		window.addMouseMotionListener(l);
	}

}
