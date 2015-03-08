package game.engine.stage.scene.object;

import game.engine.image.sprite.Sprite;

import java.awt.Graphics2D;

/**
 * Animates a sprite. Every row is treated as a single animation and can be
 * switched.
 * 
 * @author Marvin Bruns
 *
 */
public class AnimatedSceneObject extends SceneObject {

	private Sprite sprite;
	private int[] frames;

	private long defaultTime;
	private long[][] time;

	private long timeBase;
	private int frame;

	private int animation;

	/**
	 * Create an {@link AnimatedSceneObject} from a sprite. Only one row will be
	 * used for animation. The row used for this animation can be changed.
	 * 
	 * @param sprite
	 *            The animated sprite
	 * @param defaultTime
	 *            Nanosecond for animation used if no individual time is
	 *            provided
	 * @param time
	 *            Time for every frame. Long[row][frame]
	 * @param frames
	 *            An array containing the number of frames used for every row.
	 *            If negative or too high the sprite's maximum frame number is
	 *            used.
	 */
	public AnimatedSceneObject(Sprite sprite, long defaultTime, long[][] time,
			int[] frames) {

		this.sprite = sprite;
		this.defaultTime = defaultTime;
		this.time = time;
		this.frames = frames;
		this.timeBase = 0;
		this.frame = 0;

		setAnimationRow(0);
		setSize(sprite.getTileWidth(), sprite.getTileHeight());
	}

	public AnimatedSceneObject(Sprite sprite, long defaultTime, long[][] time) {

		this(sprite, defaultTime, time, null);
	}

	/**
	 * Create an {@link AnimatedSceneObject} from a sprite with equal time for
	 * every frame.
	 * 
	 * @see #AnimatedSceneObject(Sprite, long, double[])
	 * @param sprite
	 *            The animated sprite
	 * @param defaultTime
	 *            Time for each frame
	 */
	public AnimatedSceneObject(Sprite sprite, long defaultTime) {

		this(sprite, defaultTime, null, null);
	}

	/**
	 * Change animation row.
	 * 
	 * @param row
	 *            The row of the sprite used for animation
	 * @param reset
	 *            if true animation will be
	 * @see #resetAnimation()
	 */
	public void setAnimationRow(int row, boolean reset) {

		if (row < 0 || row >= getAnimationCount()) {
			throw new ArrayIndexOutOfBoundsException(
					"animation out of bounds. Animations -> "
							+ getAnimationCount());
		}

		animation = row;

		if (reset) {

			resetAnimation();
		}
	}

	/**
	 * Change animation row and reset the animation.
	 * 
	 * @param row
	 *            The row of the sprite used for animation
	 * @see #setAnimationRow(int, boolean)
	 */
	public void setAnimationRow(int row) {

		setAnimationRow(row, true);
	}

	/**
	 * Get the current row used for animation
	 * 
	 * @return The row used for animation
	 */
	public int getAnimationRow() {

		return animation;
	}

	public int getAnimationCount() {

		return sprite.getRows();
	}

	/**
	 * Resets actual animation. The first frame will be painted next time.
	 */
	public void resetAnimation() {

		frame = 0;
		timeBase = 0;
	}

	@Override
	protected void paint(Graphics2D g, long elapsedTime) {

		timeBase += elapsedTime;

		long tempTime;
		while (timeBase >= (tempTime = getTime(getAnimationRow(), frame))) {

			timeBase -= tempTime;
			frame++;
			if (frame >= getFrameCount(getAnimationRow())) {
				frame = 0;
			}
		}

		sprite.drawTile(g, frame, getAnimationRow(), getWidth(), getHeight());
	}

	/**
	 * Animation time for a frame
	 */
	protected long getTime(int row, int frame) {

		if (time != null && frame < time.length) {

			return time[row][frame];
		}

		return defaultTime;
	}

	/**
	 * Number of used frames of a row
	 */
	protected int getFrameCount(int row) {

		if (frames != null && row < frames.length) {

			int frameCount = frames[row];

			if (frameCount >= 0 && frameCount < sprite.getColumns()) {

				return frameCount;
			}
		}

		return sprite.getColumns();
	}

	@Override
	public Point getOrigin() {

		return ORIGIN_TOP_LEFT;
	}

}
