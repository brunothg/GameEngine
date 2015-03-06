package game.engine.time;

/**
 * Central time class for measuring elapsed time in nano time.
 * 
 * @author Marvin Bruns
 *
 */
public class Time
{

	public static final long NANOSECONDS_PER_SECOND = 1_000_000_000;
	public static final long MILLISECONDS_PER_SECOND = 1_000;
	public static final int NANOSECONDS_PER_MILLISECOND = 1_000_000;

	private long lastTime;
	private long elapsedTime;

	public Time()
	{
		reset();
	}

	/**
	 * Reset time. Elapsed time is set to 0.
	 */
	public void reset()
	{
		lastTime = System.nanoTime();
		elapsedTime = 0;
	}

	/**
	 * Calculate the elapsed time since the last update or reset.
	 */
	public long update()
	{
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
	public long elapsedTime()
	{
		return elapsedTime;
	}

	/**
	 * Convert nanoseconds to seconds
	 * 
	 * @param nano nanoseconds
	 * @return seconds
	 */
	public static double Seconds(long nano)
	{
		return nano / (double) NANOSECONDS_PER_SECOND;
	}

}
