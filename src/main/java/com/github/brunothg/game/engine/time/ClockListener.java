package com.github.brunothg.game.engine.time;

import java.util.EventListener;

public interface ClockListener extends EventListener
{
	/**
	 * A new tick occurred. Do your time based work. A clock will call this method from a thread.
	 * Think of synchronization etc.
	 * 
	 * @param frames Full frames covered by this tick
	 * @param coveredTime Time covered by the full frames (nanoseconds)
	 */
	public void tick(long frames, long coveredTime);
}
