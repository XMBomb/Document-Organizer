package li.xmb.document_organizer;

import java.nio.file.Paths;

import org.junit.Test;

public class KeywordParserTest {
	@Test
	public void testParse(){
		final KeywordParser parser = new KeywordParser();
		parser.parseFile(Paths.get("tmp\\test.htm"));
	}
}
