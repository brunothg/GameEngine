package game.engine.d3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Read OBJ models
 * 
 * @author Marvin Bruns
 *
 */
public class OBJModelParser {

	private static final Logger LOG = LoggerFactory
			.getLogger(OBJModelParser.class);

	private Reader reader;

	public OBJModelParser(Reader r) {
		this.reader = r;
	}

	/**
	 * Consume {@link #reader} input. Will be closed after parsing.
	 * 
	 * @throws IOException
	 */
	public void parse() throws IOException {
		BufferedReader in = new BufferedReader(reader);

		String line;
		while ((line = in.readLine()) != null) {
			line = line.trim();
			if (line.isEmpty()) {
				continue;
			}

			parseLine(line);
		}

		in.close();
	}

	private void parseLine(String line) {

		if (line.startsWith("#")) {
			LOG.debug("Found comment '{}'", line);
		}
	}

}
