package com.github.brunothg.game.engine.d2.commons.listener;

/**
 * Listener for exiting scenes
 * 
 * @author Marvin Bruns
 *
 * @param <R>
 *            Exit Value Type
 */
public interface ExitListener<R> {
	public void exit(R result);
}
