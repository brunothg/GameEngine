package game.engine.d2.commons;

/**
 * 
 * Enum used for laying out components. Horizontal and vertical orientation is
 * separated: {@link HorizontalOrientation}, {@link VerticalOrientation}.
 * 
 * @author Marvin Bruns
 *
 */
public class Orientation {

	private Orientation() {
	}

	public static enum HorizontalOrientation {

		West, East, Center;
	}

	public static enum VerticalOrientation {

		North, South, Center;
	}
}
