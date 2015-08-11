package game.engine.d3;

import java.io.IOException;
import java.io.Reader;

public interface ReaderResolver {
	public Reader getReader(String s) throws IOException;
}
