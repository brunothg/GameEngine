package game.engine.d2.stage;

import game.engine.d2.scene.Scene;
import game.engine.time.ClockListener;

public interface Stage extends ClockListener {

	/**
	 * Change the painted scene.
	 * 
	 * @param scene
	 *            Scene to be staged
	 */
	public void setScene(Scene scene);

	/**
	 * Get the actual painted scene.
	 * 
	 * @return Staged scene or null if none
	 */
	public Scene getScene();
}
