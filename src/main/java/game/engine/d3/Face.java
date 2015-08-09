package game.engine.d3;

public class Face {

	private Vertex[] vertices;
	private TextureVertex[] textureVertices;
	private Normal[] normals;

	public Face() {
		this(null, null, null);
	}

	public Face(Vertex[] vertices, TextureVertex[] textureVertices,
			Normal[] normals) {
		this.vertices = vertices;
		this.textureVertices = textureVertices;
		this.normals = normals;
	}

	public Vertex[] getVertices() {
		return vertices;
	}

	public void setVertices(Vertex[] vertices) {
		this.vertices = vertices;
	}

	public TextureVertex[] getTextureVertices() {
		return textureVertices;
	}

	public void setTextureVertices(TextureVertex[] textureVertices) {
		this.textureVertices = textureVertices;
	}

	public Normal[] getNormals() {
		return normals;
	}

	public void setNormals(Normal[] normals) {
		this.normals = normals;
	}

	@Override
	public String toString() {
		return String.format("Face[%s vertices, %s normal, %s texture]",
				(getVertices() != null) ? getVertices().length : 0,
				(getNormals() != null) ? getNormals().length : 0,
				(getTextureVertices() != null) ? getTextureVertices().length
						: 0);
	}

	@Override
	protected Face clone() {
		return new Face((getVertices() != null) ? getVertices().clone() : null,
				(getTextureVertices() != null) ? getTextureVertices().clone()
						: null, (getNormals() != null) ? getNormals().clone()
						: null);
	}
}
