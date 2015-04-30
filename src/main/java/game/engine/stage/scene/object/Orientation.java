package game.engine.stage.scene.object;

/**
 * 
 * Enum used for laying out components.
 * 
 * @author Marvin Bruns
 *
 */
public class Orientation
{

	private Orientation()
	{
	}

	public static enum HorizontalOrientation {

		West, East, Center;
	}

	public static enum VerticalOrientation {

		North, South, Center;
	}
}
