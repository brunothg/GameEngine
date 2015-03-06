package game.engine.time;

import java.util.EventListener;

public interface ClockListener extends EventListener {
	/**
	 * A new tick occurred. Do your time based work.
	 * 
	 * @param frames
	 *            Full frames covered by this tick
	 * @param coveredTime
	 *            Time covered by the full frames
	 */
	public void tick(long frames, long coveredTime);
}
