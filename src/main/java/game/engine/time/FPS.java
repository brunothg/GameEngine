package game.engine.time;

/**
 * Helper class for monotoring the fps rate
 * 
 * @author Marvin Bruns
 *
 */
public class FPS {

	private long fpsTime = TimeUtils.NANOSECONDS_PER_SECOND;

	private int fps = 0;
	private int fpsCounter = 0;

	/**
	 * Update the fps. Call this every time a frame is drawn.
	 * 
	 * @param elapsedTime
	 *            The elapsed time since last drawing cycle in nanoseconds
	 */
	public void update(long elapsedTime) {

		fpsTime -= elapsedTime;
		fpsCounter++;

		if (fpsTime < 0) {
			fps = fpsCounter;

			reset();
		}
	}

	/**
	 * Resets the fps rate and counter
	 */
	public void reset() {

		fpsTime = TimeUtils.NANOSECONDS_PER_SECOND;
		fpsCounter = 0;
	}

	public int getFps() {
		return fps;
	}
}
