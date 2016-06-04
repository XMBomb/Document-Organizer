package li.xmb.document_organizer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.helper.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HtmlReader {
	private final List<Tag> qualifiedElements = new ArrayList<>();
	private final int MAX_ORDER_CONFIDENCE_LEVEL = 100;
	private final int MIN_TEXT_LENGTH = 4;
	private final String REGEX_VALID_CHARACTERS = "[A-Za-z0-9äöüÄÖÜéàèâî ,-]+";

	public void read() throws IOException {
		final byte[] encoded = Files.readAllBytes(Paths.get("tmp\\test.htm"));
		// final byte[] encoded =
		// Files.readAllBytes(Paths.get("tmp\\23052016161718.htm"));
		// final byte[] encoded =
		// Files.readAllBytes(Paths.get("tmp\\23052016160852.htm"));

		final String html = new String(encoded, "UTF-8");
		final Document doc = Jsoup.parse(html);
		final Elements foundTags = doc.select("font");

		int orderFactor = foundTags.size();
		for (final Element tag : foundTags) {
			final Tag qualifiedTag = new Tag(tag);
			if (isAllCaps(tag)) {
				qualifiedTag.increaseConfidenceLevel(-50);
			}

			qualifiedTag.increaseConfidenceLevel(getWordsFoundThatManyTimesInPageFactor(tag, doc.text()));

			if (isTextLengthBigEnough(tag)) {
				qualifiedTag.increaseConfidenceLevel(50);
			}

			if (tag.text().matches(REGEX_VALID_CHARACTERS)) {
				qualifiedTag.increaseConfidenceLevel(100);
			}
			if (tag.hasAttr("style")) {
				qualifiedTag.increaseConfidenceLevel(10);
				if (hasFontSize(tag)) {
					if (isFontSizeBigEnough(tag)) {
						qualifiedTag.increaseConfidenceLevel(50);
					}
				}
				if (isBold(tag)) {
					qualifiedTag.increaseConfidenceLevel(25);
				}
				if (isItalic(tag)) {
					qualifiedTag.increaseConfidenceLevel(-50);
				}
			}
			qualifiedTag.increaseConfidenceLevel(normalizeOrderFactor(foundTags.size(), orderFactor));
			this.qualifiedElements.add(qualifiedTag);
			orderFactor--;
		}

		qualifiedElements.sort(new Comparator<Tag>() {
			@Override
			public int compare(final Tag t1, final Tag t2) {
				// ascending order
				return t1.compareTo(t2);
			}

		});

		for (final Tag tag : qualifiedElements) {
			System.out.println(tag.getTag().text() + "," + tag.getConfidenceLevel());
		}

		// Tag bestTag = null;
		// for(final Tag tagToTest : this.qualifiedElements){
		// if(bestTag == null || tagToTest.getConfidenceLevel() >
		// bestTag.getConfidenceLevel()){
		// bestTag = tagToTest;
		// }
		// }
		// System.out.println(bestTag.getTag().text()+",
		// "+bestTag.getConfidenceLevel());

	}

	private int getWordsFoundThatManyTimesInPageFactor(final Element tag, String fullText) {
		fullText = StringUtil.normaliseWhitespace(fullText);
		final String textToFind = StringUtil.normaliseWhitespace(tag.text());
		if (textToFind.length() < MIN_TEXT_LENGTH) {
			return 0;
		}

		int count = 0;

		for (final String word : textToFind.split(" ")) {
			if (word.length() > MIN_TEXT_LENGTH) {
				count += StringUtils.countMatches(fullText, word);
			}
		}

		// TODO
		return count * 10;
	}

	private boolean isTextLengthBigEnough(final Element tag) {
		return tag.text().length() >= MIN_TEXT_LENGTH;
	}

	private int normalizeOrderFactor(final int listSize, final int factor) {
		return (factor * MAX_ORDER_CONFIDENCE_LEVEL) / listSize;
	}

	private boolean isAllCaps(final Element tag) {
		return StringUtils.isAllUpperCase(tag.text());
	}

	private boolean isBold(final Element tag) {
		if (tag.attr("style").contains("bold")) {
			return true;
		}
		return false;
	}

	private boolean isItalic(final Element tag) {
		if (tag.attr("style").contains("italic")) {
			return true;
		}
		return false;
	}

	private boolean isFontSizeBigEnough(final Element tag) {
		if (tag.attr("style").contains("medium") || tag.attr("style").contains("large")) {
			return true;
		}
		return false;
	}

	private boolean hasFontSize(final Element tag) {
		if (tag.attr("style").contains("font-size")) {
			return true;
		}
		return false;
	}
}
