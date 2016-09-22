package com.github.brunothg.game.engine.d2.stage;

import com.github.brunothg.game.engine.d2.scene.Scene;
import com.github.brunothg.game.engine.image.NullGraphics;

import java.awt.Canvas;
import java.awt.Graphics2D;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferStrategy;
import java.util.EventListener;

/**
 * Stage using {@link Canvas} for drawing. If supported draws images using page flipping.<br>
 * Before using this you should test it. With some JVM implementations this will fail.
 * 
 * @author Marvin Bruns
 *
 */
public class CanvasStage extends Canvas implements Stage
{

	private static final long serialVersionUID = 1L;

	private volatile Scene scene;
	private Object sceneLock = new Object();

	private BufferStrategy bufferStrategy;

	private EventListener[] sceneListener;

	public CanvasStage()
	{

		setIgnoreRepaint(true);
	}

	@Override
	public void tick(long frames, long coveredTime)
	{

		boolean isNullGraphic = false;
		Graphics2D g2d;

		if (!isReadyForDrawing())
		{

			g2d = new NullGraphics();
			isNullGraphic = true;
		}
		else
		{

			g2d = (Graphics2D) bufferStrategy.getDrawGraphics();
		}

		int width = getWidth();
		int height = getHeight();

		synchronized (sceneLock)
		{

			Scene scene = getScene();

			if (isNullGraphic)
			{
				try
				{
					scene.paintScene(g2d, width, height, coveredTime);
				}
				catch (Throwable t)
				{
				}
			}
			else
			{
				scene.paintScene(g2d, width, height, coveredTime);
			}
		}

		g2d.dispose();

		if (!isNullGraphic)
		{
			bufferStrategy.show();
		}
	}

	private boolean isReadyForDrawing()
	{

		if (bufferStrategy == null && isDisplayable())
		{

			createBufferStrategy(2);
			bufferStrategy = getBufferStrategy();
		}
		else if (bufferStrategy == null && !isDisplayable())
		{

			return false;
		}

		return true;
	}

	@Override
	public void setScene(Scene scene)
	{

		synchronized (sceneLock)
		{

			recycleScene();

			this.scene = scene;
			registerEventListeners();
		}
	}

	@Override
	public Scene getScene()
	{

		synchronized (sceneLock)
		{

			return this.scene;
		}
	}

	/**
	 * Register scene's EventListeners
	 */
	private void registerEventListeners()
	{

		sceneListener = getScene().getEventListeners();

		if (sceneListener == null)
		{
			return;
		}

		for (int i = 0; i < sceneListener.length; i++)
		{

			EventListener evl = sceneListener[i];
			addEventListener(evl);
		}
	}

	/**
	 * Remove all registered EventListeners
	 */
	private void recycleScene()
	{

		if (sceneListener == null)
		{
			return;
		}

		for (int i = 0; i < sceneListener.length; i++)
		{

			EventListener evl = sceneListener[i];
			removeEventListener(evl);
		}
	}

	private void addEventListener(EventListener evl)
	{

		if (KeyListener.class.isInstance(evl))
		{
			addKeyListener((KeyListener) evl);
		}

		if (MouseListener.class.isInstance(evl))
		{
			addMouseListener((MouseListener) evl);
		}

		if (MouseMotionListener.class.isInstance(evl))
		{
			addMouseMotionListener((MouseMotionListener) evl);
		}
	}

	private void removeEventListener(EventListener evl)
	{

		if (KeyListener.class.isInstance(evl))
		{
			removeKeyListener((KeyListener) evl);
		}

		if (MouseListener.class.isInstance(evl))
		{
			removeMouseListener((MouseListener) evl);
		}

		if (MouseMotionListener.class.isInstance(evl))
		{
			removeMouseMotionListener((MouseMotionListener) evl);
		}
	}
}
