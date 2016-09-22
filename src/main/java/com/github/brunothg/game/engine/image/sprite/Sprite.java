package com.github.brunothg.game.engine.image.sprite;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * A sprite is an image that contains an array of smaller images (tiles). This images can be
 * accessed like a normal Java array (and animated). But that this works, it is necessary that all
 * tiles have the same size. Overlaying a grid of the dimensions of one frame marks all tiles. Even
 * the space (if any exists) between the tiles has to be equal everywhere in the sprite, even at the
 * edges of the sprite or tiles will be missed.
 * 
 * @author Marvin Bruns
 *
 */
public interface Sprite
{

	/**
	 * Draws a tile of this sprite. The tile is drawn with {@code Bounds(0, 0,
	 * width, height)}. Consider creating a clipped {@link Graphics2D} object.
	 * 
	 * @param g {@link Graphics2D} object to draw on
	 * 
	 * @param x X-Coordinate of the tile
	 * 
	 * @param y Y-Coordinate of the tile
	 * 
	 * @param width The width of the Graphics object to draw on
	 * 
	 * @param height The height of the Graphics object to draw on
	 */
	public void drawTile(Graphics2D g, int x, int y, int width, int height);

	/**
	 * Get a tile of this sprite. Be careful with this method. Creates a copy of the underlying
	 * tile.<br>
	 * Use {@link #drawTile(Graphics2D, int, int, int, int)} for faster action and less memory
	 * usage.
	 * 
	 * @param x X-Coordinate of the tile
	 * @param y Y-Coordinate of the tile
	 * @return A {@link BufferedImage} containing the tile
	 */
	public BufferedImage getTile(int x, int y);

	/**
	 * Get the number of tiles in this sprite.
	 * 
	 */
	public int getTileCount();

	/**
	 * Get the number of rows.
	 */
	public int getRows();

	/**
	 * Get the number of columns.
	 * 
	 */
	public int getColumns();

	/**
	 * Get the width of one tile
	 */
	public int getTileWidth();

	/**
	 * Get the height of one tile
	 */
	public int getTileHeight();

	/**
	 * Get sub-sprite with given dimension. The returned {@link DerivedSprite} is linked with this
	 * sprite.
	 * 
	 * @param x X-Coordinate of first tile
	 * @param y Y-Coordinate of first tile
	 * @param width Width of the sub-sprite
	 * @param height Height of the sub-sprite
	 * @return The sub-sprite in form of a {@link DerivedSprite}
	 */
	public Sprite getSubSprite(int x, int y, int width, int height);
}
