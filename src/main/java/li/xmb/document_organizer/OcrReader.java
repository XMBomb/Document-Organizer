package li.xmb.document_organizer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import li.xmb.document_organizer.beans.Tag;
import li.xmb.document_organizer.cli.CliTitleChooser;
import li.xmb.document_organizer.config.Config;
import li.xmb.document_organizer.title.TitleFinder;
import li.xmb.document_organizer.utils.FileUtil;

public class OcrReader {
	private static final Logger logger = LoggerFactory.getLogger(OcrReader.class);

	private static final String OCR_DIR = Config.getDefault().getStringProperty("ocrDir");
	private static final String OCR_FILE_FORMAT = Config.getDefault().getStringProperty("ocrFileFormat");
	private static final String PROCESSED_OCR_DIR = Config.getDefault().getStringProperty("processedOcrDir");
	private static final String SOURCE_FILE_DIR = Config.getDefault().getStringProperty("sourceFileDir");
	private static final String OUTPUT_DIR = Config.getDefault().getStringProperty("outputDir");
	private static final String FILE_EXTENSION_SEPARATOR = ".";

	public final void readAllFiles() throws IOException{
		final File folder = new File(OCR_DIR);
		for(final File foundFile : folder.listFiles()){
			logger.info("Processing file {}", foundFile.getName());
			// only accept the right file format
			if(FileUtil.getFileExtension(foundFile).equals(OCR_FILE_FORMAT)){
				findTitleOfFile(foundFile.toPath());
			}
		}
	}
	
	private final void findTitleOfFile(final Path ocrFile) throws IOException{
		final TitleFinder finder = new TitleFinder();
		final List<Tag> tags = finder.findTitleInFile(ocrFile);
		final CliTitleChooser cliHandler = new CliTitleChooser(tags);

		final String newFileName = cliHandler.decideTitle();
		logger.info("Using {} for the filename", newFileName);
		final File sourceFile = findCorrespondingSourceFile(ocrFile.toFile());
		try {
			renameFile(sourceFile, newFileName);
			moveOcrFile(ocrFile.toFile());
		}catch (final IOException e){
			logger.error("Could not rename file", e);
		}
	}
	
	private final File findCorrespondingSourceFile(final File ocrFile){
		final File sourceFilesFolder = new File(SOURCE_FILE_DIR);
		
		for (final File sourceFile : sourceFilesFolder.listFiles()){
			final String sourceFilename = FileUtil.getFilenameWithoutExtension(sourceFile);
			final String ocrFilename = FileUtil.getFilenameWithoutExtension(ocrFile);
			if (ocrFilename.compareTo(sourceFilename) == 0){
				return sourceFile;
			}
		}
		throw new OcrReaderException("No source file for ocr file found!");
	}

	private final void renameFile(final File file, final String newName) throws IOException{
		final String fileName = OUTPUT_DIR+File.separator+newName+ FILE_EXTENSION_SEPARATOR +FileUtil.getFileExtension(file);
		final File newFile = new File(fileName);
		if (newFile.exists()){
			throw new IOException("There is already a file with the name "+newName);
		}
		file.renameTo(newFile);
		logger.debug("Renamed file to {}", fileName);
	}

	private final void moveOcrFile(final File ocrFile){
		final String pathName = PROCESSED_OCR_DIR+File.separator+ocrFile.getName();
		ocrFile.renameTo(new File(pathName));
		logger.info("Moved ocr file to {}", pathName);
	}
}
