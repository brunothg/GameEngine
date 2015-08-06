package game.engine.d3;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class OBJObject {

	private String name;
	private List<Vertex> vertices = new LinkedList<Vertex>();

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

	public String toString() {
		return String.format("Object '%s'", getName());
	}

	// TODO clone

}
