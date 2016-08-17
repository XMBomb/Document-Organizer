package li.xmb.document_organizer.utils;

import java.io.File;

public class FileUtil {
	public static String getFilenameWithoutExtension(final File file){
		final String filename = file.getName();
		return filename.substring(0, filename.lastIndexOf('.'));
	}

	public static String getFileExtension(final File file) {
		final String filename = file.getName();
		return filename.substring(filename.lastIndexOf('.') + 1, filename.length());
	}
}
