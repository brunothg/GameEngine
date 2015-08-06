package game.engine.d3;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

public class OBJModel {

	private LinkedHashMap<String, OBJObject> objects = new LinkedHashMap<String, OBJObject>();

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

	@Override
	public String toString() {
		return String.format("Objects '%s'", objects.size());
	}

	@Override
	protected OBJModel clone() {

		OBJModel objm = new OBJModel();

		List<OBJObject> objs = getObjects();
		for (OBJObject obj : objs) {
			objm.addObject(obj.clone());
		}

		return objm;
	}
}
