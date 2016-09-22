package com.github.brunothg.game.engine.d2.object.sprite;

import com.github.brunothg.game.engine.d2.commons.Point;
import com.github.brunothg.game.engine.d2.commons.Size;
import com.github.brunothg.game.engine.d2.object.SceneObject;
import com.github.brunothg.game.engine.image.sprite.Sprite;

import java.awt.Graphics2D;

/**
 * Animates a sprite. Every row is treated as a single animation and can be switched.
 * 
 * @author Marvin Bruns
 *
 */
public class SpriteSceneObject extends SceneObject
{

	private Sprite sprite;
	private int[] frames;

	private long defaultTime;
	private long[][] time;

	private long timeBase;
	private int frame;

	private int animation;

	/**
	 * Create an {@link SpriteSceneObject} from a sprite. Only one row will be used for animation.
	 * The row used for this animation can be changed.
	 * 
	 * @param sprite The animated sprite
	 * @param defaultTime Nanosecond for animation used if no individual time is provided
	 * @param time Time for every frame. Long[row][frame]. Zero means stable image, it won't be
	 *        animated.
	 * @param frames An array containing the number of frames used for every row. If negative or too
	 *        high the sprite's maximum frame number is used.
	 */
	public SpriteSceneObject(Sprite sprite, long defaultTime, long[][] time, int[] frames)
	{

		if (defaultTime < 0)
		{
			throw new IllegalArgumentException("Negative default time is not allowed");
		}

		this.sprite = sprite;
		this.defaultTime = defaultTime;
		this.time = time;
		this.frames = frames;
		this.timeBase = 0;
		this.frame = 0;

		setAnimationRow(0);
		setSize(sprite.getTileWidth(), sprite.getTileHeight());
	}

	/**
	 * Create an {@link SpriteSceneObject} from a sprite with full frames per row.
	 * 
	 * @see #SpriteSceneObject(Sprite, long, long[][], int[])
	 * @param sprite The animated sprite
	 * @param defaultTime Time for each frame where not specified
	 * @param time individual time for every frame
	 */
	public SpriteSceneObject(Sprite sprite, long defaultTime, long[][] time)
	{

		this(sprite, defaultTime, time, null);
	}

	/**
	 * Create an {@link SpriteSceneObject} from a sprite with equal time for every frame.
	 * 
	 * @see #SpriteSceneObject(Sprite, long, long[][])
	 * @param sprite The animated sprite
	 * @param defaultTime Time for each frame
	 */
	public SpriteSceneObject(Sprite sprite, long defaultTime)
	{

		this(sprite, defaultTime, null, null);
	}

	/**
	 * Change animation row.
	 * 
	 * @param row The row of the sprite used for animation
	 * @param reset if true animation will be
	 * @see #resetAnimation()
	 */
	public void setAnimationRow(int row, boolean reset)
	{

		if (row < 0 || row >= getAnimationRowCount())
		{
			throw new ArrayIndexOutOfBoundsException(
				"animation out of bounds. Animations -> " + getAnimationRowCount());
		}

		animation = row;

		if (reset)
		{

			resetAnimation();
		}
	}

	/**
	 * Change animation row and reset the animation.
	 * 
	 * @param row The row of the sprite used for animation
	 * @see #setAnimationRow(int, boolean)
	 */
	public void setAnimationRow(int row)
	{

		setAnimationRow(row, true);
	}

	/**
	 * Get the current row used for animation
	 * 
	 * @return The row used for animation
	 */
	public int getAnimationRow()
	{

		return animation;
	}

	/**
	 * Get the number of rows in this animation
	 * 
	 * @return Number of animation rows
	 */
	public int getAnimationRowCount()
	{

		return getSprite().getRows();
	}

	/**
	 * Get the frame of the current row in animation
	 * 
	 * @return Current animated frame in row
	 */
	public int getAnimationFrame()
	{

		return frame;
	}

	/**
	 * Set the frame of the current row in animation. If it is greater than the available frames the
	 * next frame in animation is chosen. Remember, that this will it will take affect when the
	 * animation is painted the next time. So if the next frame is this which should be painted you
	 * might set to the frame before.
	 * 
	 * @param frame The current animated frame in row
	 */
	public void setAnimationFrame(int frame)
	{

		if (frame < 0)
		{
			throw new IllegalArgumentException("Negative frame numbers not allowed");
		}

		this.frame = frame;
	}

	/**
	 * Resets actual animation. The first frame in actual row will be painted next time.
	 */
	public void resetAnimation()
	{

		setAnimationFrame(0);
		timeBase = 0;
	}

	/**
	 * Get the height of one frame of the underlying sprite.
	 * 
	 * @return Height of one original frame
	 */
	public int getTileHeight()
	{

		return sprite.getTileHeight();
	}

	/**
	 * Get the width of one frame of the underlying sprite.
	 * 
	 * @return Width of one original frame
	 */
	public int getTileWidth()
	{

		return sprite.getTileWidth();
	}

	/**
	 * Get the size of one frame of the underlying sprite.
	 * 
	 * @return Dimension of one original frame
	 */
	public Size getTileSize()
	{

		return new Size(sprite.getTileWidth(), sprite.getTileHeight());
	}

	@Override
	protected void paint(Graphics2D g, long elapsedTime)
	{

		if (getTime(getAnimationRow(), frame) > 0)
		{
			recalculateFrame(elapsedTime);
		}

		getSprite().drawTile(g, frame, getAnimationRow(), getWidth(), getHeight());
	}

	/**
	 * 
	 * Recalculates the current visible frame depending on the elapsed time. Row restarts if it
	 * reaches end an the last frame has a time &gt; 0.
	 */
	protected void recalculateFrame(long elapsedTime)
	{
		timeBase += elapsedTime;

		long tempTime;
		while (timeBase >= (tempTime = getTime(getAnimationRow(), getAnimationFrame())) && tempTime != 0)
		{

			timeBase -= tempTime;
			setAnimationFrame(getAnimationFrame() + 1);
			if (getAnimationFrame() >= getFrameCount(getAnimationRow()))
			{
				setAnimationFrame(0);
			}
		}
	}

	/**
	 * Animation time for a frame
	 */
	protected long getTime(int row, int frame)
	{

		if (time != null && row < time.length && frame < time[row].length)
		{

			long individuelTime = time[row][frame];

			if (individuelTime >= 0)
			{

				return individuelTime;
			}
		}

		return defaultTime;
	}

	/**
	 * Number of used frames of a row
	 */
	protected int getFrameCount(int row)
	{

		if (frames != null && row < frames.length)
		{

			int frameCount = frames[row];

			if (frameCount >= 0 && frameCount < sprite.getColumns())
			{

				return frameCount;
			}
		}

		return sprite.getColumns();
	}

	protected Sprite getSprite()
	{

		return sprite;
	}

	@Override
	public Point getOrigin()
	{

		return ORIGIN_TOP_LEFT;
	}

}
