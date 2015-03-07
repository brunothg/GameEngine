package game.engine.stage.scene.object;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

/**
 * Helpful class for managing Objects in a scene. Provides methods for collision
 * handling.
 * 
 * @author Marvin Bruns
 *
 */
public abstract class SceneObject {

	private Point position;
	private Size size;

	private boolean drawBoundingBox;

	public SceneObject() {

		setPosition(0, 0);
		setSize(0, 0);
	}

	/**
	 * Paint this SceneObject
	 * 
	 * @param g
	 *            Graphics Object for painting
	 * @param onScreen
	 *            true wenn diese Methode aufgerufen wird um das SceneObject auf
	 *            dem Bildschirm zu zeichnen. False otherwise - Zum Beispiel
	 *            wenn die Kollision geprueft wird.
	 */
	protected abstract void paint(Graphics2D g, boolean onScreen);

	/**
	 * Paints this SceneObject.
	 * 
	 * @param g
	 *            Graphics Object for painting
	 * @param elapsedTime
	 *            Elapsed time since the last call to this method
	 */
	public void paintOnScene(Graphics2D g, long elapsedTime) {

		Point topLeftPosition = getTopLeftPosition();
		int x_topLeft = topLeftPosition.getX();
		int y_topLeft = topLeftPosition.getY();

		int width = getWidth();
		int height = getHeight();

		Graphics2D g2d = (Graphics2D) g.create(x_topLeft, y_topLeft, width,
				height);
		paint(g2d, true);

		if (isDrawBoundingBox()) {

			g2d.setColor(Color.BLACK);
			g2d.drawRect(0, 0, width, height);
		}
	}

	/**
	 * The position of this {@link SceneObject}'s top left corner
	 * 
	 * @return The Position of this SceneObject's top left corner
	 */
	public Point getTopLeftPosition() {
		return new Point(getX() - getOrigin().getX(), getY()
				- getOrigin().getY());
	}

	/**
	 * Set the position of this {@link SceneObject}'s top left corner
	 */
	public void setTopLeftPosition(Point p) {
		setPosition(new Point(p.getX() + getOrigin().getX(), p.getY()
				+ getOrigin().getY()));
	}

	/**
	 * Get the position of this {@link SceneObject}
	 * 
	 * @return The position of this ScreenObject
	 */
	public Point getPosition() {
		return position;
	}

	/**
	 * The Y-Coordinate of this {@link SceneObject}
	 * 
	 * @see #getPosition()
	 * @return The Y-Coordinate of this ScreenObject
	 */
	public int getY() {
		return getPosition().getY();
	}

	/**
	 * The X-Coordinate of this {@link SceneObject}
	 * 
	 * @see #getPosition()
	 * @return The X-Coordinate of this ScreenObject
	 */
	public int getX() {
		return getPosition().getX();
	}

	/**
	 * Set the position of this {@link SceneObject}
	 * 
	 * @param position
	 *            The Position of this ScreenObject
	 */
	public void setPosition(Point position) {
		if (position == null) {
			throw new NullPointerException(
					"This SceneObject must have a position");
		}

		this.position = position;
	}

	/**
	 * Set the position of this {@link SceneObject}
	 * 
	 * @see #setPosition(Point)
	 * @param x
	 *            The X-Coordinate of this ScreenObject
	 * @param y
	 *            The Y-Coordinate of this ScreenObject
	 */
	public void setPosition(int x, int y) {
		setPosition(new Point(x, y));
	}

	/**
	 * The origin of this {@link SceneObject}. This object's position will be
	 * translated to it's origin.
	 * 
	 * @return The origin of this ScreenObject
	 */
	public abstract Point getOrigin();

	/**
	 * The size of this {@link SceneObject}
	 * 
	 * @return The Size of this ScreenObject
	 */
	public Size getSize() {
		return size;
	}

	/**
	 * The height of this {@link SceneObject}
	 * 
	 * @see #getSize()
	 * @return The height of this ScreenObject
	 */
	public int getHeight() {
		return getSize().getHeight();
	}

	/**
	 * The width of this {@link SceneObject}
	 * 
	 * @see #getSize()
	 * @return The width of this ScreenObject
	 */
	public int getWidth() {
		return getSize().getWidth();
	}

