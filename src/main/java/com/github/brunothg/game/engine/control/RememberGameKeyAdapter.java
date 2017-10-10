package com.github.brunothg.game.engine.control;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

/**
 * {@link KeyAdapter} that tracks detailed information about key events.
 * 
 * @author Marvin Bruns
 *
 */
public class RememberGameKeyAdapter extends KeyAdapter {

	private Map<Integer, KeyInfo> keys = new HashMap<>(255);
	private Map<Integer, KeyInfo> extendedKeys = new HashMap<>(255);

	@Override
	public void keyPressed(KeyEvent e) {
		KeyInfo keyInfo = getKeyInfo(e.getKeyCode());
		keyInfo.setPressCount(keyInfo.getPressCount() + 1);

		KeyInfo extendedKeyInfo = getExtendedKeyInfo(e.getExtendedKeyCode());
		extendedKeyInfo.setPressCount(extendedKeyInfo.getPressCount() + 1);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		KeyInfo keyInfo = getKeyInfo(e.getKeyCode());
		keyInfo.setReleaseCount(keyInfo.getReleaseCount() + 1);

		KeyInfo extendedKeyInfo = getExtendedKeyInfo(e.getExtendedKeyCode());
		extendedKeyInfo.setReleaseCount(extendedKeyInfo.getReleaseCount() + 1);
	}

	/**
	 * Gets the {@link KeyInfo} for a specific keyCode
	 * 
	 * @param keyCode
	 *            The keyCode
	 * @return The {@link KeyInfo} of the keyCode
	 */
	public KeyInfo getKeyInfo(int keyCode) {
		KeyInfo keyInfo = keys.get(keyCode);
		if (keyInfo == null) {
			keyInfo = new KeyInfo(keyCode);
			keys.put(keyCode, keyInfo);
		}
		return keyInfo;
	}

	/**
	 * Gets the {@link KeyInfo} for a specific extendedKeyCode
	 * 
	 * @param keyCode
	 *            The extendedKeyCode
	 * @return The {@link KeyInfo} of the extendedKeyCode
	 */
	public KeyInfo getExtendedKeyInfo(int keyCode) {
		KeyInfo keyInfo = extendedKeys.get(keyCode);
		if (keyInfo == null) {
			keyInfo = new KeyInfo(keyCode);
			extendedKeys.put(keyCode, keyInfo);
		}
		return keyInfo;
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
	 * @return true if {@link KeyEvent#getKeyCode()} - {@link KeyEvent#VK_SHIFT} is
	 *         down
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
	 * @return true if {@link KeyEvent#getKeyCode()} - {@link KeyEvent#VK_ALT} is
	 *         down
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
	 * @return true if {@link KeyEvent#getKeyCode()} - {@link KeyEvent#VK_ALT_GRAPH}
	 *         is down
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
	 * @return true if {@link KeyEvent#getKeyCode()} - {@link KeyEvent#VK_CONTROL}
	 *         is down
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

		return getKeyInfo(keyCode).isDown();
	}

	/**
	 * Check, if a key is currently up
	 * 
	 * @param keyCode
	 *            Value of {@link KeyEvent #getKeyCode()}
	 * @return true if key isn't currently pressed
	 */
	public boolean isKeyUp(int keyCode) {

		return getKeyInfo(keyCode).isUp();
	}

	/**
	 * Check, if a key is currently down
	 * 
	 * @param keyCode
	 *            Value of {@link KeyEvent #getExtendedKeyCode()}
	 * @return true if key is currently pressed
	 */
	public boolean isExtendedKeyDown(int keyCode) {

		return getExtendedKeyInfo(keyCode).isDown();
	}

	/**
	 * Check, if a key is currently up
	 * 
	 * @param keyCode
	 *            Value of {@link KeyEvent #getExtendedKeyCode()}
	 * @return true if key isn't currently pressed
	 */
	public boolean isExtendedKeyUp(int keyCode) {

		return getExtendedKeyInfo(keyCode).isUp();
	}

	/**
	 * This class holds the information about one key.
	 * 
	 * @author Marvin Bruns
	 *
	 */
	protected class KeyInfo {
		private int keyCode;
		private int pressCount;
		private int releaseCount;

		public KeyInfo(int keyCode) {
			this.setKeyCode(keyCode);
			this.setPressCount(0);
			this.setReleaseCount(0);
		}

		public int getKeyCode() {
			return keyCode;
		}

		public void setKeyCode(int keyCode) {
			this.keyCode = keyCode;
		}

		public int getPressCount() {
			return pressCount;
		}

		public void setPressCount(int pressCount) {
			this.pressCount = pressCount;
		}

		public int getReleaseCount() {
			return releaseCount;
		}

		public void setReleaseCount(int releaseCount) {
			this.releaseCount = releaseCount;
		}

		public int getTypeCount() {
			return Math.min(getReleaseCount(), getPressCount());
		}

		public boolean isDown() {
			return getReleaseCount() < getPressCount();
		}

		public boolean isUp() {
			return !isDown();
		}
	}
}
