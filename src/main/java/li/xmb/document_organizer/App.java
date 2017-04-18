package li.xmb.document_organizer;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App {
	private static final Logger logger = LoggerFactory.getLogger(App.class);

	public static void main(final String[] args) {
		App app = new App();
		app.init();
	}

	private void init() {
		try {
			final OcrReader ocrReader = new OcrReader();
			ocrReader.readAllFiles();
		} catch (final IOException e) {
			logger.error("General error", e);
		}
	}
}
