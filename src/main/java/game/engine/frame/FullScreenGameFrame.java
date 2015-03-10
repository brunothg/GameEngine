package game.engine.frame;

import game.engine.image.EmptyImage;
import game.engine.stage.CanvasStage;
import game.engine.stage.SwingStage;
import game.engine.stage.scene.LoadingScene;
import game.engine.stage.scene.Scene;
import game.engine.time.Clock;

import java.awt.BorderLayout;
import java.awt.DisplayMode;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Window;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.imageio.ImageIO;

/**
 * 
 * Fullscreen GameFrame
 * 
 * @author Marvin Bruns
 *
 */
public class FullScreenGameFrame
{

	private Window window;

	private GraphicsDevice gd;
	private DisplayMode dm;

	private AtomicBoolean isInFullScreenMode = new AtomicBoolean(false);

	private DisplayMode previousDisplayMode;

	private CanvasStage stage;
	private Clock clock;

	/**
	 * Uses default GraphicsDevice
	 * 
	 * @see #FullScreenGameFrame(GraphicsDevice)
	 */
	public FullScreenGameFrame()
	{

		this(GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice());
	}

	/**
	 * Uses default {@link DisplayMode}
	 * 
	 * @see #FullScreenGameFrame(GraphicsDevice, DisplayMode)
	 */
	public FullScreenGameFrame(GraphicsDevice gd)
	{

		this(gd, gd.getDisplayMode());
	}

	/**
	 * Uses default {@link GraphicsConfiguration}
	 * 
	 * @see #FullScreenGameFrame(GraphicsDevice, GraphicsConfiguration, DisplayMode)
	 */
	public FullScreenGameFrame(GraphicsDevice gd, DisplayMode dm)
	{

		this(gd, gd.getDefaultConfiguration(), dm);
	}

	/**
	 * 
	 * @param gd {@link GraphicsDevice} used for rendering
	 * @param gc {@link GraphicsConfiguration} used for {@link Window}
	 * @param dm {@link DisplayMode} used for rendering
	 */
	public FullScreenGameFrame(GraphicsDevice gd, GraphicsConfiguration gc, DisplayMode dm)
	{

		this.window = new Window(null, gc);

		this.gd = gd;
		this.dm = dm;

		initialize();
	}

	private void initialize()
	{

		window.setAlwaysOnTop(true);
		window.setIgnoreRepaint(true);
		setDefaultIcon();

		stage = new CanvasStage();
		stage.setScene(new LoadingScene());
		window.add(stage, BorderLayout.CENTER);

		clock = new Clock();
		clock.addClockListener(stage);
		clock.start();

		window.setLayout(new BorderLayout());
	}

	private void setDefaultIcon()
	{
		try
		{
			window.setIconImage(ImageIO.read(SwingGameFrame.class.getResource("/game/engine/media/icon.png")));
		}
		catch (IOException e)
		{
			window.setIconImage(new EmptyImage.AlphaImage());
		}
	}

	/**
	 * Change DisplayMode when in full screen exclusive mode
	 * 
	 * @param dm {@link DisplayMode}
	 * @return true if it was successful
	 */
	public boolean setDisplayMode(DisplayMode dm)
	{

		this.dm = dm;

		if (isInFullScreenMode.get() && gd.isDisplayChangeSupported())
		{

			gd.setDisplayMode(dm);

			return true;
		}

		return false;
	}

	/**
	 * Check if this Window is in fullscreen exclusive mode
	 * 
	 * @return true if in fullscreen exclusive mode
	 */
	public boolean isInExclusiveFullscreenMode()
	{

		return isInFullScreenMode.get();
	}

	/**
	 * Set invisible and dispose window
	 * 
	 * @see Window#dispose()
	 */
	public void dispose()
	{

		try
		{
			setVisible(false);
			window.dispose();
		}
		catch (Exception e)
		{
		}
		finally
		{

			clock.destroy();
		}
	}

	@Override
	protected void finalize() throws Throwable
	{

		try
		{
			clock.destroy();
		}
		catch (Exception e)
		{
		}
		finally
		{

			super.finalize();
		}
	}

	public void setVisible(boolean visible)
	{
		if (visible)
		{

			show();
		}
		else
		{

			hide();
		}
	}

	private void show()
	{

		if (gd.isFullScreenSupported() && !isInFullScreenMode.get())
		{

			previousDisplayMode = gd.getDisplayMode();

			gd.setFullScreenWindow(window);
			gd.setDisplayMode(dm);

			isInFullScreenMode.set(true);
		}
		else
		{

			setVisible(true);
		}
	}

	private void hide()
	{

		if (isInFullScreenMode.get())
		{

			gd.setDisplayMode(previousDisplayMode);
			gd.setFullScreenWindow(null);

			isInFullScreenMode.set(false);
		}
		else
		{

			setVisible(false);
		}
	}

	/**
	 * Get the stage of this game frame.
	 * 
	 * @return Stage of this frame
	 */
	public CanvasStage getStage()
	{

		return stage;
	}

	/**
	 * Change the {@link SwingStage} that is used by this {@link SwingGameFrame}. Normally there's
	 * no reason to change the default stage.
	 * 
	 * @param stage
	 */
	public void setStage(CanvasStage stage)
	{

		if (stage == null)
		{
			throw new IllegalArgumentException("Null value not allowed");
		}
		clock.removeClockListener(this.stage);
		window.remove(this.stage);

		this.stage = stage;
		window.add(this.stage, BorderLayout.CENTER);
		clock.addClockListener(stage);
	}

	/**
	 * @see SwingStage#setScene(Scene)
	 */
	public void setScene(Scene scene)
	{

		getStage().setScene(scene);
	}

	/**
	 * @see SwingStage#getScene()
	 */
	public Scene getScene()
	{

		return getStage().getScene();
	}

	/**
	 * @see Clock#setFramesPerSecond(int)
	 */
	public void setFramesPerSecond(int framesPerSecond)
	{

		clock.setFramesPerSecond(framesPerSecond);
	}

	/**
	 * @see Clock#getFramesPerSecond()
	 */
	public double getFramesPerSecond()
	{

		return clock.getFramesPerSecond();
	}

	/**
	 * @see Window#addKeyListener(KeyListener)
	 */
	public void addKeyListener(KeyListener l)
	{

		window.addKeyListener(l);
	}

	/**
	 * @see Window#addMouseListener(MouseListener)
	 */
	public void addMouseListener(MouseListener l)
	{

		window.addMouseListener(l);
	}

	/**
	 * @see Window#addMouseMotionListener(MouseMotionListener)
	 */
	public void addMouseListener(MouseMotionListener l)
	{

		window.addMouseMotionListener(l);
	}

}
