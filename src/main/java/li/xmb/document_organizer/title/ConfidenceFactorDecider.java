package li.xmb.document_organizer.title;

import li.xmb.document_organizer.config.Config;
import li.xmb.document_organizer.title.confidence_factor.IConfidenceFactor;

public class ConfidenceFactorDecider {
	public static final int MIN_FACTOR = 0;
	public static final int MAX_FACTOR = 100;
	
	public static final int MIN_TEXT_LENGTH = 4;

	public static double getImportanceBasedConfidenceFactor ( IConfidenceFactor factor )
	{
		Class<? extends IConfidenceFactor> confidenceFactorClass = factor.getClass();
		return Config.getDefault().getDoubleProperty( "confidence."+confidenceFactorClass.getSimpleName() ) * factor.getFactor();
	}
	
}
