package game.engine.image;

import game.engine.stage.scene.object.Size;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.List;

/**
 * 
 * This class represents a nine pitch image. Such an image contains a 1px
 * border. Black pixel({@link Color#BLACK} ) at the left or top border indicate
 * the stretch regions. Relative size of multiple regions will be kept.
 * 
 * @author Marvin Bruns
 *
 */
public class NinePatchImage implements Image {

	private Region[][] stretchRegions;

	private int naturalWidth;
	private int naturalHeight;

	private int sourceHeight;
	private int sourceWidth;

	private boolean horizontalStretch;
	private boolean verticalStretch;

	private Insets insets;

	private int quality = 1;

	/**
	 * Crates a new stretchable image from a nine patch image. The data array is
	 * shared (consider creating a copy of the image).
	 * 
	 * @see BufferedImage#getSubimage(int, int, int, int)
	 * @param src
	 *            The shared source image
	 */
	public NinePatchImage(BufferedImage src) {

		compile(src);
	}

	private void compile(BufferedImage src) {

		sourceHeight = src.getHeight() - 2;
		sourceWidth = src.getWidth() - 2;

		naturalWidth = 0;
		naturalHeight = 0;

		horizontalStretch = false;
		verticalStretch = false;

		insets = getInsets(src);

		List<Patch> hPatches = getPatches(src, true);
		List<Patch> vPatches = getPatches(src, false);
		stretchRegions = new Region[vPatches.size()][hPatches.size()];

		int x = 0;
		int y = 0;
		for (Patch h : hPatches) {
			for (Patch v : vPatches) {

				Region region = new Region();
				region.relWidth = (!h.fixedSize) ? h.relSize : -1;
				region.relHeight = (!v.fixedSize) ? v.relSize : -1;
				region.img = src.getSubimage(h.start, v.start, h.length(),
						v.length());

				stretchRegions[y][x] = region;
				y++;
			}
			x++;
			y = 0;
		}

		for (Patch h : hPatches) {

			if (!h.fixedSize) {
				horizontalStretch = true;
			} else {
				naturalWidth += h.length();
			}
		}

		for (Patch v : vPatches) {

			if (!v.fixedSize) {
				verticalStretch = true;
			} else {
				naturalHeight += v.length();
			}
		}
	}

	private Insets getInsets(BufferedImage src) {

		int left, right, top, bottom;
		left = right = top = bottom = 0;

		left = getInset(src, true, true);
		right = getInset(src, true, false);

		top = getInset(src, false, true);
		bottom = getInset(src, false, false);

		return new Insets(top, left, bottom, right);
	}

	private int getInset(BufferedImage src, boolean horizontal, boolean positive) {
		int inset = 0;

		int size = (horizontal) ? src.getWidth() : src.getHeight();
		int start = (positive) ? 1 : size - 2;

		int count = 0;
		for (int pos = start; ((positive) ? pos < size - 1 : pos > 0); pos = ((positive) ? pos + 1
				: pos - 1)) {

			Color c = new Color(src.getRGB((horizontal) ? pos
					: src.getWidth() - 1, (horizontal) ? src.getHeight() - 1
					: pos), true);

			if (c.equals(Color.BLACK)) {

				inset = count;
				break;
			}

			count++;
		}

		return inset;
	}

	private List<Patch> getPatches(BufferedImage src, boolean horizontal) {
		List<Patch> patches = new LinkedList<Patch>();

		int totalPatchSize = 0;
		int start = -1;

		int size = ((horizontal) ? src.getWidth() : src.getHeight());
		for (int pos = 1; pos < size - 1; pos++) {

			Color c = new Color(src.getRGB((horizontal) ? pos : 0,
					(horizontal) ? 0 : pos), true);
			if (!c.equals(Color.BLACK) || pos == size - 2) {
				if (start != -1) {

					Patch p;
					{
						Patch last = (patches.size() > 1) ? patches.get(patches
								.size() - 1) : null;

						p = new Patch();
						p.start = (last != null) ? last.end : 1;
						p.end = start;
						p.fixedSize = true;

						patches.add(p);
					}

					{
						p = new Patch();
						p.start = start;
						p.end = pos;
						p.fixedSize = false;

						totalPatchSize += p.length();
						patches.add(p);
					}

					start = -1;
				}

				continue;
			}

			if (start == -1) {
				start = pos;
			}
		}
		{
			Patch last = (patches.size() > 0) ? patches.get(patches.size() - 1)
					: null;

			Patch p = new Patch();
			p.start = (last != null) ? last.end : 1;
			p.end = size - 1;
			p.fixedSize = true;

			if (p.length() > 0) {

				patches.add(p);
			}
		}

		for (Patch p : patches) {

			p.relSize = p.length() / (double) totalPatchSize;
		}

		return patches;
	}

