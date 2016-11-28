package li.xmb.document_organizer.title.confidence_factor;

import org.jsoup.nodes.Element;

import li.xmb.document_organizer.title.ConfidenceFactorDecider;
import li.xmb.document_organizer.utils.CustomStringUtil;
import li.xmb.document_organizer.utils.HtmlUtil;

public class ValidTextConfidenceFactor implements IConfidenceFactor
{
	private final String REGEX_VALID_CHARACTERS = "[A-Za-z0-9äöüÄÖÜéèàâî ,.:\"-]+";
	private Element htmlElement;

	public ValidTextConfidenceFactor ( Element htmlElement )
	{
		this.htmlElement = htmlElement;
	}

	@Override
	public int getFactor ()
	{
		final String text = CustomStringUtil.trimAllWhitespace(HtmlUtil.removeTags(htmlElement.text(), false));
		if (text.length() < ConfidenceFactorDecider.MIN_TEXT_LENGTH){
			return ConfidenceFactorDecider.MIN_FACTOR;
		}
		if (text.matches(REGEX_VALID_CHARACTERS)) {
			return ConfidenceFactorDecider.MAX_FACTOR;
		}
		return ConfidenceFactorDecider.MAX_FACTOR;
	}

}
