package li.xmb.document_organizer.utils;

import org.jsoup.helper.StringUtil;

public class WhitespaceUtil {
	public static String trimAllWhitespace(final String s){
		return StringUtil.normaliseWhitespace(s).trim().replaceAll("\u00A0", "").replaceAll(" ", "").replaceAll(" ", "");
	}
}
