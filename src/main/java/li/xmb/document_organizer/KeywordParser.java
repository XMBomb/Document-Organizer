package li.xmb.document_organizer;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Map;

import li.xmb.document_organizer.config.Config;

public class KeywordParser {

	public void parseFile(final Path file) {
		@SuppressWarnings({ "rawtypes", "unchecked" })
		final ArrayList<Map> keyWords = Config.getDefault().getProperty("keywords", ArrayList.class);
		
		

		for(final Map<String, String> category : keyWords){
			category.forEach((key,value) -> System.out.println(key+value));
		}

	}

}
