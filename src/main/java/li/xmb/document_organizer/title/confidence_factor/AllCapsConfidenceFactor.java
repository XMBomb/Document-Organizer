package li.xmb.document_organizer.title.confidence_factor;

import org.jsoup.nodes.Element;

import li.xmb.document_organizer.title.ConfidenceFactorDecider;
import li.xmb.document_organizer.utils.TagUtil;

public class AllCapsConfidenceFactor implements IConfidenceFactor
{
	private Element htmlElement;

	public AllCapsConfidenceFactor (Element htmlElement)
	{
		this.htmlElement = htmlElement;
	}

	@Override
	public int getFactor ()
	{
		return TagUtil.isAllCaps(htmlElement) ? ConfidenceFactorDecider.MAX_FACTOR : ConfidenceFactorDecider.MIN_FACTOR;
	}

}
