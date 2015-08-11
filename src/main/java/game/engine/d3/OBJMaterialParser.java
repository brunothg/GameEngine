package game.engine.d3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OBJMaterialParser {

	private static final char LINE_NEEDS_NEXT_LINE_SIGN = '\\';
	private static final String COMMENT_REGEX = "^#.*";
	private static final String ARGUMENT_SPLIT_REGEX = "\\s";

	private static final Logger LOG = LoggerFactory
			.getLogger(OBJMaterialParser.class);

	private Reader reader;
	private OBJMaterialLibrary library;

	public OBJMaterialParser(Reader r) {
		this.reader = r;
	}

	/**
	 * Consume {@link #reader} input. Will be closed after parsing.
	 * 
	 * @throws IOException
	 */
	public OBJMaterialLibrary parse() throws IOException {
		library = new OBJMaterialLibrary();
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

		reader = null;
		in.close();
		return library;
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
		default:
			LOG.warn("Unkown argument '{}'", cmd);
			break;
		}
	}
}
