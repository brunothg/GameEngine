package game.engine.d3;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import org.junit.Before;
import org.junit.Test;

public class OBJModelParserTest {

	private OBJModelParser parser;

	@Before
	public void setUp() throws Exception {
		parser = new OBJModelParser(new InputStreamReader(
				OBJModelParserTest.class.getResourceAsStream("/ape.obj"),
				StandardCharsets.UTF_8), null);
	}

	@Test
	public void parseTest() throws Exception {
		parser.parse();
	}
}
