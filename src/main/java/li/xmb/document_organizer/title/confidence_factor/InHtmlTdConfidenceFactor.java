package li.xmb.document_organizer.title.confidence_factor;

import org.jsoup.nodes.Element;

import li.xmb.document_organizer.title.ConfidenceFactorDecider;

public class InHtmlTdConfidenceFactor implements IConfidenceFactor
{
	private Element htmlElement;

	public InHtmlTdConfidenceFactor (Element htmlElement)
	{
		this.htmlElement = htmlElement;
	}
	
	@Override
	public int getFactor ()
	{
		if(!htmlElement.parent().parent().tagName().contains("td")){
			return ConfidenceFactorDecider.MAX_FACTOR;
		}
		return ConfidenceFactorDecider.MIN_FACTOR;
	}

}
