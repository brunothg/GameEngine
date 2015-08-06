package game.engine.d3;

public class Vertex {

	private double x;
	private double y;
	private double z;
	private double weight;

	public Vertex() {
		this(0, 0, 0);
	}

	public Vertex(double x, double y) {
		this(x, y, 0);
	}

	public Vertex(double x, double y, double z) {
		this(x, y, z, 1.0);
	}

	public Vertex(double x, double y, double z, double weight) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.weight = weight;
	}

	public Vertex(double[] pos) {
		this((pos.length > 0) ? pos[0] : 0, (pos.length > 1) ? pos[1] : 0, (pos.length > 2) ? pos[2] : 0,
				(pos.length > 3) ? pos[3] : 1.0);
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

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		return new Vertex(getX(), getY(), getZ(), getWeight());
	}

	@Override
	public String toString() {
		return String.format("Vertex[%s, %s, %s];W: %s", getX(), getY(), getZ(), getWeight());
	}
}
