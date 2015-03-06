package game.engine.time;

import javax.swing.event.EventListenerList;

/**
 * 
 * A FPS pulser.
 * 
 * @author Marvin Bruns
 *
 */
public class Clock extends Thread {

	public static final int FPS_PAUSE = 0;
	public static final int FPS_VERY_SLOW = 10;
	public static final int FPS_SLOW = 20;
	public static final int FPS_MODERATE = 30;
	public static final int FPS_FAST = 60;
	public static final int FPS_AS_FAST_AS_POSSIBLE = -1;

	private static final int STATE_RUNNING = 0x1;
	private static final int STATE_PAUSED = 0x2;
	private static final int STATE_TERMINATED = 0x3;

	private EventListenerList listenerList = new EventListenerList();

	private volatile double framesPerSecond;
	private volatile long nanosecondsPerFrame;
	private volatile long time;
	private volatile long elapsedTime;

	private volatile int state;

	private Object pauseLock = new Object();

	public Clock() {

		this(FPS_MODERATE);
	}

	public Clock(int framesPerSecond) {

		setFramesPerSecond(framesPerSecond);
	}

	@Override
	public void run() {

		setTime(System.nanoTime());

		while (!isInterrupted()) {
			int state = getStatus();

			if (state == STATE_PAUSED) {

				synchronized (pauseLock) {
					try {
						pauseLock.wait();
					} catch (InterruptedException e) {
						interrupt();
					}
				}

			} else if (state == STATE_RUNNING) {

				running();
			}
		}

		setStatus(STATE_TERMINATED);
	}

	private void running() {

		long nanosecondsPerFrame;
		long time;

		// Fetch all synchronized values
		synchronized (this) {
			nanosecondsPerFrame = getNanosecondsPerFrame();
			time = getTime();
		}

		// Calculate time values
		elapsedTime += System.nanoTime() - time;

		long frames = elapsedTime / nanosecondsPerFrame;
		long coveredTime = frames * nanosecondsPerFrame;

		if (frames > 0) {
			tick(frames, coveredTime);
		}

		// Prepare for next round
		elapsedTime -= coveredTime;
		setTime(System.nanoTime());
	}

	/**
	 * Clock tick
	 * 
	 * @param frames
	 *            Full frames covered by this tick
	 * @param coveredTime
	 *            Time covered by the full frames
	 */
	private void tick(long frames, long coveredTime) {
		synchronized (listenerList) {
			ClockListener[] listeners = listenerList
					.getListeners(ClockListener.class);

			for (ClockListener cl : listeners) {

				cl.tick(frames, coveredTime);
			}
		}
	}

	public void addClockListener(ClockListener cl) {
		synchronized (listenerList) {
			listenerList.add(ClockListener.class, cl);
		}
	}

	public void removeClockListener(ClockListener cl) {
		synchronized (listenerList) {
			listenerList.remove(ClockListener.class, cl);
		}
	}

	/**
	 * Get the frames per second
	 * 
	 * @return FPS or negative value (as fast as possible)
	 */
	public synchronized double getFramesPerSecond() {

		return this.framesPerSecond;
	}

	/**
	 * Set the frames per second.
	 * 
	 * @param framesPerSecond
	 *            FPS or negative(as fast as possible)
	 */
	public synchronized void setFramesPerSecond(double framesPerSecond) {
		if (framesPerSecond == 0) {
			throw new IllegalArgumentException("0 fps rate not allowed");
		}

		this.framesPerSecond = framesPerSecond;
		this.nanosecondsPerFrame = Math
				.round((TimeUtils.NANOSECONDS_PER_SECOND / this.framesPerSecond));
	}

	private synchronized long getNanosecondsPerFrame() {

		return this.nanosecondsPerFrame;
	}

	/**
	 * Pause or resume the clock
	 * 
	 * @param pause
	 *            if true Clock will be paused
	 */
	public synchronized void setPaused(boolean pause) {

		if (pause && getStatus() != STATE_PAUSED) {

			setStatus(STATE_PAUSED);
		} else if (getStatus() != STATE_RUNNING) {

			setStatus(STATE_RUNNING);

			synchronized (pauseLock) {

				pauseLock.notifyAll();
			}

			elapsedTime = 0;
			setTime(System.nanoTime());
		}
	}

	private synchronized void setTime(long nanoTime) {

		this.time = nanoTime;
	}

	private synchronized long getTime() {

		return this.time;
	}

	private synchronized void setStatus(int state) {

		this.state = state;
	}

	private synchronized int getStatus() {

		return this.state;
	}
}
