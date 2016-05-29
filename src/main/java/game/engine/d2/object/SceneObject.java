package game.engine.d2.object;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;

import game.engine.d2.commons.Point;
import game.engine.d2.commons.RenderingOptions;
import game.engine.d2.commons.Size;

/**
 * Helpful class for managing Objects in a scene. Provides methods for collision
 * handling.
 * 
 * @author Marvin Bruns
 *
 */
public abstract class SceneObject {

	protected final static Point ORIGIN_TOP_LEFT = new Point(0, 0);

	private Point position = ORIGIN_TOP_LEFT;
	private Size size = new Size(0, 0);

	private boolean drawBoundingBox;

	private RenderingOptions renderingOptions;

	public SceneObject() {

		setPosition(0, 0);
		setSize(0, 0);
	}

	/**
	 * Paint this SceneObject. This method maybe called for collision testing
	 * with an elapsed time of zero. For correct collision the
	 * {@link SceneObject} it is important, that the object is drawn in it's
	 * actual state.
	 * 
	 * @param g
	 *            Graphics Object for painting
	 * @param elapsedTime
	 *            Elapsed time since the last call to this method
	 */
	protected abstract void paint(Graphics2D g, long elapsedTime);

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
		if (renderingOptions != null) {
			renderingOptions.apply(g2d);
		}
		paint(g2d, elapsedTime);

		if (isDrawBoundingBox()) {
			g2d.setColor(Color.BLACK);
			g2d.drawRect(0, 0, width - 1, height - 1);
		}

