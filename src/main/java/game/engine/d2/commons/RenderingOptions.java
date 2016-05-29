package game.engine.d2.commons;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.RenderingHints.Key;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import game.engine.exception.IncompatibleValueException;

/**
 * A class holding several rendering settings, that can be used for configuring
 * {@link Graphics2D} objects.
 * 
 * @author Marvin Bruns
 *
 */
public class RenderingOptions extends HashMap<RenderingHints.Key, Object> {
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = LoggerFactory
			.getLogger(RenderingOptions.class);

	/**
	 * No options set
	 */
	public RenderingOptions() {
	}

	/**
	 * Copies the options defined by the given {@link Graphics} object.
	 * 
	 * @param g
	 *            {@link Graphics} object used for initial configuration
	 */
	public RenderingOptions(Graphics g) {
		copyOptionsFrom(g);
	}

	/**
	 * Try to cast to {@link Graphics2D} and then copy its options
	 * 
	 * @param g
	 *            {@link Graphics2D} used for copying
	 * @see #copyOptionsFrom(Graphics2D)
	 */
	public void copyOptionsFrom(Graphics g) {
		if (g instanceof Graphics2D) {
			copyOptionsFrom((Graphics2D) g);
		}
	}

	/**
	 * Copy options of the given {@link Graphics2D} object.
	 * 
	 * @param g
	 *            {@link Graphics2D} used for copying
	 */
	public void copyOptionsFrom(Graphics2D g) {
		RenderingHints options = g.getRenderingHints();
		for (java.util.Map.Entry<Object, Object> option : options.entrySet()) {
			Object key = option.getKey();
			Object value = option.getValue();
			if (key instanceof RenderingHints.Key) {
				this.put((RenderingHints.Key) key, value);
			} else {
				LOG.warn(
						"Could not copy option ({} -> {}). Should be of type {}",
						key, value, RenderingHints.Key.class);
			}
		}
	}

	@Override
	public void putAll(Map<? extends Key, ? extends Object> m) {
		for (java.util.Map.Entry<? extends Key, ? extends Object> entry : m
				.entrySet()) {
			this.put(entry.getKey(), entry.getValue());
		}
	}

	/**
	 * Set the rendering state for {@link Key}. Only values accepted by this
	 * {@link Key} are allowed.
	 * 
	 * @see Key#isCompatibleValue(Object)
	 */
	public Object put(Key key, Object value) {
		if (!key.isCompatibleValue(value)) {
			throw new IncompatibleValueException(String.format(
					"For (%s) the value (%s) can not be used. See Key.isCompatibleValue.",
					key.toString(), value.toString()));
		}
		return super.put(key, value);
	}

	/**
	 * Sets the anti aliasing option. If null is passed, the default behavior
	 * will be activated.
	 * 
	 * @param on
	 *            The anti aliasing status used.
	 * @see RenderingHints#KEY_ANTIALIASING
	 */
	public void setAntiAliasing(Boolean on) {
		Object value;
		if (on == null) {
			value = RenderingHints.VALUE_ANTIALIAS_DEFAULT;
		} else if (on == true) {
			value = RenderingHints.VALUE_ANTIALIAS_ON;
		} else {
			value = RenderingHints.VALUE_ANTIALIAS_OFF;
		}
		put(RenderingHints.KEY_ANTIALIASING, value);
	}

	/**
	 * Sets the anti aliasing option for text drawing. If null is passed, the
	 * default behavior will be activated. More options may be available using
	 * {@link #put(Key, Object)} method.
	 * 
	 * @param on
	 *            The anti aliasing status used.
	 * @see RenderingHints#KEY_TEXT_ANTIALIASING
	 */
	public void setAntiAliasingForText(Boolean on) {
		Object value;
		if (on == null) {
			value = RenderingHints.VALUE_TEXT_ANTIALIAS_DEFAULT;
		} else if (on == true) {
			value = RenderingHints.VALUE_TEXT_ANTIALIAS_ON;
		} else {
			value = RenderingHints.VALUE_TEXT_ANTIALIAS_OFF;
		}
		put(RenderingHints.KEY_TEXT_ANTIALIASING, value);
	}

	/**
	 * Sets the fractional metrics option. If null is passed, the default
	 * behavior will be activated.
	 * 
	 * @param on
	 *            The fractional metrics status used.
	 * @see RenderingHints#KEY_FRACTIONALMETRICS
	 */
	public void setFractionalMetrics(Boolean on) {
		Object value;
		if (on == null) {
			value = RenderingHints.VALUE_FRACTIONALMETRICS_DEFAULT;
		} else if (on == true) {
			value = RenderingHints.VALUE_FRACTIONALMETRICS_ON;
		} else {
			value = RenderingHints.VALUE_FRACTIONALMETRICS_OFF;
		}
		put(RenderingHints.KEY_FRACTIONALMETRICS, value);
	}

	/**
	 * Sets the stroke control option. If null is passed, the default behavior
	 * will be activated. If true, strokes will be normalized.
	 * 
	 * @param on
	 *            The stroke control status used.
	 * @see RenderingHints#KEY_STROKE_CONTROL
	 */
	public void setStrokeControl(Boolean on) {
		Object value;
		if (on == null) {
			value = RenderingHints.VALUE_STROKE_DEFAULT;
		} else if (on == true) {
			value = RenderingHints.VALUE_STROKE_NORMALIZE;
		} else {
			value = RenderingHints.VALUE_STROKE_PURE;
		}
		put(RenderingHints.KEY_STROKE_CONTROL, value);
	}

