package game.engine.d3;

import java.util.LinkedList;
import java.util.List;

public class OBJObject {

	private String name;
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

		List<Face> faces = this.faces;
		for (Face f : faces) {
			obj.addFace(f.clone());
		}

		return obj;
	};

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OBJObject other = (OBJObject) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}