	/**
	 * Set the size of this {@link SceneObject}
	 * 
	 * @param size
	 *            Size of this ScreenObject
	 */
	public void setSize(Size size) {
		if (size == null || size.getHeight() < 0 || size.getWidth() < 0) {
			throw new NullPointerException(
					"This SceneObject must have a positive size");
		}

		this.size = size;
	}

	/**
	 * @see #setSize(Size)
	 * @param width
	 *            Width of this ScreenObject
	 * @param height
	 *            Height of this ScreenObject
	 */
	public void setSize(int width, int height) {
		setSize(new Size(width, height));
	}

	/**
	 * Check if the bounding box of this {@link SceneObject} is painted or not.
	 * If it is painted a rectangle will be drawn around the dimensions of this
	 * object.
	 * 
	 * @return true if the bounding box will be painted
	 */
	public boolean isDrawBoundingBox() {
		return drawBoundingBox;
	}

	/**
	 * Change the drawing state of the bounding box.
	 * 
	 * @param drawBoundingBox
	 *            true if the bounding box should be painted
	 */
	public void setDrawBoundingBox(boolean drawBoundingBox) {
		this.drawBoundingBox = drawBoundingBox;
	}

	/**
	 * Check, if both {@link SceneObject}s collide.<br>
	 * In a first step bounding box collision will be tested. If a collision
	 * maybe possible exact collision is checked.
	 * 
	 * @see #collidesExactly(SceneObject, Rectangle)
	 * @see #collidesBoundingBox(SceneObject)
	 * 
	 * @param obj
	 *            Das zweite SceneObject
	 * @return true wenn die beiden SceneObjects kollidieren
	 */
	public boolean collides(SceneObject obj) {

		boolean isColliding;

		Rectangle intersection = collidesBoundingBox(obj);
		isColliding = intersection != null;

		if (isColliding) {
			isColliding = collidesExactly(obj, intersection);
		}

		return isColliding;
	}

	/**
	 * Check exactly if a collision is present. The result may only be correct
	 * if {@link #collidesBoundingBox(SceneObject)} returns a non empty region.
	 * 
	 * @param obj
	 *            The second {@link SceneObject} for testing
	 * @param intersection
	 *            Region in which the collision may occur
	 * @return true if there's a collision
	 */
	public boolean collidesExactly(SceneObject obj, Rectangle intersection) {

		boolean ret = false;

		BufferedImage img1 = new BufferedImage(getWidth(), getHeight(),
				BufferedImage.TYPE_INT_ARGB);
		Graphics2D g1 = img1.createGraphics();
		paint(g1, false);
		g1.finalize();

		BufferedImage img2 = new BufferedImage(obj.getWidth(), obj.getHeight(),
				BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = img2.createGraphics();
		obj.paint(g2, false);
		g2.finalize();

		Point topLeftPosition1 = getTopLeftPosition();
		Point topLeftPosition2 = obj.getTopLeftPosition();

		int xOffset1 = -topLeftPosition1.getX();
		int yOffset1 = -topLeftPosition1.getY();

		int xOffset2 = -topLeftPosition2.getX();
		int yOffset2 = -topLeftPosition2.getY();

		loop: for (int y = intersection.y; y < intersection.height
				+ intersection.y; y++) {
			for (int x = (int) intersection.x; x < intersection.width
					+ intersection.x; x++) {

				int a1 = (img1.getRGB(x + xOffset1, y + yOffset1) >> 24) & 0xFF;
				int a2 = (img2.getRGB(x + xOffset2, y + yOffset2) >> 24) & 0xFF;

				if (a1 == a2 && a1 != 0) {
					ret = true;
					break loop;
				}
			}
		}

		return ret;
	}

	/**
	 * Check collision with bounding box algorithm
	 * 
	 * @param obj
	 *            The second {@link SceneObject} for testing
	 * @return The overlapping region or null if none
	 */
	public Rectangle collidesBoundingBox(SceneObject obj) {

		Rectangle2D rect = getRectangle()
				.createIntersection(obj.getRectangle());

		Rectangle ret = null;

		if (rect.getWidth() >= 0 && rect.getWidth() >= 0) {
			ret = new Rectangle((int) rect.getX(), (int) rect.getY(),
					(int) rect.getWidth(), (int) rect.getHeight());
		}

		return ret;
	}

	private Rectangle getRectangle() {

		Point position = getTopLeftPosition();

		return new Rectangle(position.getX(), position.getY(), getWidth(),
				getHeight());
	}

}
