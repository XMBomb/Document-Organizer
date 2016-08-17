package li.xmb.document_organizer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Paths;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;

import org.junit.Test;

public class JsonTest {
	@Test
	public void readJsonTest() throws FileNotFoundException{
		final FileInputStream fileInputStream = new FileInputStream(Paths.get("conf\\config.json").toFile());
//		final JsonParser parser = Json.createParser(fileInputStream);
		final JsonReader rdr = Json.createReader(fileInputStream);
		final JsonObject obj = rdr.readObject();
		
		final JsonArray results = obj.getJsonArray("keywords");
		for (final JsonObject result : results.getValuesAs(JsonObject.class)) {
			while(!result.isEmpty()) {
//				result.get
			}
		}
		
//		while (parser.hasNext()) {
//			final Event e = parser.next();
//			 if (e == Event.KEY_NAME) {
//				 System.out.println(parser.getString());
//			 }
//		}
	}
}
