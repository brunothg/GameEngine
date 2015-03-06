package game.engine.time;

import javax.swing.event.EventListenerList;

/**
 * 
 * A FPS pulser.
 * 
 * @author Marvin Bruns
 *
 */
public class Clock extends Thread
{

	public static final int FPS_AS_FAST_AS_POSSIBLE = -1;
	public static final int FPS_STOP = 0;

	private volatile double framesPerSecond;
	private volatile boolean running;

	private long lastTime;

	private EventListenerList listenerList = new EventListenerList();

	public Clock()
	{
		this(-1);
	}

	public Clock(float framesPerSecond)
	{

		setFramesPerSecond(framesPerSecond);
	}

	public void run()
	{
		running = true;
		setActualTime();

		while (running)
		{
			doWork();
		}

	}

	private void setActualTime()
	{
		lastTime = System.nanoTime();
	}

	/**
	 * Increase time and call listeners by time
	 */
	private void doWork()
	{
		double fps = getFramesPerSecond();

		if (fps == FPS_AS_FAST_AS_POSSIBLE)
		{
			tick();
			return;
		}
		else if (fps == FPS_STOP)
		{
			return;
		}

		long time = System.nanoTime();
		long elapsedTime = time - lastTime;
		int timeForOneFrame = (int) (Time.NANOSECONDS_PER_SECOND / fps);

		if (elapsedTime >= timeForOneFrame)
		{
			long ticks = elapsedTime / timeForOneFrame;

			for (int i = 0; i < ticks && i >= 0; i++)
			{
				tick();
			}

			lastTime += ticks * timeForOneFrame;
		}
		else
		{
			long timeToTick = timeForOneFrame - elapsedTime;

			try
			{
				Thread.sleep(timeToTick / Time.NANOSECONDS_PER_MILLISECOND,
					(int) (timeToTick % Time.NANOSECONDS_PER_MILLISECOND));
			}
			catch (InterruptedException e)
			{
			}
		}
	}

	/**
	 * A new tick occurred. Inform all Listeners.
	 */
	private void tick()
	{
		synchronized (listenerList)
		{
			ClockListener[] listeners = listenerList.getListeners(ClockListener.class);

			for (ClockListener cl : listeners)
			{

				cl.tick();
			}
		}
	}

	public void addClockListener(ClockListener cl)
	{
		synchronized (listenerList)
		{
			listenerList.add(ClockListener.class, cl);
		}
	}

	public void removeClockListener(ClockListener cl)
	{
		synchronized (listenerList)
		{
			listenerList.remove(ClockListener.class, cl);
		}
	}

	public synchronized double getFramesPerSecond()
	{
		return framesPerSecond;
	}

	public synchronized void setFramesPerSecond(double framesPerSecond)
	{
		if (framesPerSecond < -1)
		{
			throw new IllegalArgumentException("Negative fps rate not allowed");
		}

		setActualTime();

		this.framesPerSecond = framesPerSecond;
	}

	@Override
	public void interrupt()
	{
		running = false;

		try
		{
			super.interrupt();
		}
		catch (Exception e)
		{
		}
	}

}
