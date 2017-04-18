package li.xmb.document_organizer.title.utils;

public class TitleUtil {
	public static String normalizeTitle(final String title){
		return title.replaceAll("[^A-Za-z0-9äöüÄÖÜéèàâî -]", "").trim();
	}

	public static String getFilenameFromTitle(final String title){
		return title.replaceAll("[^A-Za-z0-9äöüÄÖÜéèàâî -]", "").trim();
	}
}
