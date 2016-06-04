package li.xmb.document_organizer.utils;

public class TitleUtil {
	public static String normalizeTitle(final String s){
		return s.replaceAll("[^A-Za-z0-9äöüÄÖÜéèàâî -]", "").trim();
	}
}
