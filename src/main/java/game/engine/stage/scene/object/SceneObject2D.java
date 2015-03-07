package game.engine.stage.scene.object;

import game.engine.image.ImageUtils;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;

/**
 * Extended {@link SceneObject} supporting {@link Area} representation.
 * 
 * @author Marvin Bruns
 *
 */
public abstract class SceneObject2D extends SceneObject {

	private static final Color COLOR_CLEAR = new Color(0, 0, 0, 0);

	private boolean buffered;
	private BufferedImage offImage;

	/**
	 * Creates a buffered SceneObject
	 */
	public SceneObject2D() {

		setBuffered(true);
	}

	/**
	 * Get the {@link Area} representing this {@link SceneObject2D}. The default
	 * implementation creates the area from the drawn pixels. The returned Area
	 * is the result of following the outline. Depending on the object there
	 * maybe a better algorithm to do this. Feel free to override this method
	 * for better performance.<br>
	 * The {@link Area} will always be recalculated when calling this method if
	 * there's a way to avoid this override this method.<br>
	 * The returned area's coordinates are global in it's parents context.
	 * 
	 * @see #calculateArea()
	 * 
	 * @param area
	 *            The area of this object to be parsed or null for full area
	 * 
	 * @return {@link Area} representing this {@link SceneObject2D} or null if
	 *         no area could be generated. The returned area should be
	 *         calculated with {@link #calculateArea(boolean)} true. Otherwise
	 *         collision handling may produce false values. An object can be
	 *         inside this object (free spaces inside -> e.g a doughnut) and
	 *         Intersection returns null.
	 */
	public Area getArea(Rectangle area) {

		return calculateArea(true, area);
	}

	/**
	 * Calculates the {@link Area} of this {@link SceneObject2D} object.
	 * 
	 * @param onlyOutline
	 *            if true the area consists only of one shape covering this
	 *            object. Otherwise all free spaces inside this object will be
	 *            cut out. Be patient with intersection etc. if true.
	 * @param area
	 *            The area of this object to be parsed
	 * 
	 * @return This {@link SceneObject2D}'s area or null if no area could be
	 *         calculated
	 */
	protected Area calculateArea(boolean onlyOutline, Rectangle area) {

		BufferedImage src = null;
		if (isBuffered()) {
			src = getOffImage();
		}

		// repaint the image if no buffering is on or just wasn't painted yet
		if (src == null) {
			BufferedImage img = new BufferedImage(getWidth(), getHeight(),
					BufferedImage.TYPE_INT_ARGB_PRE);
			Graphics2D g2d = img.createGraphics();
			ImageUtils.clearImage(g2d, img.getWidth(), img.getHeight(),
					COLOR_CLEAR);
			paint(g2d, 0);
			src = img;
		}

		Point topLeftPosition = getTopLeftPosition();
		return ImageUtils.getOutlines(src, topLeftPosition.getX(),
				topLeftPosition.getX(), area, onlyOutline);
	}

	/**
	 * Get the {@link Area} that both {@link SceneObject2D}s have in common.
	 * 
	 * @param obj
	 *            The other object for testing
	 * @return The intersecting {@link Area} or null if there's no intersection
	 */
	public Area intersects(SceneObject2D obj) {

		Rectangle boundingBox = collidesBoundingBox(obj);
		if (boundingBox == null) {

			return null;
		}

		Point posT = getTopLeftPosition();
		Point posO = obj.getTopLeftPosition();

		Rectangle bbRT = new Rectangle(boundingBox.x - posT.getX(),
				boundingBox.y - posT.getY(), boundingBox.width,
				boundingBox.height);
		Rectangle bbRO = new Rectangle(boundingBox.x - posO.getX(),
				boundingBox.y - posO.getY(), boundingBox.width,
				boundingBox.height);

		Area areaT = getArea(bbRT);
		Area areaO = obj.getArea(bbRO);

		if (areaT == null || areaO == null) {
			return null;
		}

		Area intersection = new Area(areaT);
		intersection.intersect(areaO);

		if (intersection.isEmpty()) {
			intersection = null;
		}

		return intersection;
	}

	/**
	 * Test if the given {@link SceneObject2D} is completely inside this object.
	 * It means, that the given object was eaten up.
	 * 
	 * @param obj
	 *            The other object for testing
	 * @return true if this object covers the given object
	 */
	public boolean covers(SceneObject2D obj) {

		Rectangle boundingBox = collidesBoundingBox(obj);
		if (boundingBox == null) {

			return false;
		}

		Point pos = getTopLeftPosition();

		Rectangle bbR = new Rectangle(boundingBox.x - pos.getX(), boundingBox.y
				- pos.getY(), boundingBox.width, boundingBox.height);

		Area areaT = getArea(bbR);
		Area areaO = obj.getArea(null);

		if (areaT == null || areaO == null) {
			return false;
		}

		Area subsracted = new Area(areaO);
		subsracted.subtract(areaT);

		if (subsracted.isEmpty()) {
			return true;
		}

		return false;
	}

	/**
	 * Check exactly if a collision is present. The result may only be correct
	 * if {@link #collidesBoundingBox(SceneObject)} returns a non empty region.
	 * The {@link #getArea()} method is used to calculate collision if both
	 * objects are {@link SceneObject2D} objects. Otherwise
	 * {@link SceneObject#collidesExactly(SceneObject, Rectangle)} will be
	 * called.
	 * 
	 * @param obj
	 *            The second {@link SceneObject} for testing
	 * @param intersection
	 *            Region in which the collision may occur
	 * @return true if there's a collision
	 */
	public boolean collidesExactly(SceneObject obj, Rectangle intersection) {

		if (obj instanceof SceneObject2D) {
			return intersects((SceneObject2D) obj) != null;
		} else {
			return super.collidesExactly(obj, intersection);
		}

	}

	/**
	 * Paints this SceneObject. If enabled draws on off image.
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

		Graphics2D g2d;

		if (isBuffered()) {
			g2d = getOffGraphics();
		} else {
			g2d = (Graphics2D) g.create(x_topLeft, y_topLeft, width, height);
		}

		paint(g2d, elapsedTime);

		if (isDrawBoundingBox()) {

			g2d.setColor(Color.BLACK);
			g2d.drawRect(0, 0, width, height);
		}

		if (isBuffered()) {
			BufferedImage offImage = getOffImage();
			g.drawImage(offImage, x_topLeft, y_topLeft, x_topLeft + width,
					y_topLeft + height, 0, 0, offImage.getWidth(),
					offImage.getHeight(), null);
		}
	}

	private Graphics2D getOffGraphics() {

		BufferedImage offImage = getOffImage();
		Graphics2D g2d = offImage.createGraphics();

		g2d.setComposite(AlphaComposite.Src);
		g2d.setColor(COLOR_CLEAR);
		g2d.fillRect(0, 0, offImage.getWidth(), offImage.getHeight());
		g2d.setComposite(AlphaComposite.SrcOver);

		return g2d;
	}

	public boolean isBuffered() {
		return buffered;
	}

	public void setBuffered(boolean buffered) {
		this.buffered = buffered;
	}

	protected BufferedImage getOffImage() {

		if (offImage == null || offImage.getWidth() != getWidth()
				|| offImage.getHeight() != getHeight()) {

			offImage = new BufferedImage(getWidth(), getHeight(),
					BufferedImage.TYPE_INT_ARGB_PRE);
		}

		return offImage;
	}

}
