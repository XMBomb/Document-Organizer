package li.xmb.document_organizer;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import org.junit.Test;

import li.xmb.document_organizer.beans.Tag;
import li.xmb.document_organizer.cli.CliTitleChooser;
import li.xmb.document_organizer.title.TitleFinder;

public class CliTitleChooserTest {
	@Test
	public void decideTitleTest() throws IOException{
		final TitleFinder finder = new TitleFinder();
//		final List<Tag> tags = finder.findTitleInFile(Paths.get("tmp\\test.htm"));
//		final List<Tag> tags = finder.findTitleInFile(Paths.get("tmp\\06062016175425.htm"));
//		final List<Tag> tags = finder.findTitleInFile(Paths.get("tmp\\05072016232411.htm"));
//		final List<Tag> tags = finder.findTitleInFile(Paths.get("tmp\\05072016232434.htm"));
		final List<Tag> tags = finder.findTitleInFile(Paths.get("tmp\\KAP_17A1_d_Fragebogen.htm"));
		
		final CliTitleChooser cliHandler = new CliTitleChooser(tags);
		cliHandler.decideTitle();
	}
}
