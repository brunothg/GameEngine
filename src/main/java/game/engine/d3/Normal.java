package game.engine.d3;

public class Normal {

	private double x;
	private double y;
	private double z;

	public Normal() {
		this(0, 0, 0);
	}

	public Normal(double x, double y) {
		this(x, y, 0);
	}

	public Normal(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Normal(double[] pos) {
		this((pos.length > 0) ? pos[0] : 0, (pos.length > 1) ? pos[1] : 0,
				(pos.length > 2) ? pos[2] : 0);
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

	public double getZ() {
		return z;
	}

	public void setZ(double z) {
		this.z = z;
	}

	@Override
	protected Normal clone() {
		return new Normal(getX(), getY(), getZ());
	}

	@Override
	public String toString() {
		return String.format("Normal[%s, %s, %s]", getX(), getY(), getZ());
	}
}
