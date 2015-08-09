package game.engine.d3;

import game.engine.utils.ArrayUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Read OBJ models
 * 
 * @author Marvin Bruns
 *
 */
public class OBJModelParser {

	private static final char LINE_NEEDS_NEXT_LINE_SIGN = '\\';
	private static final String FACE_ARGUMENT_SPLIT_REGEX = "/";
	private static final String COMMENT_REGEX = "^#.*";
	private static final String ARGUMENT_SPLIT_REGEX = "\\s";

	private static final Logger LOG = LoggerFactory
			.getLogger(OBJModelParser.class);

	private Reader reader;
	private OBJModel model;

	private List<Vertex> vertices = new LinkedList<Vertex>();
	private List<TextureVertex> textureVertices = new LinkedList<TextureVertex>();
	private List<Normal> normals = new LinkedList<Normal>();

	public OBJModelParser(Reader r) {
		this.reader = r;
	}

	private OBJObject _actualObject;

	private void setActualObject(OBJObject obj) {
		_actualObject = obj;
	}

	private OBJObject getActualObject() {
		if (_actualObject == null) {
			_actualObject = new OBJObject(null);
			model.addObject(_actualObject);
		}
		return _actualObject;
	}

	/**
	 * Consume {@link #reader} input. Will be closed after parsing.
	 * 
	 * @throws IOException
	 */
	public OBJModel parse() throws IOException {
		model = new OBJModel();
		BufferedReader in = new BufferedReader(reader);

		String lineBuffer = "";
		String line;
		int lineNr = 0;
		while ((line = in.readLine()) != null) {
			line = line.trim();
			lineNr++;

			if (line.isEmpty()) {
				LOG.warn("Skipp empty line {}", lineNr);
				continue;
			}

			if (line.charAt(line.length() - 1) == LINE_NEEDS_NEXT_LINE_SIGN) {
				LOG.debug("Command needs next line");
				lineBuffer += line.substring(0, line.length() - 1);
				continue;
			}
			line = lineBuffer + line;
			lineBuffer = "";

			LOG.debug("Now at line '{}'", lineNr);
			parseLine(line);
		}

		in.close();
		reader = null;

		return model;
	}

	private void parseLine(String line) {

		if (line.matches(COMMENT_REGEX)) {
			LOG.info("Found comment '{}'", line);
			return;
		}

		String[] args = line.split(ARGUMENT_SPLIT_REGEX);
		parseArguments(args);
	}

	private void parseArguments(String[] args) {
		if (args == null || args.length <= 0) {
			LOG.warn("Skipp empty argument");
			return;
		}

		String cmd = args[0].toLowerCase();
		String[] params = Arrays.copyOfRange(args, 1, args.length);

		switch (cmd) {
		case "o":
			createObject(params);
			break;
		case "v":
			createVertex(params);
			break;
		case "vt":
			createTextureVertex(params);
			break;
		case "vn":
			createNormal(params);
			break;
		case "f":
			createFace(params);
			break;
		default:
			LOG.warn("Unkown argument '{}'", cmd);
			break;
		}
	}

	private void createNormal(String[] params) {
		double[] position = ArrayUtils.toDouble(params);
		Normal vn = new Normal(position);
		getActualObject().addNormal(vn);
		normals.add(vn);
		LOG.debug("Create normal '{}'", vn);
	}

	private void createTextureVertex(String[] params) {
		double[] position = ArrayUtils.toDouble(params);
		TextureVertex vt = new TextureVertex(position);
		getActualObject().addTextureVertex(vt);
		textureVertices.add(vt);
		LOG.debug("Create texture vertex '{}'", vt);
	}

	private void createVertex(String[] params) {
		double[] position = ArrayUtils.toDouble(params);
		Vertex vertex = new Vertex(position);
		getActualObject().addVertex(vertex);
		vertices.add(vertex);
		LOG.debug("Create vertex '{}'", vertex);
	}

	private void createFace(String[] params) {

		int[] vertices = new int[params.length];
		int[] textureVertices = null;
		int[] normals = null;

		for (int i = 0; i < params.length; i++) {
			String[] point = params[i].split(FACE_ARGUMENT_SPLIT_REGEX);

			vertices[i] = Integer.valueOf(point[0]);
			if ((point.length > 1 && !point[1].trim().isEmpty())
					|| textureVertices != null) {
				if (i == 0) {
					textureVertices = new int[params.length];
				}
				textureVertices[i] = Integer.valueOf(point[1]);
			}
			if (point.length > 2 || normals != null) {
				if (i == 0) {
					normals = new int[params.length];
				}
				normals[i] = Integer.valueOf(point[2]);
			}
		}

		Vertex[] v = new Vertex[vertices.length];
		for (int i = 0; i < vertices.length; i++) {
			v[i] = this.vertices.get(vertices[i] - 1);
		}

		TextureVertex[] vt = null;
		if (textureVertices != null) {
			vt = new TextureVertex[textureVertices.length];
			for (int i = 0; i < textureVertices.length; i++) {
				vt[i] = this.textureVertices.get(textureVertices[i] - 1);
			}
		}

		Normal[] vn = null;
		if (normals != null) {
			vn = new Normal[normals.length];
			for (int i = 0; i < normals.length; i++) {
				vn[i] = this.normals.get(normals[i] - 1);
			}
		}

		Face face = new Face(v, vt, vn);
		getActualObject().addFace(face);
		LOG.debug("Create face '{}'", face);
	}

	private void createObject(String[] params) {
		OBJObject obj = new OBJObject(params[0]);
		setActualObject(obj);
		model.addObject(obj);
		LOG.info("Create object '{}'", obj);
	}

}
