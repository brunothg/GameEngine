package game.engine.time;

/**
 * Central timer class for measuring elapsed time in nano time.
 * 
 * @author Marvin Bruns
 *
 */
public class Timer {

	private long lastTime;
	private long elapsedTime;

	public Timer() {
		reset();
	}

	/**
	 * Reset time. Elapsed time is set to 0.
	 */
	public void reset() {
		lastTime = System.nanoTime();
		elapsedTime = 0;
	}

	/**
	 * Calculate the elapsed time since the last update or reset.
	 */
	public long update() {
		long time = System.nanoTime();
		elapsedTime = time - lastTime;
		lastTime = time;

		return elapsedTime;
	}

	/**
	 * Get the elapsed time of last update. For actual values do a new update.
	 * 
	 * @see #update()
	 * 
	 * @return elapsed time
	 */
	public long elapsedTime() {
		return elapsedTime;
	}

}
