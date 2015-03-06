package game.engine.time;

import java.util.EventListener;

public interface ClockListener extends EventListener
{
	/**
	 * A new tick occurred. Do your time based work.
	 */
	public void tick();
}
