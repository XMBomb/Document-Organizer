package li.xmb.document_organizer;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Paths;

import org.junit.Test;

public class TitleFinderTest {
	@Test
	public void readTest() throws IOException{
		final TitleFinder finder = new TitleFinder();
		
		assertEquals("im Journalismus", finder.findTitleInFile(Paths.get("tmp\\04062016140748.htm")));
		assertEquals("Potenz- und Wurzelfunktionen", finder.findTitleInFile(Paths.get("tmp\\04062016135342.htm")));
		assertEquals("Einladung zu den IDPA - Präsentationstagen", finder.findTitleInFile(Paths.get("tmp\\04062016134828.htm")));
		assertEquals("Vereinbarung Raiffeisen E-Banking-Dienstleistungen", finder.findTitleInFile(Paths.get("tmp\\23052016160852.htm")));
		assertEquals("Fähigkeitszeugnis", finder.findTitleInFile(Paths.get("tmp\\23052016161718.htm")));
//		assertEquals("Fähigkeitszeugnis", finder.findTitleInFile(Paths.get("tmp\\04062016160932.htm")));
		assertEquals("Wehrpflichtersatzabgabe", finder.findTitleInFile(Paths.get("tmp\\test.htm")));
	}
}
