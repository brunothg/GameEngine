package game.engine.d2.commons;

/**
 * 
 * An enumeration used for scaling the font size.
 * 
 * @author Marvin Bruns
 *
 */
public enum FontScaleStrategy {

	/**
	 * Size to text bounds, if text smaller than container -> /, if text bigger
	 * -> use smaller font size
	 */
	FitSize,
	/**
	 * Size to text bounds, if text smaller than container -> /, if text height
	 * bigger -> use smaller font size
	 */
	FitHeight,
	/**
	 * Size to object bounds, if text smaller -> increase font size, if text
	 * bigger -> use smaller font size
	 */
	FitParent,
	/**
	 * Size to object bounds, if text height smaller -> increase font size, if
	 * text height bigger -> use smaller font size
	 */
	FitParentHeight,
	/**
	 * No scaling at all
	 */
	NoScale,
	/**
	 * Container chooses strategy
	 */
	Auto;
}
