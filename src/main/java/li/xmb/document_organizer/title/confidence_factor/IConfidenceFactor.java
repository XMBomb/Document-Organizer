package li.xmb.document_organizer.title.confidence_factor;

public interface IConfidenceFactor
{
	/*
	 * 100 is max confidence
	 * 		boolean false = 0, true = 100
	 */
	
	int getFactor();
}
