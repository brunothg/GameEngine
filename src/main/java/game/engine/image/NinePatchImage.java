package game.engine.image;

import game.engine.stage.scene.object.Size;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
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
public class NinePatchImage {

	private Region[][] stretchRegions;

	private int naturalWidth;
	private int naturalHeight;

	private boolean horizontalStretch;
	private boolean verticalStretch;

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

		naturalWidth = 0;
		naturalHeight = 0;

		horizontalStretch = false;
		verticalStretch = false;

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

			p.relSize = p.length() / totalPatchSize;
		}

		return patches;
	}

	public void draw(Graphics g, int width, int height) {

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

		int[] hSizes = new int[stretchRegions[0].length];
		int[] vSizes = new int[stretchRegions.length];

		for (int x = 0; x < hSizes.length; x++) {

			Region region = stretchRegions[0][x];
			double relWidth = region.relWidth;
			int absWidth = region.img.getWidth();

			if (relWidth < 0) {

				hSizes[x] = absWidth;
			} else {

				hSizes[x] = (int) Math.round(stretchableWidth * relWidth);
			}
		}

		for (int y = 0; y < vSizes.length; y++) {

			Region region = stretchRegions[y][0];
			double relHeight = region.relHeight;
			int absHeight = region.img.getHeight();

			if (relHeight < 0) {

				vSizes[y] = absHeight;
			} else {

				vSizes[y] = (int) Math.round(stretchableHeight * relHeight);
			}
		}

		int posX = 0;
		int posY = 0;
		for (int y = 0; y < vSizes.length; y++) {
			int _height = vSizes[y];

			for (int x = 0; x < hSizes.length; x++) {
				int _width = hSizes[x];

				BufferedImage img = stretchRegions[y][x].img;
				g.drawImage(img, posX, posY, posX + (_width), posY + (_height),
						0, 0, img.getWidth(), img.getHeight(), null);

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
