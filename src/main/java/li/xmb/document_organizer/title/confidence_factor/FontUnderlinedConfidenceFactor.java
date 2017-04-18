package li.xmb.document_organizer.title.confidence_factor;

import org.jsoup.nodes.Element;

import li.xmb.document_organizer.title.ConfidenceFactorDecider;
import li.xmb.document_organizer.utils.TagUtil;

public class FontUnderlinedConfidenceFactor implements IConfidenceFactor{

	private final Element htmlElement;

	public FontUnderlinedConfidenceFactor(final Element htmlElement) {
		this.htmlElement = htmlElement;
	}


	@Override
	public int getFactor() {
		return TagUtil.isUnderlined(htmlElement) ? ConfidenceFactorDecider.MAX_FACTOR : ConfidenceFactorDecider.MIN_FACTOR;
	}
}