		g2d.dispose();
	}

	/**
	 * The position of this {@link SceneObject}'s top left corner
	 * 
	 * @return The Position of this SceneObject's top left corner
	 */
	public Point getTopLeftPosition() {
		return new Point(getX() - getOrigin().getX(),
				getY() - getOrigin().getY());
	}

	/**
	 * Set the position of this {@link SceneObject}'s top left corner
	 */
	public void setTopLeftPosition(Point p) {
		setPosition(new Point(p.getX() + getOrigin().getX(),
				p.getY() + getOrigin().getY()));
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
	 * translated to it's origin. The default implementation returns
	 * {@link #ORIGIN_TOP_LEFT}
	 * 
	 * @return The origin of this ScreenObject
	 */
	public Point getOrigin() {

		return ORIGIN_TOP_LEFT;
	}

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
	 * Get the {@link RenderingOptions} used for painting. The are applied to
	 * every {@link Graphics2D} object, this one is painted on.
	 * 
	 * @return The rendering options
	 * @see #setRenderingOptions(RenderingOptions)
	 */
	public RenderingOptions getRenderingOptions() {
		return renderingOptions;
	}

	/**
	 * Set the {@link RenderingOptions} for painting.
	 * 
	 * @param renderingOptions
	 * @see #getRenderingOptions()
	 */
	public void setRenderingOptions(RenderingOptions renderingOptions) {
		this.renderingOptions = renderingOptions;
	}

	/**
	 * Check, if the given {@link SceneObject} is completely covered by this
	 * {@link SceneObject}.<br>
	 * First test with bounding box if there maybe a overlapping situation exact
	 * calculations are made.
	 * 
	 * @param obj
	 *            Other Object for testing
	 * 
	 * @return true if the given object is completely covered by this object
	 */
	public boolean consumes(SceneObject obj) {

		if (consumesBoundingBox(obj)) {

			return consumesExactly(obj);
		}

		return false;
	}

	/**
	 * Exactly check overlapping. Uses alpha mask to detect geometry. If there's
	 * a pixel in the given object, that has an alpha greater 0 and this object
	 * is transparent (alpha = 0) at this position false is returned.
	 * 
	 * 
	 * @param obj
	 *            Other object for testing
	 * @return true if the given {@link SceneObject} is consumed by this object
	 */
	public boolean consumesExactly(SceneObject obj) {

		BufferedImage img1 = new BufferedImage(getWidth(), getHeight(),
				BufferedImage.TYPE_INT_ARGB);
		Graphics2D g1 = img1.createGraphics();
		paint(g1, 0);

		BufferedImage img2 = new BufferedImage(obj.getWidth(), obj.getHeight(),
				BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = img2.createGraphics();
		obj.paint(g2, 0);

		ColorModel cm1 = img1.getColorModel();
		WritableRaster raster1 = img1.getRaster();

		ColorModel cm2 = img2.getColorModel();
		WritableRaster raster2 = img2.getRaster();

		Point topLeftPosition1 = getTopLeftPosition();
		Point topLeftPosition2 = obj.getTopLeftPosition();

		int offsetX = topLeftPosition2.getX() - topLeftPosition1.getX();
		int offsetY = topLeftPosition2.getY() - topLeftPosition1.getY();

		int width1 = img1.getWidth();
		int height1 = img1.getHeight();

		int width2 = img2.getWidth();
		int height2 = img2.getHeight();

		for (int x = 0; x < width2; x++) {
			for (int y = 0; y < height2; y++) {

				int alpha2 = cm2.getAlpha(raster2.getDataElements(x, y, null));

				if (alpha2 == 0) {
					continue;
				}

				int xt = x - offsetX;
				int yt = y - offsetY;

				// Collision not possible out of bounds
				if (xt < 0 || yt < 0 || xt >= width1 || yt >= height1) {

					return false;
				}

				int alpha1 = cm1
						.getAlpha(raster1.getDataElements(xt, yt, null));

				if (alpha1 == 0) {
					return false;
				}
			}
		}

		return true;
	}

	/**
	 * Tests overlapping with bounding box.
	 * 
	 * @param obj
	 *            Other Object for testing
	 */
	public boolean consumesBoundingBox(SceneObject obj) {

		Rectangle rectangleT = getRectangle();
		Rectangle rectangleO = obj.getRectangle();

		if (rectangleT.contains(rectangleO)) {
			return true;
		}

		return false;
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
	 *            Second SceneObject
	 * @return true if both {@link SceneObject}s collide
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
	 * The default implementation tests every pixel drawn by this object. If
	 * there's a pixel set (alpha != 0) in both objects they collide. Depending
	 * on the object there maybe a better collision handling. Feel free to
	 * override this method for better performance.
	 * 
	 * @param obj
	 *            The second {@link SceneObject} for testing
	 * @param intersection
	 *            Region in which the collision may occur
	 * @return true if there's a collision
	 */
	public boolean collidesExactly(SceneObject obj, Rectangle intersection) {

		boolean collides = false;

		BufferedImage img1 = new BufferedImage(getWidth(), getHeight(),
				BufferedImage.TYPE_INT_ARGB);
		Graphics2D g1 = img1.createGraphics();
		paint(g1, 0);

		BufferedImage img2 = new BufferedImage(obj.getWidth(), obj.getHeight(),
				BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = img2.createGraphics();
		obj.paint(g2, 0);

		ColorModel cm1 = img1.getColorModel();
		WritableRaster raster1 = img1.getRaster();

		ColorModel cm2 = img2.getColorModel();
		WritableRaster raster2 = img2.getRaster();

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

				int alpha1 = cm1.getAlpha(raster1.getDataElements(x + xOffset1,
						y + yOffset1, null));
				// (img1.getRGB(x + xOffset1, y + yOffset1) >> 24) & 0xFF;

				// Collision not possible
				if (alpha1 == 0) {
					continue;
				}

				int alpha2 = cm2.getAlpha(raster2.getDataElements(x + xOffset2,
						y + yOffset2, null));
				// (img2.getRGB(x + xOffset2, y + yOffset2) >> 24) & 0xFF;

				if (alpha1 == alpha2) {
					collides = true;
					break loop;
				}
			}
		}

		return collides;
	}

	/**
	 * Check collision with bounding box algorithm.
	 * 
	 * @param obj
	 *            The second {@link SceneObject} for testing
	 * @return The overlapping region or null if none. Coordinates (0,0) is
	 *         located in the top-left corner.
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

	/**
	 * Get the {@link Rectangle} representing this object's bounds.
	 * 
	 * @return This object's bounds
	 */
	public Rectangle getRectangle() {

		Point position = getTopLeftPosition();

		return new Rectangle(position.getX(), position.getY(), getWidth(),
				getHeight());
	}

}
