package li.xmb.document_organizer;

import java.util.ArrayList;

import org.junit.Test;

import li.xmb.document_organizer.config.Config;

public class ConfigTest {
	@Test
	public void testConfig(){
		Config.getDefault().getProperty("keywords", ArrayList.class);
	}
}
