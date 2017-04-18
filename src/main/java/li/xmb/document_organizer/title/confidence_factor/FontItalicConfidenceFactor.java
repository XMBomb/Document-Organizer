package li.xmb.document_organizer.title.confidence_factor;

import org.jsoup.nodes.Element;

import li.xmb.document_organizer.title.ConfidenceFactorDecider;
import li.xmb.document_organizer.utils.TagUtil;

public class FontItalicConfidenceFactor implements IConfidenceFactor{

	private final Element htmlElement;

	public FontItalicConfidenceFactor(final Element htmlElement) {
		this.htmlElement = htmlElement;
	}


	@Override
	public int getFactor() {
		return TagUtil.isItalic(htmlElement) ? ConfidenceFactorDecider.MAX_FACTOR : ConfidenceFactorDecider.MIN_FACTOR;
	}
}
