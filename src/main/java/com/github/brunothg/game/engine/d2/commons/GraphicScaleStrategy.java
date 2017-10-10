package com.github.brunothg.game.engine.d2.commons;

/**
 * 
 * An enumeration used for scaling grpahics.
 * 
 * @author Marvin Bruns
 *
 */
public enum GraphicScaleStrategy {

	/**
	 * Scale to maximum bounds. Original ratio is ignored.
	 */
	Fill,
	/**
	 * Keeps original ratio.
	 */
	Fit,
	/**
	 * No scaling at all
	 */
	NoScale,
	/**
	 * Container chooses strategy
	 */
	Auto;
}
