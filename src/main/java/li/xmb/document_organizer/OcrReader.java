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
	/**
	* <p>The {@link Logger} for this class.</p>
	*/
	private static final Logger LOGGER = LoggerFactory.getLogger(OcrReader.class);

	private final KeywordParser keywordParser = new KeywordParser();
	
	public static final String OCR_DIR = Config.getDefault().getStringProperty("ocrDir");
	public static final String OCR_FILE_FORMAT = Config.getDefault().getStringProperty("ocrFileFormat");
	public static final String PROCESSED_OCR_DIR = Config.getDefault().getStringProperty("processedOcrDir");
	public static final String SOURCE_FILE_DIR = Config.getDefault().getStringProperty("sourceFileDir");
	public static final String OUTPUT_DIR = Config.getDefault().getStringProperty("outputDir");
	

	
	public final void readAllFiles() throws IOException{
		final File folder = new File(OCR_DIR);
		for(final File foundFile : folder.listFiles()){
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
		final Tag chosenTitleTag = cliHandler.decideTitle();
		final String newFileName = chosenTitleTag.getFilenameText();
		LOGGER.info("Using {} for the filename", newFileName);
		final File sourceFile = findCorrespondingSourceFile(ocrFile.toFile());
		renameFile(sourceFile, newFileName);
		moveOcrFile(ocrFile.toFile());
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
	
	private final void renameFile(final File file, final String newName){
		final String fileName = OUTPUT_DIR+File.separator+newName+"."+FileUtil.getFileExtension(file);
//		TODO: handle non existant paths 
		file.renameTo(new File(fileName));
		LOGGER.info("Renamed file to {}", fileName);
	}
	
	private final void moveOcrFile(final File ocrFile){
		final String pathName = PROCESSED_OCR_DIR+File.separator+ocrFile.getName();
		ocrFile.renameTo(new File(pathName));
		LOGGER.info("Moved ocr file to {}", pathName);
	}
}
