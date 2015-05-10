package game.engine.stage.scene.object;

import game.engine.image.sprite.Sprite;

/**
 * Animates a sprite. The whole sprite is treated as one single animation.
 * 
 * @author Marvin Bruns
 *
 */
public class Sprite2DSceneObject extends SpriteSceneObject {

	private long timeBase;

	public Sprite2DSceneObject(Sprite sprite, long defaultTime, long[][] time) {
		super(sprite, defaultTime, time);
	}

	public Sprite2DSceneObject(Sprite sprite, long defaultTime) {
		super(sprite, defaultTime);
	}

	/**
	 * 
	 * Recalculates the current visible frame depending on the elapsed time.
	 * Sprite restarts if it reaches end an the last frame has a time > 0.
	 */
	protected void recalculateFrame(long elapsedTime) {

		timeBase += elapsedTime;

		long tempTime;
		while (timeBase >= (tempTime = getTime(getAnimationRow(),
				getAnimationFrame())) && tempTime != 0) {

			timeBase -= tempTime;
			setAnimationFrame(getAnimationFrame() + 1);
			if (getAnimationFrame() >= getFrameCount(getAnimationRow())) {
				setAnimationFrame(0);

				int newRow = getAnimationRow() + 1;
				if (newRow >= getAnimationRowCount()) {

					newRow = 0;
				}
				setAnimationRow(newRow, false);
			}
		}
	}

	/**
	 * Resets actual animation. The first frame in actual row will be painted
	 * next time.
	 */
	public void resetAnimation() {

		setAnimationFrame(0);
		timeBase = 0;
	}
}
