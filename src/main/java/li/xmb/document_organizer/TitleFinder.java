package li.xmb.document_organizer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.helper.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import li.xmb.document_organizer.utils.WhitespaceUtil;

public class TitleFinder {
	private static final double MULTIPLE_WORD_FACTOR = 2500D;
	private final int MAX_ORDER_CONFIDENCE_LEVEL = 100;
	private final int MIN_TEXT_LENGTH = 4;
	private final String REGEX_VALID_CHARACTERS = "[A-Za-z0-9äöüÄÖÜéèàâî ,-]+";

	public String findTitleInFile(final Path file) throws IOException {
		final List<Tag> qualifiedElements = new ArrayList<>();
		final byte[] encoded = Files.readAllBytes(file);

		final String html = new String(encoded, "UTF-8");
		final Document doc = Jsoup.parse(html);
		final Elements foundTags = doc.select("font");

		int orderFactor = foundTags.size();
		for (final Element tag : foundTags) {
			final Tag qualifiedTag = new Tag(tag);
			if (isAllCaps(tag)) {
				qualifiedTag.increaseConfidenceLevel(-50, "allCaps");
			}

			qualifiedTag.increaseConfidenceLevel(getWordsFoundThatManyTimesInPageFactor(tag, doc.text()), "wordsFoundThatManyTimesInPageFactor");

			if (isTextLengthBigEnough(tag)) {
				qualifiedTag.increaseConfidenceLevel(50, "textLength");
			}

			if (tag.text().matches(REGEX_VALID_CHARACTERS)) {
				qualifiedTag.increaseConfidenceLevel(100, "validCharacters");
			}
			if (tag.hasAttr("style")) {
				qualifiedTag.increaseConfidenceLevel(10, "hasStyle");
				if (hasFontSize(tag)) {
					qualifiedTag.increaseConfidenceLevel(getFontSizeFactor(tag), "fontSizeFactor");
				}
				if (isBold(tag)) {
					qualifiedTag.increaseConfidenceLevel(25, "isBold");
				}
				if (isItalic(tag)) {
					qualifiedTag.increaseConfidenceLevel(-50, "isItalic");
				}
				if (isUnderlined(tag)) {
					qualifiedTag.increaseConfidenceLevel(50, "isUnderlined");
				}
			}
			qualifiedTag.increaseConfidenceLevel(normalizeOrderFactor(foundTags.size(), orderFactor), "order");
			qualifiedElements.add(qualifiedTag);
			orderFactor--;
		}

		qualifiedElements.sort(new Comparator<Tag>() {
			@Override
			public int compare(final Tag t1, final Tag t2) {
				return t1.compareTo(t2);
			}

		});

//		for (final Tag tag : qualifiedElements) {
//			System.out.println(tag.getTag().text() + "," + tag.getConfidenceLevel());
//		}

		 final Tag bestTag = qualifiedElements.get(0);
//		 for(final Tag tagToTest : qualifiedElements){
//			 if(bestTag == null || tagToTest.getConfidenceLevel() >
//			 	bestTag.getConfidenceLevel()){
//				 bestTag = tagToTest;
//			 }
//		 }
		 return normalizeTitle(bestTag.getTag().text());
		// System.out.println(bestTag.getTag().text()+",
		// "+bestTag.getConfidenceLevel());

	}
	
	private String normalizeTitle(final String s){
		return s.trim().replaceAll("[^A-Za-z0-9äöüÄÖÜéèàâî -]", "");
	}

	private int getWordsFoundThatManyTimesInPageFactor(final Element tag, String fullText) {
		fullText = StringUtil.normaliseWhitespace(fullText);
		final String textToFind = WhitespaceUtil.trimAllWhitespace(tag.text());
		if (textToFind.length() < MIN_TEXT_LENGTH) {
			return 0;
		}

		int count = 0;

		count = StringUtils.countMatches(fullText, textToFind);
		
		final double factor = (((double)textToFind.length()*count)
				/fullText.length())
				*MULTIPLE_WORD_FACTOR;

		return (int)factor;
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
	
	private boolean isUnderlined(final Element tag) {
		if (tag.attr("style").contains("underline")) {
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

	private int getFontSizeFactor(final Element tag) {
		final String[][] fontSizes = {
			{"xx-small", "-100"},
			{"x-small", "-50"},
			{"small", "-20"},
			{"medium", "0"},
			{"large", "15"},
			{"x-large", "20"},
			{"xx-large", "0"},
		};
		
		for(int i=0; i<fontSizes.length; i++){
			final String[] fontSize = fontSizes[i];
			final String fontSizeName = fontSize[0];
			final int fontFactor = Integer.valueOf(fontSize[1]);
			if (tag.attr("style").contains(fontSizeName)){
				return fontFactor;
			}
		}
		return 0;
	}

	private boolean hasFontSize(final Element tag) {
		if (tag.attr("style").contains("font-size")) {
			return true;
		}
		return false;
	}
}
