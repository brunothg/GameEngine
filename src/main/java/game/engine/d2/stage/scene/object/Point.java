package game.engine.d2.stage.scene.object;

/**
 * 
 * Represents a 2D coordinate.
 * 
 * @author Marvin Bruns
 *
 */
public class Point {

	private int x;
	private int y;

	public Point() {

		this(0, 0);
	}

	public Point(int x, int y) {
		this.setX(x);
		this.setY(y);
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
}
