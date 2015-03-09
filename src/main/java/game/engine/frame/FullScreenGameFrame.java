package game.engine.frame;

import game.engine.exception.FullscreenException;
import game.engine.image.EmptyImage;
import game.engine.image.ImageUtils;
import game.engine.stage.Stage;
import game.engine.stage.SwingStage;
import game.engine.stage.scene.LoadingScene;
import game.engine.stage.scene.Scene;
import game.engine.time.Clock;

import java.awt.DisplayMode;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Window;
import java.awt.image.BufferStrategy;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * 
 * GameFrame using <b>Fullscreen Exclusive Mode</b> for better performance, if
 * possible. If full screen mode isn't supported, full screen mode is emulated
 * (performance may be equal {@link GameFrame}).
 * 
 * @author Marvin Bruns
 *
 */
public class FullScreenGameFrame extends Window implements Stage {

	private static final long serialVersionUID = 1L;

	private GraphicsDevice gd;
	private DisplayMode dm;

	private Clock clock;

	private volatile Scene scene;
	private Object sceneLock = new Object();

	/**
	 * Uses default {@link GraphicsDevice}.
	 * 
	 * @see #FullScreenGameFrame(GraphicsDevice)
	 */
	public FullScreenGameFrame() {

		this(GraphicsEnvironment.getLocalGraphicsEnvironment()
				.getDefaultScreenDevice());
	}

	/**
	 * Uses default {@link DisplayMode}.
	 * 
	 * @see #FullScreenGameFrame(GraphicsDevice, DisplayMode)
	 */
	public FullScreenGameFrame(GraphicsDevice gd) {

		this(gd, gd.getDisplayMode());
	}

	/**
	 * Uses default {@link GraphicsConfiguration}.
	 * 
	 * @see #FullScreenGameFrame(GraphicsDevice, GraphicsConfiguration,
	 *      DisplayMode)
	 * 
	 * @param gd
	 *            The {@link GraphicsDevice} used to show this window
	 * @param dm
	 *            The {@link DisplayMode} used to show this window
	 */
	public FullScreenGameFrame(GraphicsDevice gd, DisplayMode dm) {

		this(gd, gd.getDefaultConfiguration(), dm);
	}

	/**
	 * 
	 * @param gd
	 *            The {@link GraphicsDevice} used to show this window
	 * @param gc
	 *            The {@link GraphicsConfiguration} used to show this window
	 * @param dm
	 *            The {@link DisplayMode} used to show this window
	 */
	public FullScreenGameFrame(GraphicsDevice gd, GraphicsConfiguration gc,
			DisplayMode dm) {

		super(null, gc);

		if (gc.getDevice() != gd && !gc.getDevice().equals(gd)) {

			throw new FullscreenException(
					"GraphicsConfiguration is not from this GraphicsDevice");
		}

		this.gd = gd;
		this.dm = dm;

		initialize();

	}

	private void initialize() {

		setDefaultIcon();
		setAlwaysOnTop(true);

		setScene(new LoadingScene());

		clock = new Clock();
		clock.addClockListener(this);
		clock.start();

		initializeFulscreenMode();
	}

	private void initializeFulscreenMode() {

		gd.setFullScreenWindow(this);

		if (gd.isFullScreenSupported()) {

			initializeFullscreenExclusiveMode();
		} else {

			initializeSoftFullScreenMode();
		}
	}

	private void initializeSoftFullScreenMode() {

		setIgnoreRepaint(true);
	}

	private void initializeFullscreenExclusiveMode() {

		setIgnoreRepaint(true);
		gd.setDisplayMode(dm);
	}

	private void setDefaultIcon() {
		try {
			setIconImage(ImageIO.read(GameFrame.class
					.getResource("/game/engine/media/icon.png")));
		} catch (IOException e) {
			setIconImage(new EmptyImage.AlphaImage());
		}
	}

	@Override
	public void tick(long frames, long coveredTime) {

		Scene scene = getScene();

		int width = getWidth();
		int height = getHeight();

		BufferStrategy buffer = getBuffer();
		Graphics2D g2d = (Graphics2D) buffer.getDrawGraphics();
		ImageUtils.clearImage(g2d, width, height, ImageUtils.COLOR_TRANSPARENT);

		scene.paintScene(g2d, width, height, coveredTime);

		g2d.dispose();

		buffer.show();
	}

	private BufferStrategy getBuffer() {

		createBufferStrategy(2);
		return this.getBufferStrategy();
	}

	/**
	 * @see SwingStage#setScene(Scene)
	 */
	public void setScene(Scene scene) {

		synchronized (this.sceneLock) {
			this.scene = scene;
		}
	}

	/**
	 * @see SwingStage#getScene()
	 */
	public Scene getScene() {

		synchronized (this.sceneLock) {
			return this.scene;
		}
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
}
