package com.github.brunothg.game.engine.d2.scene;

import com.github.brunothg.game.engine.time.FPS;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.EventListener;

/**
 * This scene is an overlay and paints the fps rate in the upper left corner.
 * 
 * @author Marvin Bruns
 */
public class FPSScene implements Scene
{

	private Scene scene;
	private Color textColor;
	private Font font;
	private FPS fps;

	/**
	 * New Overlay fps scene.
	 * 
	 * @param scene Underlying scene
	 */
	public FPSScene(Scene scene)
	{
		this.scene = scene;
		this.fps = new FPS();
		this.textColor = Color.BLACK;
		this.font = new Font(Font.SANS_SERIF, Font.BOLD, 12);
	}

	@Override
	public void paintScene(Graphics2D g, int width, int height, long elapsedTime)
	{

		if (scene != null)
		{
			scene.paintScene(g, width, height, elapsedTime);
		}
		paintFPS(g);

		fps.update(elapsedTime);
	}

	private void paintFPS(Graphics2D g)
	{

		float fps = this.fps.getFps();

		if (Float.isNaN(fps) || Float.isInfinite(fps))
		{
			fps = 0;
		}

		g.setColor(textColor);
		g.setFont(font);
		g.drawString(String.format("%.2f FPS", fps), 2, 2 + g.getFontMetrics().getHeight());
	}

	public Color getTextColor()
	{
		return textColor;
	}

	public void setTextColor(Color textColor)
	{
		if (textColor == null)
		{
			return;
		}

		this.textColor = textColor;
	}

	public Font getFont()
	{
		return font;
	}

	public void setFont(Font font)
	{
		if (font == null)
		{
			return;
		}

		this.font = font;
	}

	@Override
	public EventListener[] getEventListeners()
	{

		if (scene != null)
		{

			return scene.getEventListeners();
		}

		return null;
	}

}
