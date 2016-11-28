package li.xmb.document_organizer.title.confidence_factor;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Element;

import li.xmb.document_organizer.title.ConfidenceFactorDecider;
import li.xmb.document_organizer.utils.CustomStringUtil;

public class WordFoundManyTimesConfidenceFactor implements IConfidenceFactor
{
	private String fullText;
	private Element htmlElement;

	private static final int BEST_COUNT = 4;
	private static final int EXPONENTIAL_FACTOR = 2;
	private static final int DEDUCTION_FACTOR = 3;

	public WordFoundManyTimesConfidenceFactor ( String fullText, Element htmlElement )
	{
		this.fullText = fullText;
		this.htmlElement = htmlElement;
	}

	@Override
	public int getFactor ()
	{
		fullText = CustomStringUtil.trimAllWhitespace( fullText );
		final String textToFind = CustomStringUtil.trimAllWhitespace( htmlElement.text() );
		if ( textToFind.length() < ConfidenceFactorDecider.MIN_TEXT_LENGTH )
		{
			return ConfidenceFactorDecider.MIN_FACTOR;
		}

		int count = 0;

		count = StringUtils.countMatches( fullText, textToFind );

		final int factor = (int) ( -Math.pow( ( count - BEST_COUNT ), EXPONENTIAL_FACTOR ) * DEDUCTION_FACTOR
				+ ConfidenceFactorDecider.MAX_FACTOR );
		if ( factor < ConfidenceFactorDecider.MIN_FACTOR )
		{
			return ConfidenceFactorDecider.MIN_FACTOR;
		}

		return factor;
	}

}
