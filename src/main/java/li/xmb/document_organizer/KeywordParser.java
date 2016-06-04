package li.xmb.document_organizer;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import li.xmb.document_organizer.config.Config;

public class KeywordParser {


	private void findKeyWords() {

	}

	public void parseLine(final String line) {
		final ArrayList<LinkedHashMap> keyWords = Config.getDefault().getProperty("keywords", ArrayList.class);
		
		for(final LinkedHashMap<?, ?> category : keyWords){
			category.values();
		}

	}

}
