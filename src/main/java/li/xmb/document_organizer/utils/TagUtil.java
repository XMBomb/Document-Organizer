package li.xmb.document_organizer.utils;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Element;

public class TagUtil {
	public static boolean hasFontSize(final Element tag) {
		if (tag.attr("style").contains("font-size")) {
			return true;
		}
		return false;
	}

	public static boolean isBold(final Element tag) {
		if (tag.attr("style").contains("bold")) {
			return true;
		}
		return false;
	}

	public static boolean isUnderlined(final Element tag) {
		if (tag.attr("style").contains("underline")) {
			return true;
		}
		return false;
	}

	public static boolean isItalic(final Element tag) {
		if (tag.attr("style").contains("italic")) {
			return true;
		}
		return false;
	}

	public static boolean isAllCaps(final Element tag) {
		return StringUtils.isAllUpperCase(tag.text());
	}

}
