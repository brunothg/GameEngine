package game.engine.time;

/**
 * Time utility class
 * 
 * @author Marvin Bruns
 *
 */
public class TimeUtils {
	public static final long NANOSECONDS_PER_SECOND = 1_000_000_000;
	public static final long MILLISECONDS_PER_SECOND = 1_000;
	public static final int NANOSECONDS_PER_MILLISECOND = 1_000_000;

	/**
	 * Convert nanoseconds to seconds
	 * 
	 * @param nano
	 *            nanoseconds
	 * @return seconds
	 */
	public static double Seconds(long nano) {
		return nano / (double) NANOSECONDS_PER_SECOND;
	}

	/**
	 * Convert nanoseconds to milliseconds
	 * 
	 * @param nano
	 *            nanoseconds
	 * @return milliseconds
	 */
	public static double Milliseconds(long nano) {
		return nano / (double) NANOSECONDS_PER_MILLISECOND;
	}

	/**
	 * Convert milliseconds to nanoseconds
	 * 
	 * @param milliseconds
	 *            Milliseconds to be converted
	 * @return nanoseconds
	 */
	public static long NanosecondsOfMilliseconds(long milliseconds) {
		return milliseconds * NANOSECONDS_PER_MILLISECOND;
	}

	/**
	 * Convert milliseconds to nanoseconds
	 * 
	 * @param seconds
	 *            Seconds to be converted
	 * @return nanoseconds
	 */
	public static long NanosecondsOfSeconds(long seconds) {
		return seconds * NANOSECONDS_PER_SECOND;
	}
}
