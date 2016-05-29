package game.engine.control;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;

/**
 * {@link KeyAdapter} that tracks key positions.
 * 
 * @author Marvin Bruns
 *
 */
public class GameKeyAdapter extends KeyAdapter {

	private Set<Integer> keys = new HashSet<Integer>();
	private Set<Integer> extendedKeys = new HashSet<Integer>();

	@Override
	public void keyPressed(KeyEvent e) {

		keys.add(e.getKeyCode());
		extendedKeys.add(e.getExtendedKeyCode());
	}

	@Override
	public void keyReleased(KeyEvent e) {

		keys.remove(e.getKeyCode());
		extendedKeys.remove(e.getExtendedKeyCode());
	}

	/**
	 * Call for {@link #isShift(boolean)} with false
	 * 
	 * @see #isShift(boolean)
	 */
	public boolean isShift() {

		return isShift(false);
	}

	/**
	 * Check if shift is down
	 * 
	 * @return true if {@link KeyEvent#getKeyCode()} - {@link KeyEvent#VK_SHIFT}
	 *         is down
	 */
	public boolean isShift(boolean extended) {

		if (extended) {

			return isExtendedKeyDown(KeyEvent.VK_SHIFT);
		}

		return isKeyDown(KeyEvent.VK_SHIFT);
	}

	/**
	 * Call for {@link #isAlt(boolean)} with false
	 * 
	 * @see #isAlt(boolean)
	 */
	public boolean isAlt() {

		return isAlt(false);
	}

	/**
	 * Check if alt is down
	 * 
	 * @return true if {@link KeyEvent#getKeyCode()} - {@link KeyEvent#VK_ALT}
	 *         is down
	 */
	public boolean isAlt(boolean extended) {

		if (extended) {

			return isExtendedKeyDown(KeyEvent.VK_ALT);
		}

		return isKeyDown(KeyEvent.VK_ALT);
	}

	/**
	 * Call for {@link #isAltGr(boolean)} with false
	 * 
	 * @see #isAltGr(boolean)
	 */
	public boolean isAltGr() {

		return isAltGr(false);
	}

	/**
	 * Check if alt_gr is down
	 * 
	 * @return true if {@link KeyEvent#getKeyCode()} -
	 *         {@link KeyEvent#VK_ALT_GRAPH} is down
	 */
	public boolean isAltGr(boolean extended) {

		if (extended) {

			return isExtendedKeyDown(KeyEvent.VK_ALT_GRAPH);
		}

		return isKeyDown(KeyEvent.VK_ALT_GRAPH);
	}

	/**
	 * Call for {@link #isCtrl(boolean)} with false
	 * 
	 * @see #isCtrl(boolean)
	 */
	public boolean isCtrl() {

		return isCtrl(false);
	}

	/**
	 * Check if ctrl is down
	 * 
	 * @return true if {@link KeyEvent#getKeyCode()} -
	 *         {@link KeyEvent#VK_CONTROL} is down
	 */
	public boolean isCtrl(boolean extended) {

		if (extended) {

			return isExtendedKeyDown(KeyEvent.VK_CONTROL);
		}

		return isKeyDown(KeyEvent.VK_CONTROL);
	}

	/**
	 * Check, if a key is currently down
	 * 
	 * @param keyCode
	 *            Value of {@link KeyEvent #getKeyCode()}
	 * @return true if key is currently pressed
	 */
	public boolean isKeyDown(int keyCode) {

		return keys.contains(keyCode);
	}

	/**
	 * Check, if a key is currently up
	 * 
	 * @param keyCode
	 *            Value of {@link KeyEvent #getKeyCode()}
	 * @return true if key isn't currently pressed
	 */
	public boolean isKeyUp(int keyCode) {

		return !isKeyDown(keyCode);
	}

	/**
	 * Check, if a key is currently down
	 * 
	 * @param keyCode
	 *            Value of {@link KeyEvent #getExtendedKeyCode()}
	 * @return true if key is currently pressed
	 */
	public boolean isExtendedKeyDown(int keyCode) {

		return extendedKeys.contains(keyCode);
	}

	/**
	 * Check, if a key is currently up
	 * 
	 * @param keyCode
	 *            Value of {@link KeyEvent #getExtendedKeyCode()}
	 * @return true if key isn't currently pressed
	 */
	public boolean isExtendedKeyUp(int keyCode) {

		return !isExtendedKeyDown(keyCode);
	}
}
