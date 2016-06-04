package li.xmb.document_organizer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

import li.xmb.document_organizer.config.Config;

public class OcrReader {
	private final KeywordParser keywordParser = new KeywordParser();
	
	public static final String OCR_DIR = Config.getDefault().getStringProperty("workingDir") + Config.getDefault().getStringProperty("ocrDir");
	
	public final void read() throws IOException{
		final File folder = new File(OCR_DIR);
		for(final File foundFile : folder.listFiles()){
			processFile(foundFile.toPath());
		}
	}
	
	private final void processFile(final Path file) throws IOException{
        try (Stream<String> lines = Files.lines(file)) {
            lines.forEach(s -> keywordParser.parseLine(s));
        } catch (final IOException ex) {

        }
	}
}
