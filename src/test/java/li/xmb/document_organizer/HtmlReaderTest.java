package li.xmb.document_organizer;

import java.io.IOException;

import org.junit.Test;

public class HtmlReaderTest {
	@Test
	public void readTest() throws IOException{
		final HtmlReader reader = new HtmlReader();
		reader.read();
	}
}
