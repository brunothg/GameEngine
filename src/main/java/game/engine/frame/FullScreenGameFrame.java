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
	private GraphicsConfiguration gc;
	private DisplayMode dm;

	private AtomicBoolean isInFullScreenMode = new AtomicBoolean(false);

	public FullScreenGameFrame()
	{

		this(GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice());
	}

	public FullScreenGameFrame(GraphicsDevice gd)
	{

		this(gd, gd.getDisplayMode());
	}

	public FullScreenGameFrame(GraphicsDevice gd, DisplayMode dm)
	{

		this(gd, gd.getDefaultConfiguration(), dm);
	}

	public FullScreenGameFrame(GraphicsDevice gd, GraphicsConfiguration gc, DisplayMode dm)
	{

		this.window = new Window(null, gc);

		this.gd = gd;
		this.gc = gc;
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

	public boolean setDisplayMode(DisplayMode dm)
	{

		this.dm = dm;

		if (isInFullScreenMode.get())
		{

			gd.setDisplayMode(dm);

			return true;
		}

		return false;
	}

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
		// TODO show

		if (gd.isFullScreenSupported() && !isInFullScreenMode.get())
		{

			gd.setFullScreenWindow(window);
			isInFullScreenMode.set(true);
		}
		else
		{

			setVisible(true);
		}
	}

	private void hide()
	{
		// TODO hide

		if (isInFullScreenMode.get())
		{

			gd.setFullScreenWindow(null);
			isInFullScreenMode.set(false);
		}
		else
		{

			setVisible(false);
		}
	}
}
