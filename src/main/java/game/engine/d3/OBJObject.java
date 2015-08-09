package game.engine.d3;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class OBJObject {

	private String name;
	private List<Vertex> vertices = new LinkedList<Vertex>();
	private List<TextureVertex> textureVertices = new LinkedList<TextureVertex>();
	private List<Normal> normals = new LinkedList<Normal>();
	private List<Face> faces = new LinkedList<Face>();

	public OBJObject(String name) {
		this.setName(name);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void addVertex(Vertex vertex) {
		vertices.add(vertex);
	}

	public void setVertex(Vertex vertex, int nr) {
		vertices.set(nr, vertex);
	}

	public Vertex getVertex(int nr) {
		return vertices.get(nr);
	}

	public List<Vertex> getVertices() {
		ArrayList<Vertex> vertices = new ArrayList<Vertex>(this.vertices.size());

		for (Vertex v : this.vertices) {
			vertices.add(v);
		}

		return vertices;
	}

	public void addTextureVertex(TextureVertex vt) {
		textureVertices.add(vt);
	}

	public void setTextureVertex(TextureVertex vt, int nr) {
		textureVertices.set(nr, vt);
	}

	public TextureVertex getTextureVertex(int nr) {
		return textureVertices.get(nr);
	}

	public List<TextureVertex> getTextureVertices() {
		ArrayList<TextureVertex> textureVertices = new ArrayList<TextureVertex>(
				this.vertices.size());

		for (TextureVertex v : this.textureVertices) {
			textureVertices.add(v);
		}

		return textureVertices;
	}

	public void addNormal(Normal vn) {
		normals.add(vn);
	}

	public void setNormal(Normal vn, int nr) {
		normals.set(nr, vn);
	}

	public Normal getNormal(int nr) {
		return normals.get(nr);
	}

	public List<Normal> getNormals() {
		ArrayList<Normal> normals = new ArrayList<Normal>(this.normals.size());

		for (Normal v : this.normals) {
			normals.add(v);
		}

		return normals;
	}

	public void addFace(Face face) {
		faces.add(face);
	}

	public List<Face> getFaces() {
		return faces;
	}

	public String toString() {
		return String.format("Object '%s'", getName());
	}

	protected OBJObject clone() {
		OBJObject obj = new OBJObject(getName());

		List<Vertex> vertices = this.vertices;
		for (Vertex v : vertices) {
			obj.addVertex(v.clone());
		}

		List<Face> faces = this.faces;
		for (Face f : faces) {
			obj.addFace(f.clone());
		}

		return obj;
	};

}
