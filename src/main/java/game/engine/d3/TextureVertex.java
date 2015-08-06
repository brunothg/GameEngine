package game.engine.d3;

public class TextureVertex {

	private double x;
	private double y;
	private double depth;

	public TextureVertex() {
		this(0, 0);
	}

	public TextureVertex(double[] pos) {
		this(pos[0], (pos.length > 1) ? pos[1] : 0, (pos.length > 2) ? pos[2]
				: 0);
	}

	public TextureVertex(double x, double y) {
		this(x, y, 0);
	}

	public TextureVertex(double x, double y, double depth) {
		this.x = x;
		this.y = y;
		this.depth = depth;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getDepth() {
		return depth;
	}

	public void setDepth(double depth) {
		this.depth = depth;
	}

	@Override
	public String toString() {
		return String.format("Texture vertex [%s, %s];W: %s", getX(), getY(),
				getDepth());
	}

	@Override
	protected TextureVertex clone() {
		return new TextureVertex(getX(), getY(), getDepth());
	}

}
