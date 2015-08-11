package game.engine.d3;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

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
	 * @return
	 * @throws IOException
	 */
	public static OBJModel load(InputStream in, ReaderResolver resolver)
			throws IOException {
		return load(in, StandardCharsets.UTF_8, resolver);
	}

	/**
	 * @see #load(Reader)
	 * @param in
	 * @param cs
	 * @return
	 * @throws IOException
	 */
	public static OBJModel load(InputStream in, Charset cs,
			ReaderResolver resolver) throws IOException {
		return load(new InputStreamReader(in, cs), resolver);
	}

	/**
	 * Use default resolver depending on p
	 * 
	 * @see #load(InputStream, Charset, ReaderResolver)
	 * @param p
	 * @param cs
	 * @return
	 * @throws IOException
	 */
	public static OBJModel load(final Path p, final Charset cs)
			throws IOException {

		ReaderResolver resolver = new ReaderResolver() {

			@Override
			public Reader getReader(String s) throws IOException {
				return new InputStreamReader(Files.newInputStream(p.getParent()
						.resolve(s)), cs);
			}
		};
		return load(Files.newInputStream(p), cs, resolver);
	}

	public static OBJModel load(Reader r, ReaderResolver resolver)
			throws IOException {
		OBJModelParser parser = new OBJModelParser(r, resolver);
		return parser.parse();
	}
}
