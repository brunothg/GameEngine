package game.engine.frame;

import game.engine.image.EmptyImage;

import java.awt.DisplayMode;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Window;
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

		setVisible(false);
		window.dispose();
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
}
