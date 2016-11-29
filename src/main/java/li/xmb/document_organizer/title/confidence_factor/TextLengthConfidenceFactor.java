package li.xmb.document_organizer.title.confidence_factor;

import org.jsoup.nodes.Element;

import li.xmb.document_organizer.title.ConfidenceFactorDecider;

public class TextLengthConfidenceFactor implements IConfidenceFactor
{

	private Element htmlElement;

	public TextLengthConfidenceFactor ( Element htmlElement )
	{
		this.htmlElement = htmlElement;
	}

	@Override
	public int getFactor ()
	{
		int textLength = htmlElement.text().length();
		return textLength < ConfidenceFactorDecider.MIN_TEXT_LENGTH ? ConfidenceFactorDecider.MIN_FACTOR : ConfidenceFactorDecider.MAX_FACTOR;
	}

}
