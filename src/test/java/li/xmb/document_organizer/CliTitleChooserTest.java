package li.xmb.document_organizer;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import org.junit.Test;

public class CliTitleChooserTest {
	@Test
	public void decideTitleTest() throws IOException{
		final TitleFinder finder = new TitleFinder();
		final List<Tag> tags = finder.findTitleInFile(Paths.get("tmp\\test.htm"));
		
		final CliTitleChooser cliHandler = new CliTitleChooser(tags);
		cliHandler.decideTitle();
	}
}
