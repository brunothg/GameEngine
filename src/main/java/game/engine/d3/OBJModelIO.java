package game.engine.d3;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * Class providing IO functionality for '.obj' files
 * 
 * @author Marvin Bruns
 *
 */
public class OBJModelIO {

	/**
	 * Uses UTF-8 as charset
	 * 
	 * @see #load(InputStream, Charset)
	 * @param in
	 * @throws IOException
	 */
	public static void load(InputStream in) throws IOException {
		load(in, StandardCharsets.UTF_8);
	}

	/**
	 * @see #load(Reader)
	 * @param in
	 * @param cs
	 * @throws IOException
	 */
	public static void load(InputStream in, Charset cs) throws IOException {
		load(new InputStreamReader(in, cs));
	}

	public static void load(Reader r) throws IOException {
		OBJModelParser parser = new OBJModelParser(r);
		parser.parse();
	}
}
