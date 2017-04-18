package li.xmb.document_organizer.utils;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Element;

public class TagUtil {
	private static final String HTML_ITALIC_ATTRIBUTE = "italic";
	private static final String HTML_UNDERLINED_ATTRIBUTE = "underline";
	private static final String HTML_BOLD_ATTRIBUTE = "bold";
	private static final String HTML_FONT_SIZE_ATTRIBUTE = "font-size";
	private static final String HTML_STYLE_ATTRIBUTE = "style";

	public static boolean hasFontSize(final Element tag) {
		if (tag.attr(HTML_STYLE_ATTRIBUTE).contains(HTML_FONT_SIZE_ATTRIBUTE)) {
			return true;
		}
		return false;
	}

	public static boolean isBold(final Element tag) {
		if (tag.attr(HTML_STYLE_ATTRIBUTE).contains(HTML_BOLD_ATTRIBUTE)) {
			return true;
		}
		return false;
	}

	public static boolean isUnderlined(final Element tag) {
		if (tag.attr(HTML_STYLE_ATTRIBUTE).contains(HTML_UNDERLINED_ATTRIBUTE)) {
			return true;
		}
		return false;
	}

	public static boolean isItalic(final Element tag) {
		if (tag.attr(HTML_STYLE_ATTRIBUTE).contains(HTML_ITALIC_ATTRIBUTE)) {
			return true;
		}
		return false;
	}

	public static boolean isAllCaps(final Element tag) {
		return StringUtils.isAllUpperCase(tag.text());
	}

}
