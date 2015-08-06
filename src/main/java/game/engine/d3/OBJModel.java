package game.engine.d3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class OBJModel {

	private Map<String, OBJObject> objects = new HashMap<String, OBJObject>();

	public OBJObject addObject(OBJObject obj) {
		return objects.put(obj.getName(), obj);
	}

	public OBJObject getObject(String name) {
		return objects.get(name);
	}

	public List<OBJObject> getObjects() {
		Set<Entry<String, OBJObject>> objSet = objects.entrySet();

		ArrayList<OBJObject> objList = new ArrayList<OBJObject>(objSet.size());
		for (Entry<String, OBJObject> obj : objSet) {
			objList.add(obj.getValue());
		}

		return objList;
	}

	// TODO toString
	// TODO clone
}
