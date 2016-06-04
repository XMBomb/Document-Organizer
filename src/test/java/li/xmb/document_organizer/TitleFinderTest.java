package li.xmb.document_organizer;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Paths;

import org.junit.Test;

public class TitleFinderTest {
	@Test
	public void readTest() throws IOException{
		final TitleFinder finder = new TitleFinder();
//		
		assertEquals("im Journalismus", finder.getBestTitle(Paths.get("tmp\\04062016140748.htm")));
		assertEquals("Potenz- und Wurzelfunktionen", finder.getBestTitle(Paths.get("tmp\\04062016135342.htm")));
		assertEquals("Einladung zu den IDPA - Präsentationstagen", finder.getBestTitle(Paths.get("tmp\\04062016134828.htm")));
		assertEquals("Vereinbarung Raiffeisen E-Banking-Dienstleistungen", finder.getBestTitle(Paths.get("tmp\\23052016160852.htm")));
		assertEquals("Fähigkeitszeugnis", finder.getBestTitle(Paths.get("tmp\\23052016161718.htm")));
		assertEquals("Volksabstimmung vom 5 Juni 2016", finder.getBestTitle(Paths.get("tmp\\04062016163621.htm")));
		assertEquals("Guide for Quidc Use", finder.getBestTitle(Paths.get("tmp\\04062016165959.htm")));
		assertEquals("Tail Slash", finder.getBestTitle(Paths.get("tmp\\04062016181549.htm")));
//		assertEquals("Medizinischer Fragebogen", finder.getBestTitle(Paths.get("tmp\\KAP_17A1_d_Fragebogen.htm")));
		assertEquals("Certificate of Proficiency in English", finder.getBestTitle(Paths.get("tmp\\StatementOfResult.htm")));
		assertEquals("Anlass-Planung", finder.getBestTitle(Paths.get("tmp\\Anlassplanung.htm")));
		assertEquals("Wehrpflichtersatzabgabe", finder.getBestTitle(Paths.get("tmp\\test.htm")));
//		assertEquals("Arztzeugnis", finder.getBestTitle(Paths.get("tmp\\04062016160932.htm")));
	}
}
