package com.github.brunothg.game.engine.d2.scene;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import com.github.brunothg.game.engine.d2.commons.GraphicScaleStrategy;
import com.github.brunothg.game.engine.d2.commons.Point;

/**
 * This scene guarantees a fixed width and height. Be careful with
 * transformations - you should not overwrite existing transformations.
 * 
 * @author Marvin Bruns
 *
 */
public abstract class ScaleScene implements Scene {

	private int width;
	private int height;

	private GraphicScaleStrategy scaleStrategy;

	private double fx = 1;
	private double fy = 1;

	private double tx = 0;
	private double ty = 0;

	public ScaleScene(int width, int height, GraphicScaleStrategy scaleStrategy) {
		this.width = width;
		this.height = height;
		setScaleStrategy(scaleStrategy);
	}

	public ScaleScene() {
		this(1920, 1080, GraphicScaleStrategy.Auto);
	}

	@Override
	public void paintScene(Graphics2D g, int realWidth, int realHeight, long elapsedTime) {

		switch (scaleStrategy) {
		case Auto:
		case Fill: {
			fx = (double) realWidth / getWidth();
			fy = (double) realHeight / getHeight();

			tx = 0;
			ty = 0;
		}
			break;
		case Fit: {
			fx = (double) realWidth / getWidth();
			fy = (double) realHeight / getHeight();

			fx = fy = Math.min(fx, fy);

			tx = (realWidth - (getWidth() * fx)) * 0.5;
			ty = (realHeight - (getHeight() * fy)) * 0.5;
		}
			break;
		case NoScale:
		default: {
			fx = 1;
			fy = 1;

			tx = 0;
			ty = 0;
		}
			break;

		}

		AffineTransform transform = g.getTransform();
		transform.translate(tx, ty);
		transform.scale(fx, fy);
		g.setTransform(transform);

		paintFixedScene(g, elapsedTime);
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public GraphicScaleStrategy getScaleStrategy() {
		return scaleStrategy;
	}

	public void setScaleStrategy(GraphicScaleStrategy scaleStrategy) {
		this.scaleStrategy = scaleStrategy;
	}

	protected abstract void paintFixedScene(Graphics2D g, long elapsedTime);

	/**
	 * Get the scale factor for scene width (realWidth / fixedWidth)
	 * 
	 * @return scale factor for scene width
	 */
	protected double getFactorX() {
		return fx;
	}

	/**
	 * Get the scale factor for scene height (realHeight / fixedHeight)
	 * 
	 * @return scale factor for scene height
	 */
	protected double getFactorY() {
		return fy;
	}

	/**
	 * Get the translation for scene width
	 * 
	 * @return translation for scene width
	 */
	protected double getTranslateX() {
		return tx;
	}

	/**
	 * Get the translation for scene height
	 * 
	 * @return translation for scene height
	 */
	protected double getTranslateY() {
		return ty;
	}

	/**
	 * Converts a global point (real size) to local point (fixed size).
	 * 
	 * @param point
	 *            Global {@link Point}
	 * @return Local {@link Point}
	 */
	public Point convertGlobalToLocal(Point point) {
		double x = (point.getX() - getTranslateX()) / getFactorX();
		double y = (point.getY() - getTranslateY()) / getFactorY();

		return new Point((int) Math.round(x), (int) Math.round(y));
	}

	/**
	 * Converts a local point (fixed size) to global point (real size).
	 * 
	 * @param point
	 *            Local {@link Point}
	 * @return Global {@link Point}
	 */
	public Point convertLocalToGlobal(Point point) {
		double x = (point.getX() * getFactorX()) + getTranslateX();
		double y = (point.getY() * getFactorY()) - getTranslateY();

		return new Point((int) Math.round(x), (int) Math.round(y));
	}
}