	/**
	 * Sets the dithering option. If null is passed, the default behavior will
	 * be activated.
	 * 
	 * @param on
	 *            The dithering status used.
	 * @see RenderingHints#KEY_DITHERING
	 */
	public void setDithering(Boolean on) {
		Object value;
		if (on == null) {
			value = RenderingHints.VALUE_DITHER_DEFAULT;
		} else if (on == true) {
			value = RenderingHints.VALUE_DITHER_ENABLE;
		} else {
			value = RenderingHints.VALUE_DITHER_DISABLE;
		}
		put(RenderingHints.KEY_DITHERING, value);
	}

	/**
	 * Sets the enhanced color rendering option. If null is passed, the default
	 * behavior will be activated. If true, the color rendering will try to
	 * produce the best possible result, but may be much slower.
	 * 
	 * @param on
	 *            The enhanced status status used.
	 * @see RenderingHints#KEY_COLOR_RENDERING
	 */
	public void setEnhancedColorRednering(Boolean on) {
		Object value;
		if (on == null) {
			value = RenderingHints.VALUE_COLOR_RENDER_DEFAULT;
		} else if (on == true) {
			value = RenderingHints.VALUE_COLOR_RENDER_QUALITY;
		} else {
			value = RenderingHints.VALUE_COLOR_RENDER_SPEED;
		}
		put(RenderingHints.KEY_COLOR_RENDERING, value);
	}

	/**
	 * Sets the enhanced rendering option. If null is passed, the default
	 * behavior will be activated. If true, the rendering will try to produce
	 * the best possible result, but may be much slower.
	 * 
	 * @param on
	 *            The enhanced status status used.
	 * @see RenderingHints#KEY_RENDERING
	 */
	public void setEnhancedRednering(Boolean on) {
		Object value;
		if (on == null) {
			value = RenderingHints.VALUE_RENDER_DEFAULT;
		} else if (on == true) {
			value = RenderingHints.VALUE_RENDER_QUALITY;
		} else {
			value = RenderingHints.VALUE_RENDER_SPEED;
		}
		put(RenderingHints.KEY_RENDERING, value);
	}

	/**
	 * Sets the enhanced interpolation option. If null is passed, the default
	 * behavior will be activated. If true, the interpolation will try to
	 * produce the best possible result, but may be much slower.
	 * 
	 * @param on
	 *            The enhanced status status used.
	 * @see RenderingHints#KEY_INTERPOLATION
	 */
	public void setEnhancedInterpolation(Boolean on) {
		Object value;
		if (on == null) {
			value = RenderingHints.VALUE_INTERPOLATION_BILINEAR;
		} else if (on == true) {
			value = RenderingHints.VALUE_INTERPOLATION_BICUBIC;
		} else {
			value = RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR;
		}
		put(RenderingHints.KEY_INTERPOLATION, value);
	}

	/**
	 * Sets the enhanced alpha interpolation option. If null is passed, the
	 * default behavior will be activated. If true, the alpha interpolation will
	 * try to produce the best possible result, but may be much slower.
	 * 
	 * @param on
	 *            The enhanced status status used.
	 * @see RenderingHints#KEY_ALPHA_INTERPOLATION
	 */
	public void setEnhancedAlphaInterpolation(Boolean on) {
		Object value;
		if (on == null) {
			value = RenderingHints.VALUE_ALPHA_INTERPOLATION_DEFAULT;
		} else if (on == true) {
			value = RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY;
		} else {
			value = RenderingHints.VALUE_ALPHA_INTERPOLATION_SPEED;
		}
		put(RenderingHints.KEY_ALPHA_INTERPOLATION, value);
	}

	/**
	 * Controls the LCD contrast level.
	 * 
	 * @param contrast
	 *            The used contrast level
	 * @see RenderingHints#KEY_TEXT_LCD_CONTRAST
	 */
	public void setTextLcdContrast(int contrast) {
		if (!RenderingHints.KEY_TEXT_LCD_CONTRAST.isCompatibleValue(contrast)) {
			throw new IncompatibleValueException(
					"Contrast level not compatible -> " + contrast);
		}
		put(RenderingHints.KEY_TEXT_LCD_CONTRAST, contrast);
	}

	/**
	 * Apply this rendering settings to an {@link Graphics} object. Previous
	 * options will remain and aren't overwritten.
	 * 
	 * @param g
	 *            The {@link Graphics} object used for configuration purpose
	 * @see #apply(Graphics,boolean)
	 */
	public void apply(Graphics g) {
		apply(g, false);
	}

	/**
	 * Apply this rendering settings to an {@link Graphics} object. This will
	 * try to cast it to a {@link Graphics2D} object, as only this will support
	 * those settings.
	 * 
	 * @param g
	 *            The {@link Graphics} object used for configuration purpose
	 * @param overwrite
	 *            If true, all options set before will be discarded
	 * @see #apply(Graphics2D,boolean)
	 */
	public void apply(Graphics g, boolean overwrite) {
		if (g instanceof Graphics2D) {
			apply((Graphics2D) g, overwrite);
		}
	}

	/**
	 * Apply this rendering settings to an {@link Graphics2D} object. Behaves
	 * like {@link Graphics2D#addRenderingHints(java.util.Map)}.
	 * 
	 * @param g
	 *            The {@link Graphics} object used for configuration purpose
	 * @param overwrite
	 *            If true, all options set before will be discarded
	 */
	public void apply(Graphics2D g, boolean overwrite) {
		if (overwrite) {
			g.setRenderingHints(RenderingOptions.this);
		} else {
			g.addRenderingHints(RenderingOptions.this);
		}
	}
}
