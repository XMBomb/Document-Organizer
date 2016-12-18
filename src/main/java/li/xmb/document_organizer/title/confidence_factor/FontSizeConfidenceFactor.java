package li.xmb.document_organizer.title.confidence_factor;

import java.util.LinkedHashMap;
import java.util.List;

import org.jsoup.nodes.Element;

import li.xmb.document_organizer.config.Config;
import li.xmb.document_organizer.title.ConfidenceFactorDecider;
import li.xmb.document_organizer.utils.HtmlUtil;

public class FontSizeConfidenceFactor implements IConfidenceFactor {

	private final Element htmlElement;

	public FontSizeConfidenceFactor(final Element htmlElement) {
		this.htmlElement = htmlElement;
	}

	@Override
	public int getFactor() {
		final List<LinkedHashMap<String, Integer>> params = Config.getDefault().getProperty("confidence.fontSizeConfidenceFactor.params", List.class);
		
		for (final LinkedHashMap<String, Integer> param : params) {
			for (final String fontSizeName : param.keySet()) {
				final int fontFactor = param.get(fontSizeName);
				if (htmlElement.attr(HtmlUtil.STYLE_ATTRIBUTE).contains(fontSizeName)) {
					return fontFactor;
				}
			}
		}
		return ConfidenceFactorDecider.MIN_FACTOR;
	}

}