	@Override
	public void draw(Graphics2D g, int width, int height) {

		if (quality <= 0) {

			g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
					RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
		} else if (quality == 1) {

			g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
					RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		} else {

			g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
					RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		}

		// calculate used base size
		int usedNaturalWidth = (naturalWidth <= width) ? naturalWidth : width;
		int usedNaturalHeight = (naturalHeight <= height) ? naturalHeight
				: height;

		double usedRelativeWidth = usedNaturalWidth / (double) naturalWidth;
		double usedRelativeHeight = usedNaturalHeight / (double) naturalHeight;

		if (usedRelativeWidth < 1 || usedRelativeHeight < 1) {

			if (usedRelativeWidth < usedRelativeHeight) {

				usedRelativeHeight = usedRelativeWidth;
				usedNaturalHeight = (int) Math.min(
						Math.round(naturalHeight * usedRelativeHeight), height);
			} else {

				usedRelativeWidth = usedRelativeHeight;
				usedNaturalWidth = (int) Math.min(
						Math.round(naturalWidth * usedRelativeWidth), width);
			}
		}

		// calculate stretched size
		final int stretchableWidth = width - usedNaturalWidth;
		final int stretchableHeight = height - usedNaturalHeight;

		double[] hSizes = new double[stretchRegions[0].length];
		double[] vSizes = new double[stretchRegions.length];

		for (int x = 0; x < hSizes.length; x++) {

			Region region = stretchRegions[0][x];
			double relWidth = region.relWidth;
			int absWidth = region.img.getWidth();

			if (relWidth < 0) {

				hSizes[x] = absWidth * usedRelativeWidth;
			} else {

				hSizes[x] = stretchableWidth * relWidth;
			}
		}

		for (int y = 0; y < vSizes.length; y++) {

			Region region = stretchRegions[y][0];
			double relHeight = region.relHeight;
			int absHeight = region.img.getHeight();

			if (relHeight < 0) {

				vSizes[y] = absHeight * usedRelativeHeight;
			} else {

				vSizes[y] = stretchableHeight * relHeight;
			}
		}

		double posX = 0;
		double posY = 0;
		for (int y = 0; y < vSizes.length; y++) {
			double _height = vSizes[y];

			for (int x = 0; x < hSizes.length; x++) {
				double _width = hSizes[x];

				BufferedImage img = stretchRegions[y][x].img;
				g.drawImage(img, (int) Math.round(posX),
						(int) Math.round(posY),
						(int) Math.round(posX + (_width)),
						(int) Math.round(posY + (_height)), 0, 0,
						img.getWidth(), img.getHeight(), null);

				posX += _width;
			}
			posX = 0;
			posY += _height;
		}
	}

	public BufferedImage getImage(int width, int height) {

		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_ARGB_PRE);

		Graphics2D graphics = image.createGraphics();
		draw(graphics, width, height);
		graphics.dispose();

		return image;
	}

	/**
	 * Get the capability to stretch this image in horizontal direction.
	 * 
	 * @return true, if this image can be stretched in horizontal direction
	 */
	public boolean isHorizontalStretachable() {

		return horizontalStretch;
	}

	/**
	 * Get the capability to stretch this image in vertical direction.
	 * 
	 * @return true, if this image can be stretched in vertical direction
	 */
	public boolean isVerticalStretachable() {

		return verticalStretch;
	}

	/**
	 * Get the natural minimum size of this image. Natural means the pixel of
	 * the image, that aren't marked as stretch area.
	 * 
	 * @return The natural minimum size
	 */
	public Size getNaturalSize() {

		return new Size(naturalWidth, naturalHeight);
	}

	/**
	 * Get the {@link Insets} specified by the underlying nine patch source
	 * image.
	 * 
	 * @return The {@link Insets} specified by the nine patch source image
	 */
	public Insets getInsets() {

		return insets;
	}

	/**
	 * Get the source size of this image. The source size is the size the
	 * underlying image provides without the patch indicator border. This
	 * results in the a size of:<br>
	 * <br>
	 * <code>src.getWidth() - 2, src.getHeight() - 2</code><br>
	 * <br>
	 * As the insets are returned as absolute values calculated with the source
	 * size you can calculate the relative insets with the source size.
	 * 
	 * @return The source size of this image
	 */
	public Size getSourceSize() {

		return new Size(sourceWidth, sourceHeight);
	}

	/**
	 * @see #getSourceSize()
	 * @see Image#getSize()
	 */
	public Size getSize() {

		return getSourceSize();
	}

	/**
	 * Get the used interpolation quality.
	 * 
	 * @return The used interpolation quality
	 */
	public int getQuality() {
		return quality;
	}

	/**
	 * Set the interpolation quality values from 0 to 2 are allowed. Values out
	 * of the range will be increased/decreased to fit the range.
	 * 
	 * @param quality
	 *            Interpolation quality
	 */
	public void setQuality(int quality) {
		this.quality = Math.max(0, Math.min(quality, 2));
	}

	private class Patch {
		/**
		 * inclusive
		 */
		int start;

		/**
		 * exclusive
		 */
		int end;

		double relSize;
		boolean fixedSize;

		int length() {

			return Math.abs(end - start);
		}
	}

	private class Region {

		BufferedImage img;

		// If fixed size = -1
		// If stretch area relative resize value
		double relWidth;
		double relHeight;
	}
}
