package li.xmb.document_organizer.title;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import li.xmb.document_organizer.beans.Tag;
import li.xmb.document_organizer.title.confidence_factor.IConfidenceFactor;
import li.xmb.document_organizer.title.confidence_factor.InHtmlTdConfidenceFactor;
import li.xmb.document_organizer.title.utils.TitleUtil;
import li.xmb.document_organizer.utils.TagUtil;

public class TitleFinder {
	private static final String VALID_TAG_TYPE = "font";
	private final int MAX_ORDER_CONFIDENCE_LEVEL = 100;

	public List<Tag> findTitleInFile(final Path file) throws IOException {
		final List<Tag> qualifiedElements = new ArrayList<>();
		final byte[] encoded = Files.readAllBytes(file);

		final String html = new String(encoded, "UTF-8");
		final Document doc = Jsoup.parse(html);
		
		
		final Elements foundTags = findValidTags(doc);
			
		
		int orderFactor = foundTags.size();
		for (final Element tag : foundTags) {
			final Tag qualifiedTag = new Tag(tag);
			qualifiedTag.increaseConfidenceLevel(getTagValidityFactor(tag), "tagValidity");
			
//			if (isInHeaderTag(tag)){
//				qualifiedTag.increaseConfidenceLevel(75, "headerTag");
//			}
//			if (TagUtil.isAllCaps(tag)) {
//				qualifiedTag.increaseConfidenceLevel(-50, "allCaps");
//			}
			
//			qualifiedTag.increaseConfidenceLevel(getSentenceLengthFactor(tag), "sentenceLength");

//			qualifiedTag.increaseConfidenceLevel(getWordsFoundThatManyTimesInPageFactor(tag, doc.text()), "wordsFoundThatManyTimesInPageFactor");

//			if (isTextLengthBigEnough(tag)) {
//				qualifiedTag.increaseConfidenceLevel(50, "textLength");
//			}
//			
//			qualifiedTag.increaseConfidenceLevel(getTextValidityFactor(tag), "validCharacters");

			if (tag.hasAttr("style")) {
				qualifiedTag.increaseConfidenceLevel(10, "hasStyle");
				if (TagUtil.hasFontSize(tag)) {
					qualifiedTag.increaseConfidenceLevel(getFontSizeFactor(tag), "fontSizeFactor");
				}
//				if (TagUtil.isBold(tag)) {
//					qualifiedTag.increaseConfidenceLevel(25, "isBold");
//				}
//				if (TagUtil.isItalic(tag)) {
//					qualifiedTag.increaseConfidenceLevel(-50, "isItalic");
//				}
//				if (TagUtil.isUnderlined(tag)) {
//					qualifiedTag.increaseConfidenceLevel(50, "isUnderlined");
//				}
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
		return qualifiedElements;

//		for (final Tag tag : qualifiedElements) {
//			System.out.println(tag.getTag().text() + "," + tag.getConfidenceLevel());
//		}


		// System.out.println(bestTag.getTag().text()+",
		// "+bestTag.getConfidenceLevel());

	}
	
	public String getBestTitle(final Path file) throws IOException{
		final List<Tag> qualifiedElements = findTitleInFile(file);
		final Tag bestTag = qualifiedElements.get(0);
		return TitleUtil.normalizeTitle(bestTag.getTag().text());
	}
	

//	TODO:useless
	private Elements findValidTags(final Document doc) {
		final Elements elements  = doc.select(VALID_TAG_TYPE);
		final Elements validTags = new Elements();
		for(final Element element : elements){
//			if(!element.parent().parent().tagName().contains("td")){
				validTags.add(element);
//			}
		}
		return validTags;
	}
	
	private int getTagValidityFactor(final Element tag){
		final IConfidenceFactor factor = new InHtmlTdConfidenceFactor( tag );
		
		return (int) ConfidenceFactorDecider.getImportanceBasedConfidenceFactor(factor);

	}

	private int normalizeOrderFactor(final int listSize, final int factor) {
		return (factor * MAX_ORDER_CONFIDENCE_LEVEL) / listSize;
	}

	private int getFontSizeFactor(final Element tag) {
		final String[][] fontSizes = {
			{"xx-small", "-100"},
			{"x-small", "-50"},
			{"small", "-25"},
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


}
