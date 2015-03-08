package game.engine.stage.scene.object;

import game.engine.image.sprite.Sprite;

import java.awt.Graphics2D;

/**
 * Animates a sprite.
 * 
 * @author Marvin Bruns
 *
 */
public class AnimatedSceneObject extends SceneObject {

	private Sprite sprite;

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
	 */
	public AnimatedSceneObject(Sprite sprite, long defaultTime, long[][] time) {

		this.sprite = sprite;
		this.defaultTime = defaultTime;
		this.time = time;
		this.timeBase = 0;
		this.frame = 0;

		setAnimationRow(0);
		setSize(sprite.getTileWidth(), sprite.getTileHeight());
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

		this(sprite, defaultTime, null);
	}

	/**
	 * Change animation row
	 * 
	 * @param row
	 *            The row of the sprite used for animation
	 */
	public void setAnimationRow(int row) {

		if (row < 0 || row >= getAnimationCount()) {
			throw new ArrayIndexOutOfBoundsException(
					"animation out of bounds. Animations -> "
							+ getAnimationCount());
		}

		animation = row;
		resetAnimation();
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
			if (frame >= sprite.getColumns()) {
				frame = 0;
			}
		}

		sprite.drawTile(g, frame, getAnimationRow(), getWidth(), getHeight());
	}

	protected long getTime(int row, int frame) {

		if (time != null && frame < time.length) {

			return time[row][frame];
		}

		return defaultTime;
	}

	@Override
	public Point getOrigin() {

		return ORIGIN_TOP_LEFT;
	}

}
