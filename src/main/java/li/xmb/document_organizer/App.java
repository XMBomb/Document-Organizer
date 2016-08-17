package li.xmb.document_organizer;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App {
	/**
	 * <p>
	 * The {@link Logger} for this class.
	 * </p>
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

	public static void main(final String[] args) {		
		try {
			final OcrReader ocrReader = new OcrReader();
			ocrReader.readAllFiles();
		} catch (final IOException e) {
			LOGGER.error(e.getMessage());
			e.printStackTrace();
		}
	}
}
