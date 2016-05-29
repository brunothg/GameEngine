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
	 * Size to text bounds, if text smaller than container -&gt; /, if text
	 * bigger -&gt; use smaller font size
	 */
	FitSize,
	/**
	 * Size to text bounds, if text smaller than container -&gt; /, if text
	 * height bigger -&gt; use smaller font size
	 */
	FitHeight,
	/**
	 * Size to object bounds, if text smaller -&gt; increase font size, if text
	 * bigger -&gt; use smaller font size
	 */
	FitParent,
	/**
	 * Size to object bounds, if text height smaller -&gt; increase font size,
	 * if text height bigger -&gt; use smaller font size
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
